package com.ontology2.pidove;

import com.ontology2.pidove.iterables.TidyIterable;

import java.util.Iterator;

public class TidyIterableWrapper<X> implements TidyIterable<X> {
    private final Iterable<X> that;

    public TidyIterableWrapper(Iterable<X> that) {
        this.that=that;
    }

    @Override
    public Iterator<X> iterator() {
        return that.iterator();
    }
}
