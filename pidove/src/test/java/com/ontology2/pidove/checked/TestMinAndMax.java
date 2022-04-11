package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.ontology2.pidove.checked.Iterables.max;
import static com.ontology2.pidove.checked.Iterables.min;
import static java.util.Comparator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMinAndMax {
    @Test
    public void testMin() {
        assertEquals(Optional.of(2), min(naturalOrder(), List.of(11, 7, 2, 4)));
        assertEquals(Optional.empty(), min(naturalOrder(), List.<Integer>of()));
        assertEquals(Optional.of("disney"), min(naturalOrder(), List.of("walt","disney","world")));
        assertEquals(Optional.of("world"), min(reverseOrder(), List.of("walt","disney","world")));
    }

    @Test
    public void testDefault() {
        assertEquals(Optional.of(2), min(List.of(11, 7, 2, 4)));
        assertEquals(Optional.empty(), min(List.<Integer>of()));
        assertEquals(Optional.of("disney"), min(List.of("walt","disney","world")));
        assertEquals(Optional.of(11), max(List.of(11, 7, 2, 4)));
        assertEquals(Optional.empty(), max(List.<Integer>of()));
        assertEquals(Optional.of("world"), max(List.of("walt","disney","world")));
    }

    @Test
    public void testMax() {
        assertEquals(Optional.of(11), max(List.of(11, 7, 2, 4), naturalOrder()));
        assertEquals(Optional.empty(), max(List.<Integer>of(), naturalOrder()));
        assertEquals(Optional.of("world"), max(List.of("walt","disney","world"), naturalOrder()));
        assertEquals(Optional.of("disney"), max(List.of("walt","disney","world"), reverseOrder()));
    }
}
