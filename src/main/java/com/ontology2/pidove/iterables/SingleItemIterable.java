package com.ontology2.pidove.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

class SingleItemIterable<X> implements Iterable<X> {

    private final X x;

    public SingleItemIterable(X x) {
        this.x = x;
    }

    @Override
    public Iterator<X> iterator() {
        return new Iterator<>() {
            boolean ready = true;

            @Override
            public boolean hasNext() {
                return ready;
            }

            @Override
            public X next() {
                if (ready) {
                    ready = false;
                    return x;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
