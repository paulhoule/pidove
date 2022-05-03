package com.ontology2.pidove.util;

public record Pair<X,Y>(X left, Y right) {
    public <Z> Triad<X,Y,Z> and(Z right) {
        return new Triad(left,this.right,right);
    }

    public Pair<Y,X> reverse() {
        return new Pair<>(right,left);
    };
}
