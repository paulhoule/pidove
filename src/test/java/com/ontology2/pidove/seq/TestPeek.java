package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ontology2.pidove.seq.Fixtures.closeSpy;
import static com.ontology2.pidove.seq.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPeek {
    @Test
    public void giveItAPeek() {
        var sink = new ArrayList<Character>();
        var pasta = over("pasta");
        var honey = List.of('p','a','s','t','a');
        assertEquals(honey, asList(peek(sink::add, pasta)));
        assertEquals(honey, sink);
    }

    @Test
    public void andItCloses() {
        var instrumented = closeSpy(List.of());
        assertEquals(0, count(peek(x-> {}, instrumented)));
        assertEquals(1, instrumented.getCloseCount());
    }
}
