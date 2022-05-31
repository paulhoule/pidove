package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;

import java.util.Iterator;
import java.util.NoSuchElementException;

record PairwiseIterable<X>(Iterable<X> values) implements Iterable<Pair<X, X>> {
    @Override
    public Iterator<Pair<X, X>> iterator() {
        return new PairwiseIterator();
    }

    private class PairwiseIterator extends TidyIterator<X,Pair<X, X>> {
        boolean startState=true;
        X lastValue;

        public PairwiseIterator() {
            super(values.iterator());
        }

        @Override
        public boolean hasNext() {
            if(startState) {
                if(that.hasNext()) {
                    lastValue = that.next();
                    startState=false;
                } else {
                    return false;
                }
            }

            return that.hasNext();
        }

        @Override
        public Pair<X, X> next() {
            if(startState) {
                if(that.hasNext()) {
                    lastValue = that.next();
                    startState=false;
                } else {
                    throw new NoSuchElementException();
                }
            }
            var thisValue = that.next();
            try {
                return new Pair<>(lastValue, thisValue);
            } finally {
                lastValue=thisValue;
            }
        }
    }
}
