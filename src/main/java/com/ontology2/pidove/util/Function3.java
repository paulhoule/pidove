package com.ontology2.pidove.util;

@FunctionalInterface
public interface Function3<A,B,C,X> {
    public X apply(A a,B b,C c);
}
