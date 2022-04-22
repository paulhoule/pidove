package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

class DropWhileIterable<X> implements Iterable<X> {
    private final Iterable<X> values;
    private final Predicate<? super X> predicate;

    public DropWhileIterable(Iterable<X> values, Predicate<? super X> predicate) {
        this.values = values;
        this.predicate = predicate;
    }

    @Override
    public Iterator<X> iterator() {
        return new DropWhileIterator(values);
    }

    private class DropWhileIterator extends AutoClosingIterator<X> {
        public DropWhileIterator(Iterable<X> that) {
            super(that.iterator());
        }
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        Optional<X> nextValue = Optional.empty();

        {
            while (that.hasNext()) {
                var value = that.next();
                if (!predicate.test(value)) {
                    nextValue = Optional.of(value);
                    break;
                }
            }
        }
        @Override
        public boolean hasNext() {
            return nextValue.isPresent();
        }

        @Override
        public X next() {
            if (nextValue.isEmpty()) {
                throw new NoSuchElementException();
            }

            try {
                return nextValue.get();
            } finally {
                if (that.hasNext()) {
                    nextValue = Optional.of(that.next());
                } else {
                    nextValue = Optional.empty();
                }
            }
        }
    }
}
