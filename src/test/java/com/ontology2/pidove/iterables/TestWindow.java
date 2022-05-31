package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWindow {
    @Test
    public void noWindow() {
        assertEquals(0, count(window(3, Collectors.toList(), empty())));
    }

    @Test
    public void oneWindow() {
        assertEquals(List.of(), asList(window(3, Collectors.toList(), range(1))));
    }

    @Test
    public void twoWindow() {
        assertEquals(List.of(), asList(window(3, Collectors.toList(), range(2))));
    }

    @Test
    public void threeWindow() {
        assertEquals(List.of(List.of(0L,1L,2L)), asList(window(3, Collectors.toList(), range(3))));
    }

    @Test
    public void fourWindow() {
        assertEquals(List.of(List.of(0L,1L,2L),List.of(1L,2L,3L)),
                asList(window(3, Collectors.toList(), range(4))));
    }

    @Test
    public void bigWindow() {
        var sums = asList(window(3, Collectors.summingLong(x->x), range(10)));
        var shouldbe = List.of(
                3L, 6L, 9L, 12L, 15L, 18L, 21L, 24L
        );
        assertEquals(shouldbe, sums);
    }
}
