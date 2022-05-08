package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static com.ontology2.pidove.seq.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPairwise {
    @Test
    public void emptyCase() {
        assertEquals(0,count(pairwise(List.of())));
    }

    @Test
    public void singleCase() {
        assertEquals(0,count(pairwise(List.of("blue"))));
    }

    @Test
    public void squares() {
        var squares = map(x->x*x, range(0,10));
        var d2 = difference(difference(squares));
        var grouped = collect(Collectors.groupingBy(x->x,Collectors.counting()), d2);
        assertEquals(1, grouped.size());
        assertEquals(8, grouped.get(2L));
    }

    @Test
    public void cubes() {
        var cubes = map(x->x*x*x, range(10,20));
        var q = asList(difference(cubes));
        assertEquals(List.of(331L, 397L, 469L, 547L, 631L, 721L, 817L, 919L, 1027L), q);
        var s = asList(difference(q));
        assertEquals(List.of(66L, 72L, 78L, 84L, 90L, 96L, 102L, 108L), s);
        var t = asList(difference(s));
        assertEquals(asList(repeat(7, 6L)), t);
    }

    Iterable<Long> difference(Iterable<Long> x) {
        return map(p->p.right()-p.left(), pairwise(x));
    }
}
