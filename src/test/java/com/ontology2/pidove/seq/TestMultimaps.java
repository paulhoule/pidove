package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.ontology2.pidove.seq.Iterables.distinct;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "RedundantOperationOnEmptyContainer"})
public class TestMultimaps {
    @Test
    public void testListMultimap() {
        var that = Maps.<String, Integer>mapOfLists();
        assertTrue(that.isEmpty());
        assertFalse(that.containsKey("one"));
        that.get("one").add(7);
        assertTrue(that.get("one").contains(7));
        assertEquals(1,that.get("one").size());
        that.get("one").addAll(List.of(3,11));
        assertEquals(List.of(7,3,11),that.get("one"));
        that.get("two").add(500);
        assertEquals(Set.of("one","two"),that.keySet());
        assertEquals(Set.of(List.of(7,3,11),List.of(500)),distinct(that.values()));
        that.get("two").add(500);
        assertEquals(Set.of(List.of(7,3,11),List.of(500, 500)),distinct(that.values()));
        // an example of what we're preventing by making this unsupported
        assertThrows(UnsupportedOperationException.class, () -> that.put("three", List.of()));
        that.remove("two");
        assertEquals(Set.of("one"),that.keySet());
    }

    @Test
    public void testSetMultimap() {
        var that = Maps.<String, Integer>mapOfSets();
        assertTrue(that.isEmpty());
        assertFalse(that.containsKey("one"));
        that.get("one").add(7);
        assertTrue(that.get("one").contains(7));
        assertEquals(1,that.get("one").size());
        that.get("one").addAll(List.of(3,11));
        assertEquals(Set.of(7,3,11),that.get("one"));
        that.get("two").add(500);
        assertEquals(Set.of("one","two"),that.keySet());
        assertEquals(Set.of(Set.of(7,3,11),Set.of(500)),distinct(that.values()));
        that.get("two").add(500);
        assertEquals(Set.of(Set.of(7,3,11),Set.of(500)),distinct(that.values()));
        // an example of what we're preventing by making this unsupported
        assertThrows(UnsupportedOperationException.class, () -> that.put("three", Set.of()));
        that.remove("two");
        assertEquals(Set.of("one"),that.keySet());
    }

    @Test
    public void testMapOfMaps() {
        var that = Maps.<String, String, Integer>mapOfMaps();
        assertTrue(that.isEmpty());
        assertFalse(that.containsKey("one"));
        that.get("one").put("red", 7);
        assertTrue(that.get("one").containsKey("red"));
        assertEquals(1,that.get("one").size());
        that.get("one").put("blue", 350);
        assertEquals(Set.of("red","blue"), that.get("one").keySet());
    }
}
