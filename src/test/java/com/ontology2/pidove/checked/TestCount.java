package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.ontology2.pidove.checked.Iterables.count;
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
}
