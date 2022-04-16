package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LimitIterable<X> implements Iterable<X> {

    private final Iterable<X> values;
    private final int amount;

    public LimitIterable(Iterable<X> values, int amount) {
        this.values = values;
        this.amount = amount;
    }

    @Override
    public Iterator<X> iterator() {
        final var that = values.iterator();
        return new Iterator<>() {
            int count;

            @Override
            public boolean hasNext() {
                return that.hasNext() && count < amount;
            }

            @Override
            public X next() {
                if (count > amount) {
                    throw new NoSuchElementException();
                }
                count++;
                return that.next();
            }
        };
    }
}
