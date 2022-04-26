package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

class FilterIterable<X> extends TidyIterable<X> {
    private final Iterable<X> values;
    private final Predicate<X> predicate;

    public FilterIterable(Iterable<X> values, Predicate<X> predicate) {
        this.values = values;
        this.predicate = predicate;
    }

    @Override
    public Iterator<X> iterator() {
        return new FilterIterator(values);
    }

    private class FilterIterator extends TidyIterator<X,X> {
        X placeholder;
        boolean loadAhead;

        public FilterIterator(Iterable<X> source) {
            super(source.iterator());
            loadAhead = false;
        }

        @Override
        public boolean hasNext() {
            if (loadAhead)
                return true;

            while (that.hasNext()) {
                final var next = that.next();
                if (predicate.test(next)) {
                    placeholder = next;
                    loadAhead = true;
                    return true;
                }
            }
            return false;
        }

        @Override
        public X next() {
            if (loadAhead) {
                loadAhead = false;
                return placeholder;
            }

            while (that.hasNext()) {
                final var next = that.next();
                if (predicate.test(next)) {
                    return next;
                }
            }
            throw new NoSuchElementException();
        }
    }
}
