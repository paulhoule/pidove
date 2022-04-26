package com.ontology2.pidove.util;

import java.util.function.Supplier;

public class Suppliers {
    public static <X> Supplier<X> constant(X value) {
        return ()->value;
    }

    public static <X> Supplier<X> iterable(Iterable<X> value) {
        return value.iterator()::next;
    }
}
