package com.ontology2.pidove.util;

/**
 * Tuple of length 2
 *
 * @param left first value
 * @param right second value
 * @param <X> type of first value
 * @param <Y> type of second value
 */

public record Pair<X,Y>(X left, Y right) implements Tuple<X,Y> {
    /**
     * static factory method
     *
     * @param l first value
     * @param r second value
     * @return new pair
     * @param <X> type of first value
     * @param <Y> type of second value
     */
    public static <X,Y> Pair<X,Y> of(X l,Y r) {
        return new Pair<>(l,r);
    }

    /**
     * Adds a third value to make this a Trio.  The current right value becomes the middle
     *
     * @param right new value
     * @return a Trio where the first two values come from here and the third value is the one passed in
     * @param <Z> type of the new value
     */
    public <Z> Trio<X,Y,Z> and(Z right) {
        return new Trio<>(left,this.right,right);
    }

    /**
     *
     * @return a new pair with the left and right values switched
     */
    public Pair<Y,X> reverse() {
        return new Pair<>(right,left);
    }

    public int size() {
        return 2;
    }

    public X first() {
        return left();
    }

    public Y last() {
        return right();
    }
}
