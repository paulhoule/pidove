package com.ontology2.pidove.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

public record RepeatIterable<X>(long times, X value) implements Iterable<X> {

    @Override
    public Iterator<X> iterator() {
        return new RepeatIterator();
    }

    private class RepeatIterator implements Iterator<X> {
        private long count;

        @Override
        public boolean hasNext() {
            return count<times;
        }

        @Override
        public X next() {
            if(!hasNext())
                throw new NoSuchElementException();

            try {
                return value;
            } finally {
                count++;
            }
        }
    }
}
