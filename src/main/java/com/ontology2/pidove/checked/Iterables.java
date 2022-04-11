package com.ontology2.pidove.checked;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        return new Iterable<>() {
            @Override
            public Iterator<X> iterator() {
                return new Iterator<>() {
                    int i = 0;
                    Iterator<X> nextIterator = null;

                    @Override
                    public boolean hasNext() {
                        while(true) {
                            if(nextIterator==null) {
                                if(i==values.length)
                                    return false;

                                nextIterator=values[i++].iterator();
                            }

                            if(nextIterator.hasNext()) {
                                return true;
                            } else {
                                nextIterator=null;
                            }
                        }
                    }

                    @Override
                    public X next() {
                        while(true) {
                            if(nextIterator==null) {
                                if(i==values.length)
                                    throw new NoSuchElementException();

                                nextIterator=values[i++].iterator();
                            }

                            if(nextIterator.hasNext()) {
                                return nextIterator.next();
                            } else {
                                nextIterator=null;
                            }
                        }

                    }
                };
            }
        };
    }

    public static <X,Y,Z> Z collect(Collector<X, Y, Z> collector, Iterable<X> values) {
        var container = collector.supplier().get();
        for(var value: values) {
            collector.accumulator().accept(value, container);
        }
        return collector.finisher().apply(container);
    }

    public static <X,Z> Z collect(SimpleCollector<X, Z> collector, Iterable<X> values) {
        var container = collector.supplier().get();
        for(var value: values) {
            collector.accumulator().accept(value, container);
        }
        return container;
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
        return new Iterable<>() {
            @Override
            public Iterator<X> iterator() {
                final var that = values.iterator();

                return new Iterator<>() {
                    X placeholder;
                    boolean loadAhead=false;

                    @Override
                    public boolean hasNext() {
                        if(loadAhead)
                            return true;

                        while(that.hasNext()) {
                            final var next = that.next();
                            if(predicate.test(next)) {
                                placeholder = next;
                                loadAhead = true;
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public X next() {
                        if(loadAhead) {
                            loadAhead=false;
                            return placeholder;
                        }

                        while(that.hasNext()) {
                            final var next = that.next();
                            if(predicate.test(next)) {
                                return next;
                            }
                        }
                        throw new NoSuchElementException();
                    }
                };
            }
        };
    }

    public static <X,Y> Iterable<Y> flatMap(Function<X, Iterable<Y>> fn, Iterable<X> values) {
        return new Iterable<>() {
            final Iterator<X> that = values.iterator();
            Iterator<Y> current=null;

            @Override
            public Iterator<Y> iterator() {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        while(true) {
                            if(current==null || !current.hasNext()) {
                                if(that.hasNext()) {
                                    current = fn.apply(that.next()).iterator();
                                } else {
                                    return false;
                                }
                            }

                            if(current.hasNext()) {
                                return true;
                            }
                        }
                    }

                    @Override
                    public Y next() {
                        while(true) {
                            if(current==null || !current.hasNext()) {
                                if(that.hasNext()) {
                                    current = fn.apply(that.next()).iterator();
                                } else {
                                    throw new NoSuchElementException();
                                }
                            }

                            if(current.hasNext()) {
                                return current.next();
                            }
                        }
                    }
                };
            }
        };
    }

    public static <X> Iterable<X> generate(Supplier<Supplier<X>> source) {
        return new Iterable<>() {
            @Override
            public Iterator<X> iterator() {
                Supplier<X> that = source.get();
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public X next() {
                        return that.get();
                    }
                };
            }
        };
    }

    public static <X> Iterable<X> limit(final int amount, Iterable<X> values) {
        return new Iterable<>() {

            @Override
            public Iterator<X> iterator() {
                final var that = values.iterator();
                return new Iterator<>() {
                    int count;

                    @Override
                    public boolean hasNext() {
                        return that.hasNext() && count<amount;
                    }

                    @Override
                    public X next() {
                        if(count>amount) {
                            throw new NoSuchElementException();
                        }
                        count++;
                        return that.next();
                    }
                };
            }
        };
    }

    public static <X,Y> Iterable<Y> map(Iterable<X> values, Function<X,Y> fn) {

        return new Iterable<>() {
            @Override
            public Iterator<Y> iterator() {
                final var that = values.iterator();
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return that.hasNext();
                    }

                    @Override
                    public Y next() {
                        return fn.apply(that.next());
                    }
                };
            }
        };
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
        return new Iterable<>() {

            @Override
            public Iterator<X> iterator() {
                return new Iterator<>() {
                    boolean ready=true;

                    @Override
                    public boolean hasNext() {
                        return ready;
                    }

                    @Override
                    public X next() {
                        if(ready) {
                            ready=false;
                            return x;
                        } else {
                            throw new NoSuchElementException();
                        }
                    }
                };
            }
        };
    }

    @SafeVarargs
    public static <X> Iterable<X> of(final X ...x) {
        return new Iterable<>() {

            @Override
            public Iterator<X> iterator() {
                return new Iterator<>() {
                    int i=0;

                    @Override
                    public boolean hasNext() {
                        return i<x.length;
                    }

                    @Override
                    public X next() {
                        if(i<x.length) {
                            return x[i++];
                        } else {
                            throw new NoSuchElementException();
                        }
                    }
                };
            }
        };
    }

    public static Iterable<Character> over(String s) {
        return new Iterable<>() {

            @Override
            public Iterator<Character> iterator() {
                return new Iterator<>() {
                    int i=0;

                    @Override
                    public boolean hasNext() {
                        return i<s.length();
                    }

                    @Override
                    public Character next() {
                        if(i<s.length()) {
                            return s.charAt(i++);
                        } else {
                            throw new NoSuchElementException();
                        }
                    }
                };
            }
        };
    }

    public static <X> Iterable<X> over(final X[] x) {
        return new Iterable<>() {

            @Override
            public Iterator<X> iterator() {
                return new Iterator<>() {
                    int i=0;

                    @Override
                    public boolean hasNext() {
                        return i<x.length;
                    }

                    @Override
                    public X next() {
                        if(i<x.length) {
                            return x[i++];
                        } else {
                            throw new NoSuchElementException();
                        }
                    }
                };
            }
        };
    }

    public static <X,Y> Iterable<Pair<X,Y>> over(final Map<X,Y> that) {
        return map(that.entrySet(), e->new Pair<>(e.getKey(), e.getValue()));
    }

    public static <X extends Comparable<X>> Optional<X> min(final Iterable<X> values) {
        return min(Comparator.naturalOrder(), values);
    }

    public static <X> Iterable<X> peek(Consumer<X> listener, final Iterable<X> values) {
        return new Iterable<>() {
            @Override
            public Iterator<X> iterator() {
                Iterator<X> that = values.iterator();

                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return that.hasNext();
                    }

                    @Override
                    public X next() {
                        var value = that.next();
                        listener.accept(value);
                        return value;
                    }
                };
            }
        };
    }

    public static <X> Iterable<X> skip(final Iterable<X> values, final int amount) {
        return new Iterable<>() {
            @Override
            public Iterator<X> iterator() {
                var that = values.iterator();
                for(int i=0;i<amount;i++) {
                    if(that.hasNext()) {
                        that.next();
                    }
                }
                return new Iterator<>() {

                    @Override
                    public boolean hasNext() {
                        return that.hasNext();
                    }

                    @Override
                    public X next() {
                        return that.next();
                    }
                };
            }
        };
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
