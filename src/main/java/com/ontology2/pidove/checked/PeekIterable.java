package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.function.Consumer;

class PeekIterable<X> implements Iterable<X> {
    private final Iterable<X> values;
    private final Consumer<X> listener;

    public PeekIterable(Iterable<X> values, Consumer<X> listener) {
        this.values = values;
        this.listener = listener;
    }

    @Override
    public Iterator<X> iterator() {
        Iterator<X> that = values.iterator();

        return new Iterator<>() {
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
        };
    }
}
