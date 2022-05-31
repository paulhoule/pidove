package com.ontology2.pidove.util;

/**
 * Tuple of length 3.  Named a Trio instead of a Triple because it is not an RDF triple.
 *
 * @param left first value
 * @param middle second value
 * @param right third value
 * @param <X> type of first value
 * @param <Y> type of second value
 * @param <Z> type of third value
 */
public record Trio<X,Y,Z>(X left, Y middle, Z right) implements Tuple<X,Z> {
    /**
     *
     * @param left first value
     * @param middle middle value
     * @param right third value
     * @return a new pair
     * @param <A> type of first value
     * @param <B> type of second value
     * @param <C> type of third value
     */
    public static <A,B,C> Trio<A,B,C> of(A left, B middle, C right) {
        return new Trio<>(left,middle,right);
    }

    public int size() {
        return 3;
    }

    public X first() {
        return left();
    }

    public Z last() {
        return right();
    }
}
