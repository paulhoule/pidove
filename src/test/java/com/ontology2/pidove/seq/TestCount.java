package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import static com.ontology2.pidove.seq.Fixtures.closeSpy;
import static com.ontology2.pidove.seq.Iterables.count;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCount {
    @Test
    public void zeroOneTwoThree() {
        assertEquals(0, count(of()));
        assertEquals(1, count(of(1)));
        assertEquals(2, count(of(1,2)));
        assertEquals(3, count(of(1,2,3)));
    }

    @Test
    public void countCloses() {
        var instrumented = closeSpy(of());
        assertEquals(0, count(instrumented));
        assertEquals(1, instrumented.getCloseCount());
    }
}
