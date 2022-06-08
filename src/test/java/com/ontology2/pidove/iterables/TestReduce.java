package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Iterables.reduce;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReduce {
    @Test
    public void reduceSum() {
        assertEquals(Optional.empty(),reduce(Integer::sum, List.of()));
        assertEquals(Optional.of(1), reduce(Integer::sum,List.of(1)));
        assertEquals(Optional.of(3175), reduce(Integer::sum,List.of(3100, 75)));

        assertEquals(0, reduce(0, Integer::sum, List.of()));
        assertEquals(8423, reduce(0, Integer::sum, List.of(8423)));
        assertEquals(4096, reduce(0, Integer::sum, List.of(4000, 90, 6)));
    }

    @Test
    public void reduceSum$() {
        assertEquals(Optional.of(4096), $(List.of(4000, 90, 6)).reduce(Integer::sum));
        assertEquals(4097, $(List.of(4000, 90, 6)).reduce(1, Integer::sum));
    }
}
