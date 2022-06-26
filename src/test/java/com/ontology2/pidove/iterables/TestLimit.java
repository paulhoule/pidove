package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Fixtures.equalItemsAssert;
import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestLimit {
    @Test
    public void testLimit() {
        assertFalse(limit(100, List.of()).iterator().hasNext());
        var aList=List.of(1,2,3,4,5);
        assertEquals(aList, asList(limit(100, aList)));
        assertEquals(List.of(), asList(limit(0, aList)));
        assertEquals(List.of(1), asList(limit(1, aList)));
        assertEquals(List.of(1,2), asList(limit(2, aList)));
        assertEquals(List.of(1,2,3), asList(limit(3, aList)));
        assertEquals(List.of(1,2,3,4), asList(limit(4, aList)));
        assertEquals(List.of(1,2,3,4,5), asList(limit(5, aList)));
        assertEquals(List.of(1,2,3,4,5), asList(limit(6, aList)));
    }

    @Test
    public void testLimit$() {
        assertFalse(limit(100, List.of()).iterator().hasNext());
        var aList=List.of(1,2,3,4,5);
        assertEquals(List.of(1,2,3), $(aList).limit(3).toList());
    }

    @Test
    public void testSkip() {
        assertFalse(skip(100, List.of()).iterator().hasNext());
        var aList=List.of(1,2,3,4,5);
        assertEquals(List.of(), asList(skip(100, aList)));
        assertEquals(aList, asList(skip(0, aList)));
        assertEquals(List.of(2,3,4,5), asList(skip(1, aList)));
        assertEquals(List.of(3,4,5), asList(skip(2, aList)));
        assertEquals(List.of(4,5), asList(skip(3, aList)));
        assertEquals(List.of(5), asList(skip(4, aList)));
        assertEquals(List.of(), asList(skip(5, aList)));
    }

    @Test
    public void testSkip$() {
        var aList=List.of(1,2,3,4,5);
        assertEquals(List.of(), asList(skip(100, aList)));
        equalItemsAssert($(aList).skip(2), 3,4,5);
    }

    @Test
    public void testSkipFluentPair() {
        var aList=List.of('a','b','c','d','e');
        var bList = $(aList).enumerate().skip(3);
        equalItemsAssert($(bList), Pair.of(3L,'d'), Pair.of(4L, 'e'));
    }

    @Test
    public void testLimitFluentPair() {
        var aList=List.of('a','b','c','d','e');
        var bList = $(aList).enumerate().limit(2);
        equalItemsAssert($(bList), Pair.of(0L,'a'), Pair.of(1L, 'b'));
    }

}
