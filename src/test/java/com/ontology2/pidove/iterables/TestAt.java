package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestAt {
    @Test
    public void fromArray() {
        String[] a = "How does it feel?".split(" ");
        assertEquals("How", at(0, a));
        assertEquals("does", at(1, a));
        assertEquals("it", at(-2, a));
        assertEquals("feel?", at(-1, a));
        //noinspection ResultOfMethodCallIgnored
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> at(153, a));
    }

    @Test
    public void fromList() {
        List<String> l = asList(splitOn(" ","I like the way you werk it."));
        assertEquals("I", at(0, l));
        assertEquals("like", at(1, l));
        assertEquals("werk", at(-2, l));
        assertEquals("it.", at(-1, l));
        assertThrows(IndexOutOfBoundsException.class, () -> at(-44, l));
    }

    @Test
    public void fromLinkedList() {
        List<String> l = new LinkedList<>();
        splitOn(" ","I like the way you werk it.").forEach(l::add);
        assertEquals("I", at(0, l));
        assertEquals("like", at(1, l));
        assertEquals("I", at(-7, l));
        assertEquals("werk", at(-2, l));
        assertEquals("it.", at(-1, l));
        assertThrows(IndexOutOfBoundsException.class, () -> at(-8, l));
    }

    //
    // For now range returns a generic iterator but now that I think about it
    // range could reasonably be a list
    //
    @Test
    public void fromRange() {
        var r = range(10,15);
        assertEquals(10, at(0, r));
        assertEquals(11, at(1, r));
        assertEquals(12, at(2, r));
        assertEquals(13, at(3, r));
        assertEquals(14, at(4, r));
        assertEquals(10, at(-5, r));
        assertEquals(11, at(-4, r));
        assertEquals(12, at(-3, r));
        assertEquals(13, at(-2, r));
        assertEquals(14, at(-1, r));
        assertThrows(IndexOutOfBoundsException.class, () -> at(5, r));
        assertThrows(IndexOutOfBoundsException.class, () -> at(-6, r));
    }

    @Test
    public void fromEmpty() {
        var r = empty();
        assertThrows(IndexOutOfBoundsException.class, () -> at(0,r));
        assertThrows(IndexOutOfBoundsException.class, () -> at(1,r));
        assertThrows(IndexOutOfBoundsException.class, () -> at(-1,r));
    }

    @Test
    public void testTail() {
        //
        // range is a TidyIterable
        //
        var items = range(0,15);
        var tail3 = asList(tail(3,items));
        assertEquals(3, tail3.size());
        assertEquals(12L,tail3.get(0));
        assertEquals(13L,tail3.get(1));
        assertEquals(14L,tail3.get(2));
        assertEquals(List.of(12L,13L,14L), tail3);
        assertEquals(0,count(tail(0,items)));
        assertEquals(asList(items),asList(tail(16,items)));
    }

    @Test
    public void testTailList() {
        var items = asList(range(0,15));
        var tail3 = asList(tail(3,items));
        assertEquals(3, tail3.size());
        assertEquals(12L,tail3.get(0));
        assertEquals(13L,tail3.get(1));
        assertEquals(14L,tail3.get(2));
        assertEquals(List.of(12L,13L,14L), tail3);
        assertEquals(0,count(tail(0,items)));
        assertEquals(asList(items),asList(tail(16,items)));
    }

    //
    // Here we are counting on the LinkedList to behave as a generic container
    // for which we know the length so we know how many elements to skip
    // past to get to the one we want.
    //
    @Test
    public void testContainerList() {
        var items = new LinkedList<Long>();
        range(0,15).forEach(items::add);
        var tail3 = asList(tail(3,items));
        assertEquals(3, tail3.size());
        assertEquals(12L,tail3.get(0));
        assertEquals(13L,tail3.get(1));
        assertEquals(14L,tail3.get(2));
        assertEquals(List.of(12L,13L,14L), tail3);
        assertEquals(0,count(tail(0,items)));
        assertEquals(asList(items),asList(tail(16,items)));
    }
}