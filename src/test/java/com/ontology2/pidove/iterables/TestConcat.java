package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ontology2.pidove.iterables.Fixtures.closeSpy;
import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestConcat {
    @Test
    public void concatEmpty() {
        var nothing=concat();
        assertFalse(nothing.iterator().hasNext());
        assertThrows(NoSuchElementException.class, () -> nothing.iterator().next());
    }

    @Test
    public void concatSingle() {
        var nothing = concat(List.of("a", "b", "c"));
        var something = nothing.iterator();
        assertEquals("a", something.next());
        assertEquals("b", something.next());
        assertEquals("c", something.next());
        assertThrows(NoSuchElementException.class, something::next);
    }

    @Test
    public void concatTwo() {
        var that = asList(concat(List.of("a", "b", "c"),List.of("d")));
        assertEquals(List.of("a","b","c","d"), that);
    }

    @Test
    public void concatThree() {
        var that = asList(concat(List.of("a", "b", "c"),List.of(),List.of("x","y")));
        assertEquals(List.of("a","b","c","x","y"), that);
    }

    @Test
    public void concatFour() {
        var that = asList(concat(List.of("a", "b", "c"),List.of(),List.of("x","y")));
        assertEquals(List.of("a","b","c","x","y"), that);
    }

    @Test
    public void emptyLeft() {
        var that = asList(concat(List.of(),List.of("x","y")));
        assertEquals(List.of("x","y"), that);
    }

    @Test
    public void emptyRight() {
        var that = asList(concat(List.of("a", "b", "c"),List.of()));
        assertEquals(List.of("a","b","c"), that);
    }

    @Test
    public void concatCloses() {
        var one = closeSpy(List.of("i", "said", "goodbye"));
        var two = closeSpy(List.of("who", "are", "you"));
        assertEquals(6, count(concat(one,two)));
        assertEquals(1, one.getCloseCount());
        assertEquals(1, two.getCloseCount());
    }
}
