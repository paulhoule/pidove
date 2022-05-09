package com.ontology2.pidove.util;

public record Pair<X,Y>(X left, Y right) {
    public <Z> Trio<X,Y,Z> and(Z right) {
        return new Trio<>(left,this.right,right);
    }

    public Pair<Y,X> reverse() {
        return new Pair<>(right,left);
    }
}
