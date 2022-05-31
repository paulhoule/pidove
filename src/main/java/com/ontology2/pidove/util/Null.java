package com.ontology2.pidove.util;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.ontology2.pidove.iterables.Iterables.at;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Functions for handling nulls
 *
 */
public class Null {
    /**
     * <a href="https://en.wikipedia.org/wiki/Null_coalescing_operator">Null coalescing operator</a>
     *
     * returns the first argument that isn't null or null if all arguments are null.
     *
     *
     * @param a a value of type a
     * @param b a value of type b
     * @return a if a is not null otherwise b
     * @param <X> the return data type
     */
    public static <X> X coalesce(X a,X b) {
        return nonNull(a) ? a : b;
    }

    public static <X> X coalesce(X a,X b, X c) {
        return coalesce(a,coalesce(b,c));
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Null_coalescing_operator">Null coalescing operator</a>
     *
     * Since all the values are the same type this can be implemented for an arbitrary number of values with
     * varargs
     *
     * @param a first value
     * @param b second value
     * @param c third value
     * @param d more arguments...
     * @return the first non-null value.
     * @param <X> the returned data type
     */
    @SafeVarargs
    public static <X> X coalesce(X a, X b, X c, X... d) {
        final var that = coalesce(a,b,c);
        if(nonNull(that)) {
            return that;
        }

        for(final var value:d) {
            if(nonNull(value)) {
                return value;
            }
        }

        return null;
    }

    /**
     * Safe navigation operator.
     *
     * If x is null returns null,  otherwise applies f1 to x.  Longer variants apply
     * any functions that occur in later arguments,  doing the null check each time.
     *
     * @param x input value
     * @param f1 function that can be applied to x
     * @return null if x is null,  otherwise f(x).
     * @param <X> input type
     * @param <Y> output type
     */
    public static<X,Y> Y safe(X x, Function<X, Y> f1) {
        if(isNull(x)) {
            return null;
        } else {
            return f1.apply(x);
        }
    }

    /**
    * Safe navigation operator.
    *
    * If x is null does nothing.  If x is not null,  makes f1 accept x.
    *
    * Longer variants apply functions in series if the result is not null,  finally making
    * the Consumer passed as the last argument accepting that result if it is not null
    *
    * @param x input value
    * @param f1 consumer that can accept x
    * @param <X> input type
    */

    public static<X> void safe0(X x, Consumer<X> f1) {
        if (!isNull(x)) {
            f1.accept(x);
        }
    }

    public static<X,Y,Z> Z safe(X x, Function<X, Y> f1, Function<Y,Z> f2) {
        if(isNull(x)) {
            return null;
        } else {
            var y = f1.apply(x);
            if(isNull(y)) {
                return null;
            } else {
                return f2.apply(y);
            }
        }
    }

    public static<X,Y> void safe0(X x, Function<X, Y> f1, Consumer<Y> c) {
        if (!isNull(x)) {
            var y = f1.apply(x);
            if (!isNull(y)) {
                c.accept(y);
            }
        }
    }

    public static<X,Y,Z,ZZ> ZZ safe(X x, Function<X, Y> f1, Function<Y,Z> f2,Function<Z,ZZ> f3) {
        if(isNull(x)) {
            return null;
        } else {
            var y = f1.apply(x);
            if(isNull(y)) {
                return null;
            } else {
                var z = f2.apply(y);
                if(isNull(z)) {
                    return null;
                } else {
                    return f3.apply(z);
                }
            }
        }
    }

    public static<X,Y,Z> void safe0(X x, Function<X, Y> f1, Function<Y,Z> f2,Consumer<Z> f3) {
        if (!isNull(x)) {
            var y = f1.apply(x);
            if (!isNull(y)) {
                var z = f2.apply(y);
                if (!isNull(z)) {
                    f3.accept(z);
                }
            }
        }
    }
}
