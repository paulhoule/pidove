package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.ontology2.pidove.util.DuctTape.unchecked;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.function.Function.identity;

/**
 *
 * A CleanIterable promises that the Iterator returned by the iterator() method
 * also implements AutoClosable if that Iterator holds resources (just as open
 * files) that need to be closed
 *
 * @param <X>
 */
public interface TidyIterable<X> extends Iterable<X> {
    /**
     * Overrides the default implementation such that the Iterator used internally
     * will be closed at the end of this method if that Iterator implements
     * AutoClosable
     *
     * @param action The action to be performed for each element
     */
    @Override
    default void forEach(Consumer<? super X> action) {
        Iterables.forEach(action, this);
    }

    default <Y,Z> Z collect(Collector<? super X, Y, Z> collector) {
        return Iterables.collect(collector, this);
    }

    default long count() {
        return Iterables.count(this);
    }

    default TidyIterable<X> dropWhile(Predicate<? super X> predicate) {
        return Iterables.dropWhile(predicate, this);
    }

    default List<X> toList() {
        return Iterables.asList(this);
    }

    default Set<X> toSet() { return Iterables.asSet(this);}


    default Optional<X> first() {
        return Iterables.first(this);
    }

    default TidyIterable<X> filter(Predicate<? super X> predicate) {
        return Iterables.filter(predicate, this);
    }
    default <Y> TidyIterable<Y> flatMap(Function<X, ? extends Iterable<Y>> fn) {
        return new FlatMapIterable<>(this, fn);
    }

    default TidyIterable<X> limit(final int amount) {
        return new LimitIterable<>(this, amount);
    }

    default <Y> TidyIterable<Y> map(Function<X,Y> fn) {
        return new MapIterable<>(this, fn);
    }

    default Optional<X> max(Comparator<X> comparator) {
        return this.collect(Collectors.maxBy(comparator));
    }

    default Optional<X> min(Comparator<X> comparator) {
        return this.collect(Collectors.minBy(comparator));
    }

    default TidyIterable<X> peek(Consumer<X> listener) {
        return new PeekIterable<>(this, listener);
    }

    default Optional<X> reduce(BinaryOperator<X> accumulator) {
        return this.collect(Collectors.reducing(accumulator));
    }

    default X reduce(X identity,BinaryOperator<X> accumulator) {
        return this.collect(Collectors.reducing(identity, accumulator));
    }

    default TidyIterable<X> takeWhile(Predicate<? super X> predicate) {
        return new TakeWhileIterable<>(this, predicate);
    }

    default TidyIterable<X> skip(final int amount) {
        return new SkipIterable<>(this, amount);
    }

    default TidyIterable<Pair<Long,X>> enumerate() {
        return new EnumerateIterable<>(this,0L);
    }

    default TidyIterable<Pair<Long,X>> enumerate(long start) {
        return new EnumerateIterable<>(this,start);
    }

    default TidyIterable<X> reversed() {
        return new ReversedIterable(this);
    }

}
