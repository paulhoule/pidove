package com.ontology2.pidove.checked;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Iterables {
    public static boolean all(Iterable<Boolean> values) {
        for(boolean b: values) {
            if(!b)
                return false;
        }

        return true;
    }

    public static boolean any(Iterable<Boolean> values) {
        for(boolean b: values) {
            if(b)
                return true;
        }

        return false;
    }

    @SafeVarargs
    public static <X> Iterable<X> concat(Iterable<X>... values) {
        return new ConcatIterable<>(values);
    }

    public static <X,Y,Z> Z collect(Collector<X, Y, Z> collector, Iterable<X> values) {
        var container = collector.supplier().get();
        for(var value: values) {
            collector.accumulator().accept(container,value);
        }
        if(collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return (Z) container;
        }
        return collector.finisher().apply(container);
    }

    public static <X> long count(Iterable<X> values) {
        long i=0;
        for(X any:values) {
            i++;
        }
        return i;
    }

    public static <X> Iterable<X> distinct(Iterable<X> values) {
        return asSet(values);
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

    public static <X,Y> Iterable<Y> flatMap(Function<X, Iterable<Y>> fn, Iterable<X> values) {
        return new FlatMapIterable<>(values, fn);
    }

    public static <X> Iterable<X> generate(Supplier<Supplier<X>> source) {
        return new GenerateIterable<>(source);
    }

    public static <X> Iterable<X> limit(final int amount, Iterable<X> values) {
        return new LimitIterable<>(values, amount);
    }

    public static <X,Y> Iterable<Y> map(Iterable<X> values, Function<X,Y> fn) {
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

    public static Iterable<Character> over(CharSequence s) {
        return new CharSequenceIterable(s);
    }

    public static <X> Iterable<X> over(final X[] x) {
        return new ArrayIterable<>(x);
    }

    public static <X,Y> Iterable<Pair<X,Y>> over(final Map<X,Y> that) {
        return map(that.entrySet(), e->new Pair<>(e.getKey(), e.getValue()));
    }

    public static <X extends Comparable<X>> Optional<X> min(final Iterable<X> values) {
        return min(Comparator.naturalOrder(), values);
    }

    public static <X> Iterable<X> peek(Consumer<X> listener, final Iterable<X> values) {
        return new PeekIterable<>(values, listener);
    }

    public static <X> Iterable<X> skip(final Iterable<X> values, final int amount) {
        return new SkipIterable<>(values, amount);
    }

    public static long sum(Iterable<Integer> values) {
        long i=0;
        for(int any:values) {
            i += any;
        }
        return i;
    }

    public static <X> List<X> asList(Iterable<X> values) {
        var that = new ArrayList<X>();
        for(X x:values) {
            that.add(x);
        }
        return that;
    }

    public static <X> Set<X> asSet(Iterable<X> values) {
        var that = new HashSet<X>();
        for(X x:values) {
            that.add(x);
        }
        return that;
    }

    public static <X,Y> Map<X,Y> asMap(Iterable<Pair<X,Y>> pairs) {
        var that = new HashMap<X,Y>();
        for(Pair<X,Y> item:pairs) {
            that.put(item.left(), item.right());
        }
        return that;
    }

}
