package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;
import org.junit.jupiter.api.Test;

import static com.ontology2.pidove.iterables.Dollar.$;
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
    public void fluentCounters() {
        var dd = asMap($("words4me").enumerate().map(Pair::reverse));
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
    public void fluentCountersPlus2() {
        var dd = asMap($("words4me").enumerate(2).map(Pair::reverse));
        assertEquals(2,dd.get('w'));
        assertEquals(3,dd.get('o'));
        assertEquals(4,dd.get('r'));
        assertEquals(5,dd.get('d'));
        assertEquals(6,dd.get('s'));
        assertEquals(7,dd.get('4'));
        assertEquals(8,dd.get('m'));
        assertEquals(9,dd.get('e'));
    }

    @Test
    public void offset() {
        var dd = asList(map(x -> x.left()+x.right(), enumerate(10L,of(4L,12L))));
        assertEquals(14L, dd.get(0));
        assertEquals(23L, dd.get(1));
    }
}
