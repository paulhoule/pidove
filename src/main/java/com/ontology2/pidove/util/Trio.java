package com.ontology2.pidove.util;

public record Trio<X,Y,Z>(X left, Y middle, Z right) {
    public static <A,B,C> Trio<A,B,C> of(A left, B middle, C right) {
        return new Trio<>(left,middle,right);
    }
}
