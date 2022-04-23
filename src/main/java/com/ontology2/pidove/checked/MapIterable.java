package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.function.Function;

class MapIterable<X, Y> extends CleanIterable<Y> {
    private final Iterable<X> values;
    private final Function<X, Y> fn;

    public MapIterable(Iterable<X> values, Function<X, Y> fn) {
        this.values = values;
        this.fn = fn;
    }

    @Override
    public Iterator<Y> iterator() {
        return new MapIterator(values);
    }

    private class MapIterator extends AutoClosingIterator<X,Y> {

        public MapIterator(Iterable<X> source) {
            super(source.iterator());
        }

        @Override
        public boolean hasNext() {
            return that.hasNext();
        }

        @Override
        public Y next() {
            return fn.apply(that.next());
        }
    }
}
