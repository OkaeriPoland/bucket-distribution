package eu.okaeri.bdistribution;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public abstract class BucketDistributor<T> {

    private final int buckets;
    private final int sizePerBucket;
    private final int maxCapacity;

    /**
     * @param buckets       Amount of target buckets
     * @param sizePerBucket Size of single target bucket
     */
    public BucketDistributor(int buckets, int sizePerBucket) {
        this.buckets = buckets;
        this.sizePerBucket = sizePerBucket;
        this.maxCapacity = buckets * sizePerBucket;
    }

    public abstract Distribution<T> distribute(List<Bucket<T>> elements);

    public List<Bucket<T>> capAtSizePerBucket(List<Bucket<T>> elements) {
        List<Bucket<T>> out = new ArrayList<>();
        for (Bucket<T> element : elements) {
            if (element.size() <= this.getSizePerBucket()) {
                out.add(element);
                continue;
            }
            List<T> newBucketItems = new ArrayList<>();
            int currentSize = 0;
            //noinspection ForLoopReplaceableByForEach
            for (Iterator<T> iterator = element.iterator(); iterator.hasNext(); ) {
                T item = iterator.next();
                newBucketItems.add(item);
                currentSize++;
                if (currentSize == this.getSizePerBucket()) {
                    out.add(new Bucket<>(newBucketItems));
                    newBucketItems.clear();
                    currentSize = 0;
                }
            }
            if (!newBucketItems.isEmpty()) {
                out.add(new Bucket<>(newBucketItems));
            }
        }
        return out;
    }
}
