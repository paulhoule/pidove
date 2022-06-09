package com.ontology2.pidove.iterables;

import static com.ontology2.pidove.iterables.Dollar.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Fixtures {
    public static <X> CloseSpyIterable<X> closeSpy(Iterable<X> that) {
        return new CloseSpyIterable<>(that);
    }

    /**
     * Reversed in order from the usual assert but that lets us use varags.  I put assert at the end of
     * the name to reflect that it is reversed.
     *
     * @param actual the result we are testing
     * @param xs all the items that actual should return
     * @param <X> the type of the Iterable
     */
    @SafeVarargs
    public static <X> void equalItemsAssert(TidyIterable<X> actual, X... xs) {
        assertEquals($(xs).toList(),actual.toList());
    }
}
