package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Fixtures.equalItemsAssert;
import static com.ontology2.pidove.iterables.Iterables.asList;
import static com.ontology2.pidove.iterables.Iterables.range;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRange {
    @Test
    public void ten() {
        assertEquals(List.of(0L,1L,2L,3L,4L,5L,6L,7L,8L,9L), asList(range(10)));
    }

    @Test
    public void ten$() {
        assertEquals(List.of(0L,1L,2L,3L,4L,5L,6L,7L,8L,9L), $(10).toList());
    }

    @Test
    public void eleven() {
        assertEquals(List.of(1L,2L,3L,4L,5L,6L,7L,8L,9L,10L), asList(range(1,11)));
    }

    @Test
    public void eleven$() {
        equalItemsAssert($(1,11),1L,2L,3L,4L,5L,6L,7L,8L,9L,10L);
    }

    @Test
    public void thirtyfive() {
        assertEquals(List.of(0L, 5L, 10L, 15L, 20L, 25L), $(0, 30, 5).toList());
    }

    @Test
    public void threes() {
        assertEquals(List.of(0L, 3L, 6L, 9L), asList(range(0, 10, 3)));
    }

    @Test
    public void minus() {
        assertEquals(List.of(0L,-1L,-2L,-3L,-4L,-5L,-6L,-7L,-8L,-9L), asList(range(0, -10, -1)));
    }

    @Test
    public void zero() {
        assertEquals(List.of(), asList(range(0)));
    }

    @Test
    public void nothing() {
        assertEquals(List.of(), asList(range(1, 0)));
    }


}
