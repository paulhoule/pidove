package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.function.Supplier;

class GenerateIterable<X> implements Iterable<X> {
    private final Supplier<Supplier<X>> source;

    public GenerateIterable(Supplier<Supplier<X>> source) {
        this.source = source;
    }

    @Override
    public Iterator<X> iterator() {
        Supplier<X> that = source.get();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public X next() {
                return that.get();
            }
        };
    }
}
