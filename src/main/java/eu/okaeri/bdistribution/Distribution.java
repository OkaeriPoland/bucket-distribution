package eu.okaeri.bdistribution;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class Distribution<T> implements Iterable {

    private final List<Bucket<T>> buckets;
    private final boolean balanced;

    /**
     * @param buckets  Distributed buckets
     * @param balanced True if all buckets are equals in size, false otherwise
     */
    public Distribution(List<Bucket<T>> buckets, boolean balanced) {
        this.buckets = buckets;
        this.balanced = balanced;
    }

    public List<Bucket<T>> getBuckets() {
        return this.buckets;
    }

    public Bucket<T> get(int i) {
        return this.buckets.get(i);
    }

    public boolean isBalanced() {
        return this.balanced;
    }

    @Override
    public Iterator<Bucket<T>> iterator() {
        return this.buckets.iterator();
    }

    @Override
    public void forEach(Consumer consumer) {
        //noinspection unchecked
        this.buckets.forEach(consumer::accept);
    }

    @Override
    public Spliterator<Bucket<T>> spliterator() {
        return this.buckets.spliterator();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Distribution.class.getSimpleName() + "[", "]")
                .add("buckets=" + this.buckets)
                .add("balanced=" + this.balanced)
                .toString();
    }
}