package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ontology2.pidove.checked.Iterables.concat;
import static com.ontology2.pidove.checked.Iterables.asList;
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
}
