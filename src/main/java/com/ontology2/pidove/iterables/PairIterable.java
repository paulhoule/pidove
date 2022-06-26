package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface PairIterable<Left,Right> extends TidyIterable<Pair<Left,Right>> {
    default Map<Left, Right> toMap() {
        return Iterables.asMap(this);
    }

    default PairIterable<Left, Right> dropWhile(Predicate<? super Pair<Left, Right>> predicate) {
        return wrap(Iterables.dropWhile(predicate, this));
    }

    default PairIterable<Left, Right> filter(Predicate<? super Pair<Left, Right>> predicate) {
        return wrap(Iterables.filter(predicate, this));
    }

    default PairIterable<Left, Right> limit(int amount) {
        return wrap(Iterables.limit(amount, this));
    }

    default PairIterable<Left, Right> skip(int amount) {
        return wrap(Iterables.skip(amount, this));
    }

    default PairIterable<Left, Right> peek(Consumer<Pair<Left, Right>> consumer) {
        return wrap(Iterables.peek(consumer, this));
    }

    default PairIterable<Left, Right> takeWhile(Predicate<? super Pair<Left, Right>> predicate) {
        return wrap(Iterables.takeWhile(predicate, this));
    }


    static <Left, Right> PairIterable<Left, Right> wrap(Iterable<Pair<Left, Right>> input) {
        return new WrappedPairIterable<>(input);
    }

    default PairIterable<Left, Right> reversed() {
        return wrap(new ReversedIterable<>(this));
    }

    default PairIterable<Left,Right> accumulate(BinaryOperator<Pair<Left,Right>> func) {
        return wrap(new AccumulateIterable<>(func, this));
    }
}


