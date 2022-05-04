package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.function.Consumer;

class PeekIterable<X> implements TidyIterable<X> {
    private final Iterable<X> values;
    private final Consumer<X> listener;

    public PeekIterable(Iterable<X> values, Consumer<X> listener) {
        this.values = values;
        this.listener = listener;
    }

    @Override
    public Iterator<X> iterator() {
        return new PeekIterator(values);
    }

    private class PeekIterator extends TidyIterator<X, X> {

        public PeekIterator(Iterable<X> values) {
            super(values.iterator());
        }

        @Override
        public boolean hasNext() {
            return that.hasNext();
        }

        @Override
        public X next() {
            var value = that.next();
            listener.accept(value);
            return value;
        }
    }
}
