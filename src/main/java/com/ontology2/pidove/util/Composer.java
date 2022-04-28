package com.ontology2.pidove.util;

import java.util.function.*;

/**
 * The ideal grammar of compose is that the first argument is a Supplier, Function, or BiFunction.  In
 * the middle there could be an arbitrary number of functions,  at the end there either a Function,
 * Consumer, or Predicate.  That gives 9 basic forms,  and these could be extended to some length N
 * of intervening functions.
 *
 * To support,  say N=10 this is a job for code generation.
 *
 */
public class Composer {
    public static <A,B,X> Function<A,B> compose(Function<A,X> f1, Function<X,B> f2) {
        return f1.andThen(f2);
    }

    public static <A,B,X1,X2> Function<A,B> compose(Function<A,X1> f1, Function<X1,X2> f2, Function<X2,B> f3) {
        return f1.andThen(f2).andThen(f3);
    }

    public static <A,B,X1,X2,X3> Function<A,B> compose(Function<A,X1> f1, Function<X1,X2> f2, Function<X2,X3> f3,
                                                Function<X3,B> f4) {
        return f1.andThen(f2).andThen(f3).andThen(f4);
    }

    public static <A,B> Supplier<B> compose(Supplier<A> f1, Function<A,B> f2) {
        return () -> f2.apply(f1.get());
    }

    /**
     *
     * Note that the generics solver can't figure out
     *
     * compose(x->-x,intConsumer).get(10)
     *
     */
    public static <A,B> Consumer<A> compose(Function<A,B> f1, Consumer<B> f2) {
        return (x) -> f2.accept(f1.apply(x));
    }

    public static <A,B,X,C> BiFunction<A,B,C> compose(BiFunction<A,B,X> f1, Function<X,C> f2) {
        return (t,u) -> f2.apply(f1.apply(t,u));
    }

    /**
     * Function&lt;X,Boolean&gt; is ambiguous with Predicate&lt;X&gt; so we need to change the name of this
     * function so it doesn't conflict with ordinary compose.
     *
     * @param f1 first function
     * @param f2 second function, a predicate
     * @return true or false depending on predicate
     * @param <A> input type
     * @param <B> inner type of predicate
     */
    public static <A,B> Predicate<A> composePredicate(Function<A,B> f1, Predicate<B> f2) {
        return (x) -> f2.test(f1.apply(x));
    }

    public static <A,B,X> BiPredicate<A,B> composePredicate(BiFunction<A,B,X> f1, Predicate<X> f2) {
        return (t, u) -> f2.test(f1.apply(t, u));
    }
}
