package com.ontology2.pidove.util;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Curry is both awkward and cute in Java.  With code generation it would be reasonable to
 * extend curry to (say) arity 20.
 */
public class Curry {
    public static <A,B,C> Function<A, Function<B,C>> curry(
            BiFunction<A,B,C> f
    ) {
        return (A a) -> (B b) -> f.apply(a,b);
    }

    public static <A,B,C,X> Function<A, Function<B,Function<C,X>>> curry(
            Function3<A,B,C, X> f
    ) {
        return (A a) -> (B b) -> (C c) -> f.apply(a,b,c);
    }

    /**
     * Uncurry is tricky because of type erasure:  (1) you can't tell the difference betweeen uncurry methods
     * that differ only by generic parameters.  To answer this I unerased the type information by
     * introducing Curry2, Curry3, ...
     *
     * It gets worse because (2) uncurry methods for Curry2,  Curry3, ... are still ambigious because the
     * compiler doesn't know when to stop uncurrying.  I kept CurryN interfaces because it is easier to
     * write Curry3 then spell out the nested function.
     *
     * @param f a function of A that returns a function of B that returns C
     * @return a bifunction that takes A and B and gives C
     * @param <A> first input type
     * @param <B> second input type
     * @param <C> output
     */
    public static <A,B,C> BiFunction<A,B,C> uncurry(Curry2<A, B, C> f) {
        return (a,b) -> f.apply(a).apply(b);
    }


    public static <A,B,C,X> Function3<A,B,C,X> uncurry3(Curry3<A,B,C,X> f) {
        return (a,b,c) -> f.apply(a).apply(b).apply(c);
    }

}
