package com.ontology2.pidove.iterables;

import java.util.Iterator;
import java.util.function.BinaryOperator;

record AccumulateIterable<X>(BinaryOperator<X> func, Iterable<X> values) implements TidyIterable<X> {

    @Override
    public Iterator<X> iterator() {
        return new AccumulateIterator();
    }

    private class AccumulateIterator extends TidyIterator<X, X> {
        boolean isFirst=true;
        X accumulator;

        public AccumulateIterator() {
            super(values.iterator());
        }

        @Override
        public boolean hasNext() {
            return that.hasNext();
        }

        @Override
        public X next() {
            if(isFirst) {
                accumulator = that.next();
                isFirst=false;
            } else {
                accumulator=func.apply(accumulator, that.next());
            }
            return accumulator;
        }
    }
}
