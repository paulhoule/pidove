package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.ontology2.pidove.iterables.Iterables.distinct;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDistinct {
    @Test
    public void isDistinct() {
        var original = List.of(1,1,5,1,2,3,2,3,1,5);
        var unique = Set.of(1,2,3,5);
        assertEquals(unique, distinct(original));
    }

}
