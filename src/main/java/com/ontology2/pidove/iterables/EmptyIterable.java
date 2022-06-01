package com.ontology2.pidove.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

class EmptyIterable<X> implements Iterable<X> {

    private EmptyIterable() {}
    private static final EmptyIterable<?> that = new EmptyIterable<>();

    public static EmptyIterable<?> get() {
        return that;
    }

    @Override
    public Iterator<X> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public X next() {
                throw new NoSuchElementException();
            }
        };
    }
}