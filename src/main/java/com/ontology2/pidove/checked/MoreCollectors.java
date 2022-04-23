package com.ontology2.pidove.checked;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public class MoreCollectors {
    public static <T, U, A, R> Collector<T,?,R> flatMapping(
            Function<? super T,? extends Iterable<? extends U>> mapper,
            Collector<? super U,A,R> downstream) {
        BiConsumer<A, ? super U> a = downstream.accumulator();

        var characteristics = downstream.characteristics().toArray(new Collector.Characteristics[0]);
        return Collector.<T, A, R>of(
                downstream.supplier(),
                (A r, T t) -> {
                    Iterable<? extends U> result = mapper.apply(t);
                    if (result != null)
                        result.forEach(u -> a.accept(r, u));
                },
                downstream.combiner(),
                downstream.finisher(),
                characteristics);
    }

    public static <X> Collector<X,?,Integer> countDistinct() {
        return collectingAndThen(toSet(), Set::size);
    }
}