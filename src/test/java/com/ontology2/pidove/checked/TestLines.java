package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.ontology2.pidove.checked.Iterables.*;
import static com.ontology2.pidove.checked.Resources.resource;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLines {
    @Test
    public void yoursIsNoDisgrace() {
        var words = asList(over(() -> resource(this, "battleships.txt")));
        assertEquals(List.of("battleships", "confide", "in", "me"), words);
    }
}
