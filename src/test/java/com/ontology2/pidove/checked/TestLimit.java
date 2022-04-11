package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.checked.Iterables.*;
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
    public void testSkip() {
        assertFalse(skip(List.of(), 100).iterator().hasNext());
        var aList=List.of(1,2,3,4,5);
        assertEquals(List.of(), asList(skip(aList, 100)));
        assertEquals(aList, asList(skip(aList, 0)));
        assertEquals(List.of(2,3,4,5), asList(skip(aList, 1)));
        assertEquals(List.of(3,4,5), asList(skip(aList, 2)));
        assertEquals(List.of(4,5), asList(skip(aList, 3)));
        assertEquals(List.of(5), asList(skip(aList, 4)));
        assertEquals(List.of(), asList(skip(aList, 5)));
    }
}
