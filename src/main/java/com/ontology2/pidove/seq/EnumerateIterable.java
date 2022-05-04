package com.ontology2.pidove.seq;

import com.ontology2.pidove.util.Pair;

import java.util.Iterator;
import java.util.NoSuchElementException;

class EnumerateIterable<X> implements TidyIterable<Pair<Long, X>> {
    private final Iterable<X> values;
    private final long start;

    EnumerateIterable(Iterable<X> values, long start) {
        this.values = values;
        this.start = start;
    }

    @Override
    public Iterator<Pair<Long, X>> iterator() {
        return new EnumerateIterator();
    }

    private class EnumerateIterator extends TidyIterator<X,Pair<Long, X>> {
        long counter = start;

        public EnumerateIterator() {
            super(values.iterator());
        }

        @Override
        public boolean hasNext() {
            return that.hasNext();
        }

        @Override
        public Pair<Long, X> next() {
            if(!that.hasNext())
                throw new NoSuchElementException();

            return new Pair<>(counter++, that.next());
        }
    }
}
