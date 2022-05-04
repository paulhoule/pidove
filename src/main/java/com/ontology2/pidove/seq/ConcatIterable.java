package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ConcatIterable<X> implements TidyIterable<X> {
    private final Iterable<X>[] values;

    @SafeVarargs
    public ConcatIterable(Iterable<X>... values) {
        this.values = values;
    }

    @Override
    public Iterator<X> iterator() {
        return new Iterator<>() {
            int i = 0;
            Iterator<X> nextIterator = null;

            @Override
            public boolean hasNext() {
                while (true) {
                    if (nextIterator == null) {
                        if (i == values.length)
                            return false;

                        nextIterator = values[i++].iterator();
                    }

                    if (nextIterator.hasNext()) {
                        return true;
                    } else {
                        Iterables.close(nextIterator);
                        nextIterator = null;
                    }
                }
            }

            @Override
            public X next() {
                while (true) {
                    if (nextIterator == null) {
                        if (i == values.length)
                            throw new NoSuchElementException();

                        nextIterator = values[i++].iterator();
                    }

                    if (nextIterator.hasNext()) {
                        return nextIterator.next();
                    } else {
                        Iterables.close(nextIterator);
                        nextIterator = null;
                    }
                }
            }
        };
    }
}
