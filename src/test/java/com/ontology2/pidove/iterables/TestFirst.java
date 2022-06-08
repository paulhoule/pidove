package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Fixtures.closeSpy;
import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestFirst {
    @Test
    public void getsAnOptional() {
        assertEquals(Optional.empty(), first(List.of()));
        assertEquals(Optional.of("boom"), first(List.of("boom")));
        assertEquals(Optional.of("freedom"), first(List.of("freedom","of","movement")));
    }

    @Test
    public void getsAnOptional$() {
        assertEquals(Optional.of("boom"), $(List.of("boom")).first());
        assertEquals(Optional.of("freedom"), $(List.of("freedom","of","movement")).first());
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

    @Test
    public void andItCloses() {
        var instrumented = closeSpy(over("change is happening"));
        assertEquals('c', first(instrumented).orElseThrow());
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void overChars() {
        assertEquals('t',$("terrific").first().orElseThrow());
    }
}
