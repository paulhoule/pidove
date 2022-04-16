package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterable<X> implements Iterable<X> {
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
