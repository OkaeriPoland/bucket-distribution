package eu.okaeri.bdistribution;

import java.util.*;
import java.util.function.Consumer;

public class Bucket<T> implements Iterable {

    private final List<T> elements;

    /**
     * @param elements Bucket contents
     */
    public Bucket(List<T> elements) {
        this.elements = new ArrayList<>(elements);
    }

    public List<T> getElements() {
        return this.elements;
    }

    public int size() {
        return this.elements.size();
    }

    public T get(int i) {
        return this.elements.get(i);
    }

    @Override
    public Iterator<T> iterator() {
        return this.elements.iterator();
    }

    @Override
    public void forEach(Consumer consumer) {
        //noinspection unchecked
        this.elements.forEach(consumer::accept);
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.elements.spliterator();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bucket.class.getSimpleName() + "[", "]")
                .add(this.elements.toString())
                .toString();
    }
}