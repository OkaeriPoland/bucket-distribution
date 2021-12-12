package eu.okaeri.bdistribution;

import lombok.Getter;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Getter
@ToString
public class Distribution<T> implements Iterable<Bucket<T>> {

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

    public Bucket<T> get(int i) {
        return this.buckets.get(i);
    }

    @Override
    public Iterator<Bucket<T>> iterator() {
        return this.buckets.iterator();
    }

    @Override
    public void forEach(Consumer<? super Bucket<T>> consumer) {
        this.buckets.forEach(consumer::accept);
    }

    @Override
    public Spliterator<Bucket<T>> spliterator() {
        return this.buckets.spliterator();
    }
}
