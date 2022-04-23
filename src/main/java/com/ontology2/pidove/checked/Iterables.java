package com.ontology2.pidove.checked;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.ontology2.pidove.checked.Exceptions.uncheck;
import static java.util.Objects.isNull;
import static java.util.function.Function.*;

public class Iterables {
    public static boolean all(Iterable<Boolean> values) {
        Iterator<Boolean> that = values.iterator();
        try {
            while (that.hasNext()) {
                if (!that.next())
                    return false;
            }

            return true;
        } finally {
            close(that);
        }
    }

    public static boolean any(Iterable<Boolean> values) {
        Iterator<Boolean> that = values.iterator();
        try {
            for(boolean b: values) {
                if(b)
                    return true;
            }

            return false;
        } finally {
            close(that);
        }
    }

    @SafeVarargs
    public static <X> Iterable<X> concat(Iterable<X>... values) {
        return new ConcatIterable<>(values);
    }

    public static <X,Y,Z> Z collect(Collector<X, Y, Z> collector, Iterable<X> values) {
        var container = collector.supplier().get();
        var that = values.iterator();
        forEach((X item) -> collector.accumulator().accept(container, item) , values);

        if(collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            //noinspection unchecked
            return (Z) container;
        }
        return collector.finisher().apply(container);
    }

    public static <X> long count(Iterable<X> values) {
        long i=0;
        var that = values.iterator();
        for(X any:values) {
            i++;
        }
        return i;
    }

    public static <X> Iterable<X> distinct(Iterable<X> values) {
        return asSet(values);
    }

    public static <X> Iterable<X> dropWhile(Predicate<? super X> predicate, Iterable<X> values) {
        return new DropWhileIterable<>(values, predicate);
    }

    public static Iterable<?> empty() {
        return EmptyIterable.get();
    }

    public static <X> Optional<X> first(Iterable<X> values) {
        var that = values.iterator();
        if(that.hasNext()) {
            return Optional.of(that.next());
        } else {
            return Optional.empty();
        }
    }

    public static <X> Iterable<X> filter(Predicate<X> predicate, Iterable<X> values) {
        return new FilterIterable<>(values, predicate);
    }

    public static <X> Iterable<X> flatten(Iterable<? extends Iterable<X>> values) {
        return flatMap(identity(), values);
    }

    public static <X,Y> Iterable<Y> flatMap(Function<X, ? extends Iterable<Y>> fn, Iterable<X> values) {
        return new FlatMapIterable<>(values, fn);
    }

    public static <X> Iterable<X> generate(Supplier<Supplier<X>> source) {
        return new GenerateIterable<>(source);
    }

    public static <X> Iterable<X> iterate(X seed, UnaryOperator<X> f) {
        return new IterateIterable<>(seed, f);
    }

    public static <X> Iterable<X> limit(final int amount, Iterable<X> values) {
        return new LimitIterable<>(values, amount);
    }

    public static <X,Y> Iterable<Y> map(Function<X,Y> fn, Iterable<X> values) {
        return new MapIterable<>(values, fn);
    }

    public static <X> Optional<X> max(final Iterable<X> values, Comparator<X> comparator) {
        Optional<X> that=Optional.empty();
        for(X item: values) {
            if(that.isEmpty()) {
                that = Optional.of(item);
            } else {
                if(comparator.compare(item, that.get())>0) {
                    that = Optional.of(item);
                }
            }
        }
        return that;
    }

    public static <X extends Comparable<X>> Optional<X> max(final Iterable<X> values) {
        return max(values, Comparator.naturalOrder());
    }

    public static <X> Optional<X> min(Comparator<X> comparator, final Iterable<X> values) {
        Optional<X> that=Optional.empty();
        for(X item: values) {
            if(that.isEmpty()) {
                that = Optional.of(item);
            } else {
                if(comparator.compare(item, that.get())<0) {
                    that = Optional.of(item);
                }
            }
        }
        return that;
    }

