package eu.okaeri.bdistribution;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.function.Consumer;

@ToString
@EqualsAndHashCode
public class Bucket<T> implements Iterable<T> {

    @Getter
    private final List<T> elements;

    /**
     * @param elements Bucket contents
     */
    public Bucket(List<T> elements) {
        this.elements = new ArrayList<>(elements);
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
    public void forEach(Consumer<? super T> consumer) {
        this.elements.forEach(consumer::accept);
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.elements.spliterator();
    }
}
