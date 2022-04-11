package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ontology2.pidove.checked.Iterables.*;
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
}
