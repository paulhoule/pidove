package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ontology2.pidove.checked.Iterables.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestFirst {
    @Test
    public void getsAnOptional() {
        assertEquals(Optional.empty(), first(List.of()));
        assertEquals(Optional.of("boom"), first(List.of("boom")));
        assertEquals(Optional.of("freedom"), first(List.of("freedom","of","movement")));
    }

    @Test
    public void firstOrFails() {
        assertThrows(NoSuchElementException.class, () -> first(List.of()).orElseThrow());
        assertEquals("boom", first(List.of("boom")).orElseThrow());
        assertEquals("freedom", first(List.of("freedom","of","movement")).orElseThrow());
    }

    @Test
    public void firstOrWorks() {
        assertEquals((Integer) 812, first(List.<Integer>of()).orElse(812));
        assertEquals((Integer) 8, first(List.of(8, 1, 2)).orElse(812));
    }

    @Test
    public void firstOrNullWorks() {
        assertNull(first(List.of()).orElse(null));
        assertEquals(883, first(List.of(883, "foo", 11)).orElse(null));
    }
}
