package com.ontology2.pidove.iterables;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public class MoreCollectors {
    /**
     * Applies a Flat Mapping on top of a collector,  that is, causes a collector to accept multiple values
     * returned by the mapper as an Iterable&lt;U&gt; as individual values of type U.
     *
     * @param mapper a function that can return 0 or more values for every input value
     * @param downstream a collector that accepts values from the mapper
     * @return a collector
     * @param <T> type of the stream that this collector takes input from
     * @param <U> the type returned by the mapper
     * @param <A> type of the accumulator used by the innter collector
     * @param <R> the final value returned by the the inner collector (and this collector)
     */
    public static <T, U, A, R> Collector<T,?,R> flatMapping(
            Function<? super T,? extends Iterable<? extends U>> mapper,
            Collector<? super U,A,R> downstream) {
        BiConsumer<A, ? super U> a = downstream.accumulator();

        var characteristics = downstream.characteristics().toArray(new Collector.Characteristics[0]);
        return Collector.of(
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

    /**
     *
     * @return a collector that returns the number of distinct values that appear in the input iterable
     * @param <X> the type of the input iterable
     *
     */
    public static <X> Collector<X,?,Integer> countDistinct() {
        return collectingAndThen(toSet(), Set::size);
    }

    /**
     *
     * @return a collector that concatenates characters into a string
     */
    public static Collector<Character, StringBuilder, String> characters() {
        return Collector.of(StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append,
                StringBuilder::toString);
    }
}
