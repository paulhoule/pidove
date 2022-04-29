package com.ontology2.pidove.util;

public record Triad<X,Y,Z>(X left, Y middle, Z right) {
    public static <A,B,C> Triad<A,B,C> of(A left,B middle,C right) {
        return new Triad<>(left,middle,right);
    }
}
