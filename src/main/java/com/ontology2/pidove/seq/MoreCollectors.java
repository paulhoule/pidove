package com.ontology2.pidove.seq;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public class MoreCollectors {
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

    public static <X> Collector<X,?,Integer> countDistinct() {
        return collectingAndThen(toSet(), Set::size);
    }

    public static Collector<Character, StringBuilder, String> characters() {
        return Collector.of(StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append,
                StringBuilder::toString);
    }

//    public <X> static Collector<X super CharSequence, StringBuilder, String> joiningOn(String separator) {
//        return Collector.of(StringBuilder::new,
//                (a,x) -> a.append(x),
//                (a,b) -> a.append(b),
//                StringBuilder::toString);
//    }
}
