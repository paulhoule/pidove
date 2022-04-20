package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

class FlatMapIterable<X, Y> implements Iterable<Y> {

    private final Iterable<X> values;
    private final Function<X, ? extends Iterable<Y>> fn;
    Iterator<Y> current;

    public FlatMapIterable(Iterable<X> values, Function<X, ? extends Iterable<Y>> fn) {
        this.values = values;
        this.fn = fn;

        current = null;
    }

    @Override
    public Iterator<Y> iterator() {
        final Iterator<X> that = values.iterator();

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                while (true) {
                    if (current == null || !current.hasNext()) {
                        if (that.hasNext()) {
                            current = fn.apply(that.next()).iterator();
                        } else {
                            return false;
                        }
                    }

                    if (current.hasNext()) {
                        return true;
                    }
                }
            }

            @Override
            public Y next() {
                while (true) {
                    if (current == null || !current.hasNext()) {
                        if (that.hasNext()) {
                            current = fn.apply(that.next()).iterator();
                        } else {
                            throw new NoSuchElementException();
                        }
                    }

                    if (current.hasNext()) {
                        return current.next();
                    }
                }
            }
        };
    }
}
