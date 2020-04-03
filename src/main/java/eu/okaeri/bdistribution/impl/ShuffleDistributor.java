package eu.okaeri.bdistribution.impl;

import eu.okaeri.bdistribution.Bucket;
import eu.okaeri.bdistribution.BucketDistributor;
import eu.okaeri.bdistribution.Distribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ShuffleDistributor distributes elements into the buckets
 * by shuffling input until it gets list that fits fully
 * into desired buckets.
 * <p>
 * ShuffleDistributor(buckets=3, sizePerBucket=4)
 * 1, 4, 1, 1, 1, 2, 2 -> [1+4=5] (invalid, exceeds sizePerBucket, shuffle)
 * 1, 1, 1, 1, 2, 2, 4 -> [1, 1, 1, 1] and [2, 2] and [4]
 * <p>
 * Notes:
 * - Sum of size of bucket elements in {@link ShuffleDistributor#distribute(List)} ()} must not exceed sizePerBucket*buckets
 * - Single bucket cannot be bigger than sizePerBucket (if so, it should be capped by using {@link BucketDistributor#capAtSizePerBucket(List)} before)
 * - Some input elements cannot be sorted into desired bucket size, therefore maxTries limit was introduced,
 *   distributor will throw {@link ShuffleIterationLimitException} when specified limit of iterations is reached.
 *   By default maxTries is calculated using the following formula: buckets * sizePerBucket * 100.
 */
public class ShuffleDistributor<T> extends BucketDistributor<T> {

    private final int maxTries;

    /**
     * @param buckets       Amount of target buckets
     * @param sizePerBucket Size of single target bucket
     */
    public ShuffleDistributor(int buckets, int sizePerBucket) {
        super(buckets, sizePerBucket);
        this.maxTries = this.getMaxCapacity() * 100;
    }

    /**
     * @param buckets       Amount of target buckets
     * @param sizePerBucket Size of single target bucket
     * @param maxTries      Limit of shuffle iterations
     */
    public ShuffleDistributor(int buckets, int sizePerBucket, int maxTries) {
        super(buckets, sizePerBucket);
        this.maxTries = maxTries;
    }

    public int getMaxTries() {
        return this.maxTries;
    }

    @Override
    public Distribution<T> distribute(List<Bucket<T>> elements) {

        // clone, because who knows what input is
        elements = new ArrayList<>(elements);

        // empty distribution
        if (elements.isEmpty()) {
            return new Distribution<>(elements, true);
        }

        // calculate total count of elements
        int elementsTotalSize = 0;
        for (Bucket<T> element : elements) {
            elementsTotalSize += element.size();
        }

        // validate if elements can fit into this distributor
        if (elementsTotalSize > this.getMaxCapacity()) {
            throw new IllegalArgumentException("the total size of elements exceeds the capacity of distributor: " + elementsTotalSize + ">" + this.getMaxCapacity());
        }

        // prepare sort buffers
        List<Bucket<T>> out = new ArrayList<>();
        List<T> currentValues = new ArrayList<>();
        int total = 0;
        int i = 0;
        int current = 0;
        int iterationCount = 0;

        // fill buckets by brute-forcing
        for (; i < elements.size(); i++) {

            // limit iteration count
            iterationCount++;
            if (iterationCount >= this.getMaxTries()) {
                throw new ShuffleIterationLimitException("ShuffleDistributor was unable to distribute elements (" + elements + ") in " + this.getMaxTries() + " tries");
            }

            Bucket<T> bucket = elements.get(i);
            Integer bucketSize = bucket.size();
            current += bucketSize;
            currentValues.addAll(new ArrayList<>(bucket.getElements()));

            // validate if provided bucket can be accepted in our distribution
            if (bucketSize > this.getSizePerBucket()) {
                throw new IllegalArgumentException("encountered group with a size over max allowed bucket size " + bucketSize + ">" + this.getSizePerBucket());
            }

            // current local sum is over-fitting bucket,
            // shuffle list and try again
            if (current > this.getSizePerBucket()) {
                current = 0;
                i = -1;
                total = 0;
                out.clear();
                Collections.shuffle(elements);
                currentValues.clear();
                continue;
            }

            // current local sum is desired bucket size
            if (current == this.getSizePerBucket()) {
                out.add(new Bucket<>(currentValues));
                currentValues = new ArrayList<>();
                total += current;
                current = 0;
            }
        }

        // add remaining unbalanced bucket if present
        boolean balanced = true;
        if (!currentValues.isEmpty()) {
            out.add(new Bucket<>(currentValues));
            balanced = false;
        }

        // ready distribution
        return new Distribution<>(out, balanced);
    }
}
