package com.ontology2.pidove.util;

import java.util.Objects;

import static com.ontology2.pidove.seq.Iterables.at;
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
            if(nonNull(d)) {
                return value;
            }
        }

        return at(-1,d);
    }
}
