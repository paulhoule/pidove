package com.ontology2.pidove.util;

import java.util.function.Function;

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
}
