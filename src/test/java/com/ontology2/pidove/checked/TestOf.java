package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static com.ontology2.pidove.checked.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestOf {
    @Test
    public void singleOf() {
        assertEquals(List.of(34), asList(Iterables.of(34)));
    }

    @Test
    public void multipleOf() {
        assertEquals(List.of(34, 11, 19), asList(Iterables.of(34, 11, 19)));
    }

    @Test
    public void iterateOverStrings() {
        assertFalse(first(over("")).isPresent());
        assertEquals(List.of('h','u','n','g','e','r'), asList(over("hunger")));
    }

    @Test
    public void iterateOverArrays() {
        assertFalse(first(over(new String[] {})).isPresent());
        assertEquals(List.of(5,0,5), asList(over(new Integer[] {5,0,5})));
    }

    @Test
    public void iterateOverMap() {
        var that = new HashMap<Integer, String>();
        that.put(3, "three");
        that.put(4, "four");
        that.put(5, "five");
        var hits = asList(over(that));
        assertEquals(3, hits.size());
        assertEquals(that, asMap(hits));
    }

    @Test
    public void ofNullableIsEmptyIfNullable() {
        var x = ofNullable(null);
        assertEquals(0,count(x));
    }

    @Test
    public void ofNullableWithValue() {
        var x = ofNullable("twenty-five");
        assertEquals(List.of("twenty-five"), asList(x));
    }

}
