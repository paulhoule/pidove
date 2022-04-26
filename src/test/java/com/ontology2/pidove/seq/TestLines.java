package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.seq.Iterables.*;
import static com.ontology2.pidove.seq.Resources.resource;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLines {
    @Test
    public void yoursIsNoDisgrace() {
        var words = asList(over(() -> resource(this, "battleships.txt")));
        assertEquals(List.of("Battleships", "confide", "in", "me"), words);
    }
}