    public static boolean none(final Iterable<Boolean> values) {
        for(boolean item: values) {
            if(item)
                return false;
        }
        return true;
    }

    public static <X> Iterable<X> of(final X x) {
        return new SingleItemIterable<>(x);
    }

    @SafeVarargs
    public static <X> Iterable<X> of(final X ...x) {
        return new ArrayIterable<>(x);
    }

    public static <X> Iterable<X> ofNullable(final X x) {
        //noinspection unchecked
        return isNull(x) ? (Iterable<X>) EmptyIterable.get() : new SingleItemIterable<>(x);
    }

    public static Iterable<Character> over(CharSequence s) {
        return new CharSequenceIterable(s);
    }

    public static <X> Iterable<X> over(final X[] x) {
        return new ArrayIterable<>(x);
    }

    public static <X,Y> Iterable<Pair<X,Y>> over(final Map<X,Y> that) {
        return map(e->new Pair<>(e.getKey(), e.getValue()), that.entrySet());
    }

    public static <X extends Comparable<X>> Optional<X> min(final Iterable<X> values) {
        return min(Comparator.naturalOrder(), values);
    }

    public static <X> Iterable<X> peek(Consumer<X> listener, final Iterable<X> values) {
        return new PeekIterable<>(values, listener);
    }

    public static Iterable<Long> range(long stop) {
        return range(0, stop, 1);
    }

    public static Iterable<Long> range(long start, long stop) {
        return range(start, stop, 1);
    }

    /**
     * This is like the range function from the Python standard library
     *
     * @param start the beginning of the range
     * @param stop iteration stops after we are equal to sto, greater, or less depending on if step is positive or negative
     * @param skip how much we add for each iteration
     * @return an iterable that counts from start to stop with skip as stride
     */
    public static Iterable<Long> range(long start, long stop, long skip) {
        return new RangeIterable(start, skip, stop);
    }

    public static <X> Optional<X> reduce(BinaryOperator<X> accumulator, final Iterable<X> values) {
        return collect(Collectors.reducing(accumulator), values);
    }

    public static <X> X reduce(X identity,BinaryOperator<X> accumulator, final Iterable<X> values) {
        return collect(Collectors.reducing(identity, accumulator), values);
    }

    public static <X> Iterable<X> skip(final int amount, final Iterable<X> values) {
        return new SkipIterable<>(values, amount);
    }

    public static long sum(Iterable<Integer> values) {
        long i=0;
        for(int any:values) {
            i += any;
        }
        return i;
    }

    public static <X> Iterable<X> takeWhile(Predicate<? super X> predicate, Iterable<X> values) {
        return new TakeWhileIterable<>(values, predicate);
    }

    public static <X> List<X> asList(Iterable<X> values) {
        return collect(Collectors.toList(), values);
    }

    public static <X> void forEach(Consumer<X> action, Iterable<X> values) {
        var source = values.iterator();
        try {
            while (source.hasNext()) {
                action.accept(source.next());
            }
        } finally {
            close(source);
        }
    }

    public static <X> void forEach(Predicate<X> action, Iterable<X> values) {
        var source = values.iterator();
        try {
            while (source.hasNext()) {
                if(!action.test(source.next())) {
                    break;
                }
            }
        } finally {
            close(source);
        }
    }

    public static <X> Set<X> asSet(Iterable<X> values) {
        return collect(Collectors.toSet(), values);
    }

    public static <X,Y> Map<X,Y> asMap(Iterable<Pair<X,Y>> pairs) {
        return collect(Collectors.toMap(Pair::left, Pair::right), pairs);
    }

    /**
     * Given an arbitrary object,  calls the close method if it is AutoCloseable,  otherwise
     * do nothing
     *
     * @param that any Object
     * @throws RuntimeException if the AutoCloseable#close method is called and throws one
     */
    public static void close(Object that) throws RuntimeException {
        if(that instanceof AutoCloseable ac) {
            uncheck(ac::close);
        }
    }
}
