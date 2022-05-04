package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.seq.Fixtures.closeSpy;
import static com.ontology2.pidove.seq.Iterables.*;
import static com.ontology2.pidove.seq.MoreCollectors.characters;
import static com.ontology2.pidove.util.DuctTape.lambda;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("CodeBlock2Expr")
public class TestCycle {
    @Test
    public void cycleZero() {
        var x = cycle(List.of());
        assertEquals(0L, count(x));
    }

    @Test
    public void cycleOne() {
        var x = cycle(List.of('*'));
        var s=collect(characters(), limit(5,x));
        assertEquals("*****", s);
    }

    @Test
    public void cycleTwo() {
        var x = cycle(List.of("deadbeat", "club"));
        var s=lambda((Integer n) -> { return collect(joining(" "), limit(n,x));} );
        assertEquals("", s.apply(0));
        assertEquals("deadbeat", s.apply(1));
        assertEquals("deadbeat club", s.apply(2));
        assertEquals("deadbeat club deadbeat", s.apply(3));
        assertEquals("deadbeat club deadbeat club", s.apply(4));
    }

    @Test
    public void cycleN() {
        var x = closeSpy(cycle(List.of(35, 19, 22)));
        var s=lambda((Integer n) -> { return sumInt(limit(n,x));} );
        assertEquals(263, s.apply(10));
        assert(x.isBalanced());
    }
}
