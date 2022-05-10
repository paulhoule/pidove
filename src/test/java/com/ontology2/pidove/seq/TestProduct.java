package com.ontology2.pidove.seq;

import com.ontology2.pidove.util.Pair;
import com.ontology2.pidove.util.Trio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.seq.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProduct {
    @Test
    public void twoByThree() {
        var p = asList(product(List.of(1,2),List.of('A','B','C')));
        assertEquals(6,p.size());
        assertEquals(Pair.of(1,'A'),p.get(0));
        assertEquals(Pair.of(1,'B'),p.get(1));
        assertEquals(Pair.of(1,'C'),p.get(2));
        assertEquals(Pair.of(2,'A'),p.get(3));
        assertEquals(Pair.of(2,'B'),p.get(4));
        assertEquals(Pair.of(2,'C'),p.get(5));
    }

    @Test
    public void empty2() {
        var p = asList(product(List.of(),List.of()));
        assertEquals(List.of(),p);
    }

    @Test
    public void emptyLeft() {
        var p = asList(product(List.of(),List.of(7,8,9)));
        assertEquals(List.of(),p);
    }

    @Test
    public void emptyRight() {
        var p = asList(product(List.of(5,6,7),List.of()));
        assertEquals(List.of(),p);
    }

    @Test
    public void oneLeft() {
        var p = asList(product(List.of("ooo"),List.of(7,8,9)));
        assertEquals(List.of(
                Pair.of("ooo",7),
                Pair.of("ooo", 8),
                Pair.of("ooo", 9)
        ),p);
    }

    @Test
    public void oneRight() {
        var p = asList(product(List.of(5,6,7),List.of("xxx")));
        assertEquals(List.of(
                Pair.of(5,"xxx"),
                Pair.of(6,"xxx"),
                Pair.of(7,"xxx")
        ),p);
    }

    @Test
    public void oneThousand() {
        var digit = range(10);
        var result = asList(product(digit,digit,digit));
        assertEquals(1000, result.size());
        assertEquals(Trio.of(0L,0L,0L), result.get(0));
        assertEquals(Trio.of(0L,0L,1L), result.get(1));
        assertEquals(Trio.of(0L,5L,2L), result.get(52));
        assertEquals(Trio.of(3L,0L,8L), result.get(308));
        assertEquals(Trio.of(9L,9L,9L), result.get(999));
    }

    @Test
    public void empty3() {
        var nothing = empty();
        var result = asList(product(nothing,nothing,nothing));
        assertEquals(0, result.size());
    }
}
