package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.seq.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCompress {
    @Test
    public void compressZero() {
        assertEquals(0, count(compress(List.of(), List.of())));
    }

    @Test
    public void compressOne() {
        assertEquals(0, count(compress(repeat(5,false), List.of(7,5,4,2,1))));
    }

    @Test
    public void compressTwo() {
        assertEquals(19, sumInt(compress(repeat(5,true), List.of(7,5,4,2,1))));
    }

    @Test
    public void compressThree() {
        var ecks = List.of(false, true, false);
        var zee = List.of("a","b","c");
        assertEquals(List.of("b"), asList(compress(ecks, zee)));
    }
}
