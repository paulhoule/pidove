package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.seq.Iterables.all;
import static com.ontology2.pidove.seq.Iterables.any;
import static com.ontology2.pidove.seq.Iterables.none;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAnyAndAll {
    @Test
    void all001() {
        assertTrue(all(List.of(true,true,true)));
        assertFalse(all(List.of(false,true,true)));
        assertFalse(all(List.of(true,false,true)));
        assertFalse(all(List.of(true,true,false)));
    }

    @Test
    void any001() {
        assertTrue(any(List.of(true,true,true)));
        assertTrue(any(List.of(false,true,true)));
        assertTrue(any(List.of(true,false,true)));
        assertTrue(any(List.of(true,true,false)));
        assertTrue(any(List.of(false,false,true)));
        assertTrue(any(List.of(false,true,false)));
        assertTrue(any(List.of(true,false,false)));
        assertFalse(any(List.of(false,false,false)));
    }

    @Test
    void none001() {
        assertFalse(none(List.of(true,true,true)));
        assertFalse(none(List.of(false,true,true)));
        assertFalse(none(List.of(true,false,true)));
        assertFalse(none(List.of(true,true,false)));
        assertFalse(none(List.of(false,false,true)));
        assertFalse(none(List.of(false,true,false)));
        assertFalse(none(List.of(true,false,false)));
        assertTrue(none(List.of(false,false,false)));
    }
}
