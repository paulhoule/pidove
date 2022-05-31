package com.ontology2.pidove.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LimitIterable<X> implements TidyIterable<X> {

    private final Iterable<X> values;
    private final int amount;

    public LimitIterable(Iterable<X> values, int amount) {
        this.values = values;
        this.amount = amount;
    }

    @Override
    public Iterator<X> iterator() {
        return new LimitIterator(values);
    }

    private class LimitIterator extends TidyIterator<X,X> {
        int count;

        public LimitIterator(Iterable<X> values) {
            super(values.iterator());
        }

        @Override
        public boolean hasNext() {
            return that.hasNext() && count < amount;
        }

        @Override
        public X next() {
            if (count > amount) {
                throw new NoSuchElementException();
            }
            count++;
            return that.next();
        }
    }
}
