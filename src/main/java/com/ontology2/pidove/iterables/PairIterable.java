package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface PairIterable<Left,Right> extends TidyIterable<Pair<Left,Right>> {
    default Map<Left,Right> toMap() {
        return Iterables.asMap(this);
    }
    default PairIterable<Left,Right> filter(Predicate<? super Pair<Left,Right>> predicate) {
            return wrap(Iterables.filter(predicate, this));
    }

    static <Left,Right> PairIterable<Left,Right> wrap(Iterable<Pair<Left,Right>> input) {
      return new WrappedPairIterable<>(input);
    }
}
