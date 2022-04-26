package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.seq.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIterate {
    @Test
    public void countUp() {
        var counter = iterate(0, x -> x+1);
        assertEquals(List.of(0,1,2,3,4), asList(limit(5,counter)));
    }

    @Test
    public void powersOfTwo() {
        var counter = iterate(1L, x -> x * 2L);
        var that = asList(limit(40, counter));
        assertEquals(1L, that.get(0));
        assertEquals(8L, that.get(3));
        assertEquals(34359738368L,that.get(35));
    }

}
