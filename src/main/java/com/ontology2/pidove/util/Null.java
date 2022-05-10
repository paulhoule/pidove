package com.ontology2.pidove.util;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.ontology2.pidove.seq.Iterables.at;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Null {
    public static <X> X coalesce(X a,X b) {
        return nonNull(a) ? a : b;
    }

    public static <X> X coalesce(X a,X b, X c) {
        return coalesce(a,coalesce(b,c));
    }

    public static <X> X coalesce(X a,X b, X c,X... d) {
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

    public static<X,Y> Y safe(X x, Function<X, Y> f1) {
        if(isNull(x)) {
            return null;
        } else {
            return f1.apply(x);
        }
    }

    public static<X> void safe0(X x, Consumer<X> f1) {
        if(isNull(x)) {
            return;
        } else {
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
        if(isNull(x)) {
            return;
        } else {
            var y = f1.apply(x);
            if(isNull(y)) {
                return ;
            } else {
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
        if(isNull(x)) {
            return;
        } else {
            var y = f1.apply(x);
            if(isNull(y)) {
                return;
            } else {
                var z = f2.apply(y);
                if(isNull(z)) {
                    return;
                } else {
                    f3.accept(z);
                }
            }
        }
    }
}
