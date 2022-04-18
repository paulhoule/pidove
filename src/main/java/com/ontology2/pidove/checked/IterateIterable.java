package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.function.UnaryOperator;

class IterateIterable<X> implements Iterable<X> {
    private final X seed;
    private final UnaryOperator<X> f;

    public IterateIterable(X seed, UnaryOperator<X> f) {
        this.seed = seed;
        this.f = f;
    }

    @Override
    public Iterator<X> iterator() {
        return new Iterator<>() {
            X value = seed;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public X next() {
                try {
                    return value;
                } finally {
                    value = f.apply(value);
                }
            }
        };
    }
}
