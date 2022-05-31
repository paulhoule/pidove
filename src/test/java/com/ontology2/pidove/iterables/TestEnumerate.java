package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;
import org.junit.jupiter.api.Test;

import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEnumerate {
    @Test
    public void counters() {
        var dd = asMap(map(Pair::reverse,enumerate(over("words4me"))));
        assertEquals(0,dd.get('w'));
        assertEquals(1,dd.get('o'));
        assertEquals(2,dd.get('r'));
        assertEquals(3,dd.get('d'));
        assertEquals(4,dd.get('s'));
        assertEquals(5,dd.get('4'));
        assertEquals(6,dd.get('m'));
        assertEquals(7,dd.get('e'));
    }

    @Test
    public void offset() {
        var dd = asList(map(x -> x.left()+x.right(), enumerate(10L,of(4L,12L))));
        assertEquals(14L, dd.get(0));
        assertEquals(23L, dd.get(1));
    }
}
