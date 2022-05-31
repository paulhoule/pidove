package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.iterables.Iterables.accumulate;
import static com.ontology2.pidove.iterables.Iterables.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAccumulate {
    @Test
    public void cumulativeSum() {
        var x = List.of(7,65,21,8,19,145);
        var y = accumulate(Integer::sum, x);
        assertEquals(List.of(7,72,93,101,120,265), asList(y));
    }
}
