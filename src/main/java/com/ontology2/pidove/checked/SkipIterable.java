package com.ontology2.pidove.checked;

import java.util.Iterator;

class SkipIterable<X> implements Iterable<X> {
    private final Iterable<X> values;
    private final int amount;

    public SkipIterable(Iterable<X> values, int amount) {
        this.values = values;
        this.amount = amount;
    }

    @Override
    public Iterator<X> iterator() {
        var that = values.iterator();
        for (int i = 0; i < amount; i++) {
            if (that.hasNext()) {
                that.next();
            }
        }
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return that.hasNext();
            }

            @Override
            public X next() {
                return that.next();
            }
        };
    }
}
