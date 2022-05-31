package com.ontology2.pidove.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayIterable<X> implements Iterable<X> {

    private final X[] x;

    @SafeVarargs
    public ArrayIterable(X... x) {
        this.x = x;
    }

    @Override
    public Iterator<X> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < x.length;
            }

            @Override
            public X next() {
                if (i < x.length) {
                    return x[i++];
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
