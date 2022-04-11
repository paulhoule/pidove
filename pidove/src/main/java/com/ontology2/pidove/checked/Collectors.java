package com.ontology2.pidove.checked;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.ontology2.pidove.checked.Iterables.*;

public class Collectors {

    public static <Input, Container, Intermediate, Result> Collector<Input, Container, Result> collectingAndThen(
            Collector<Input,Container,Intermediate> collector,Function<Intermediate, Result> finisher) {
        return Collector.of(collector.supplier(), collector.accumulator(), collector.finisher().andThen(finisher));
    }

    public static <Input, Container, Result> Collector<Input, Container, Result> collectingAndThen(
            SimpleCollector<Input,Container> collector,Function<Container, Result> finisher) {
        return Collector.of(collector.supplier(), collector.accumulator(), finisher);
    }

    public static <Input> Collector<Input, ?, Long> counting() {
        class Counter {
            private long value=0;
            public long get() { return value; }
            public void inc() { value += 1;}
        }
        return Collector.of(Counter::new, (item, cnt) -> cnt.inc(), Counter::get);
    }

    public static <T, K, A, D> Collector<T,?, Map<K,D>> groupingBy(
            Function<? super T,? extends K> classifier,
            Collector<? super T,A,D> downstream
    ) {
        return Collector.of(
                () -> new DefaultMap<>(new HashMap<K,A>(), downstream.supplier()),
                (item, collection) -> downstream.accumulator().accept(item,collection.get(classifier.apply(item))),
                (collection) -> asMap(map(over(collection), (
                        p -> new Pair<>(p.left(),downstream.finisher().apply(p.right())))
        )));
    }

    public static <T, K, D> SimpleCollector<T, Map<K,D>> groupingBy(
            Function<? super T,? extends K> classifier,
            SimpleCollector<? super T,D> downstream
    ) {
        return new SimpleCollector<>(
                () -> new DefaultMap<>(new HashMap<>(), downstream.supplier()),
                (item, collection) -> downstream.accumulator().accept(item,collection.get(classifier.apply(item))));
    }

    public static <X> Collector<X,Set<X>,Integer> countDistinct() {
        // return collectingAndThen(toSet(), Set::size);
        return Collectors.<X>toSet().andThen(Set::size);
    }
    public static <X,Y extends Collection<X>> SimpleCollector<X, Y> toCollection(Supplier<Y> source) {
        return new SimpleCollector<>(source, (item, container) -> container.add(item));
    }

    public static <X> SimpleCollector<X, List<X>> toList() {
        return toCollection(ArrayList::new);
    }

    public static <X> SimpleCollector<X, Set<X>> toSet() {
        return toCollection(HashSet::new);
    }


}
