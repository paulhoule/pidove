package com.ontology2.pidove.seq;

import java.util.Iterator;

class SkipIterable<X> implements TidyIterable<X> {
    private final Iterable<X> values;
    private final int amount;

    public SkipIterable(Iterable<X> values, int amount) {
        this.values = values;
        this.amount = amount;
    }

    @Override
    public Iterator<X> iterator() {
        return new SkipIterator(values, amount);
    }

    private class SkipIterator extends TidyIterator<X, X> {

        public SkipIterator(Iterable<X> values, int amount) {
            super(values.iterator());
            for (int i = 0; i < amount; i++) {
                if (that.hasNext()) {
                    that.next();
                }
            }
        }

        @Override
        public boolean hasNext() {
            return that.hasNext();
        }

        @Override
        public X next() {
            return that.next();
        }
    }
}
