package com.ontology2.pidove.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Partial {
    public static <A,B,X> Function<B,X> partial(BiFunction<A, B, X> f, A a) {
        return b -> f.apply(a,b);
    }

    public static <A,B> Consumer<B> partial(BiConsumer<A, B> f, A a) {
        return b -> f.accept(a,b);
    }

    public static <A,B,C,X> BiFunction<B,C,X> partial(Function3<A, B, C, X> f, A a) {
        return (b,c) -> f.apply(a,b,c);
    }

    public static <A,B,C> BiConsumer<B,C> partial(Consumer3<A, B, C> f, A a) {
        return (b,c) -> f.accept(a,b,c);
    }

    public static <A,B,C,X> Function<C,X> partial(Function3<A, B, C, X> f, A a, B b) {
        return c -> f.apply(a,b,c);
    }

    public static <A,B,C> Consumer<C> partial(Consumer3<A, B, C> f, A a, B b) {
        return (c) -> f.accept(a,b,c);
    }

    public static <A,B,X> Function<A,X> partialR(BiFunction<A, B, X> f, B b) {
        return a -> f.apply(a,b);
    }

    public static <A,B> Consumer<A> partialR(BiConsumer<A, B> f, B b) {
        return a -> f.accept(a,b);
    }

    public static <A,B,C,X> BiFunction<A,B,X> partialR(Function3<A, B, C, X> f, C c) {
        return (a,b) -> f.apply(a,b,c);
    }

    public static <A,B,C> BiConsumer<A,B> partialR(Consumer3<A, B, C> f, C c) {
        return (a, b) -> f.accept(a,b,c);
    }

    public static <A,B,C,X> Function<A,X> partialR(Function3<A, B, C, X> f, B b, C c) {
        return a -> f.apply(a,b,c);
    }

    public static <A,B,C> Consumer<A> partialR(Consumer3<A, B, C> f, B b, C c) {
        return (a) -> f.accept(a,b,c);
    }
}
