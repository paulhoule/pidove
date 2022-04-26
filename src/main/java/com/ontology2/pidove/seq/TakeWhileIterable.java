package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

class TakeWhileIterable<X> extends TidyIterable<X> {
    private final Iterable<X> values;
    private final Predicate<? super X> predicate;

    public TakeWhileIterable(Iterable<X> values, Predicate<? super X> predicate) {
        this.values = values;
        this.predicate = predicate;
    }

    @Override
    public Iterator<X> iterator() {
        return new TakeWhileIterator(values);
    }

    private class TakeWhileIterator extends TidyIterator<X, X> {
        public TakeWhileIterator(Iterable<X> that) {
            super(that.iterator());
        }
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        Optional<X> nextValue;

        {
            updateNextValue();
        }



        private void updateNextValue() {
            if(that.hasNext()) {
                var x = that.next();
                if(predicate.test(x)) {
                    nextValue = Optional.of(x);
                    return;
                }
            }
            nextValue=Optional.empty();
        }

        @Override
        public boolean hasNext() {
            return nextValue.isPresent();
        }

        @Override
        public X next() {
            if(nextValue.isEmpty()) {
                throw new NoSuchElementException();
            }
            try {
                return nextValue.get();
            } finally {
                updateNextValue();
            }
        }
    }
}
