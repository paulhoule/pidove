package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;

import java.util.Iterator;

public class WrappedPairIterable<Left, Right> implements PairIterable<Left, Right> {
    private final Iterable<Pair<Left, Right>> input;

    public WrappedPairIterable(Iterable<Pair<Left, Right>> input) {
        this.input = input;
    }

    @Override
    public Iterator<Pair<Left, Right>> iterator() {
        return input.iterator();
    }
}
