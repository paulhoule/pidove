package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

class FilterIterable<X> implements Iterable<X> {
    private final Iterable<X> values;
    private final Predicate<X> predicate;

    public FilterIterable(Iterable<X> values, Predicate<X> predicate) {
        this.values = values;
        this.predicate = predicate;
    }

    @Override
    public Iterator<X> iterator() {
        final var that = values.iterator();

        return new Iterator<>() {
            X placeholder;
            boolean loadAhead = false;

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
        };
    }
}
