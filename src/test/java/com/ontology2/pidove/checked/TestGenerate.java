package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static com.ontology2.pidove.checked.Iterables.*;
import static com.ontology2.pidove.checked.Suppliers.iterable;
import static org.junit.jupiter.api.Assertions.*;

public class TestGenerate {
    @Test
    public void simpleCase() {
        var infinite = generate(() -> () -> "eleven");
        var that = asList(limit(7, infinite));
        assertEquals(7,that.size());
        assertTrue(all(map(x->x.equals("eleven"), that)));
    }

    static class CounterSupplier implements Supplier<Integer> {
        int i=0;
        @Override
        public Integer get() {
            return i++;
        }
    }

    @Test
    public void counterCase() {
        var counter = generate(CounterSupplier::new);
        var that = asList(limit(4, counter));
        assertEquals(List.of(0, 1, 2, 3),that);

        var skipped0 =  asList(limit(4, skip(0, counter)));
        assertEquals(List.of(0, 1, 2, 3), skipped0);

        var skipped1 =  asList(limit(4, skip(1, counter)));
        assertEquals(List.of(1, 2, 3, 4), skipped1);

        var skipped25 =  asList(limit(4, skip(25, counter)));
        assertEquals(List.of(25, 26, 27, 28), skipped25);
    }

    @Test
    public void anotherWayToWriteCounters() {

        var counter = generate(() -> new Supplier<Integer>() {
            int i=0;
            @Override
            public Integer get() {
                return i++;
            }
        });

        var that = asList(limit(4, counter));
        assertEquals(List.of(0, 1, 2, 3),that);

        var skipped0 =  asList(limit(4, skip(0, counter)));
        assertEquals(List.of(0, 1, 2, 3), skipped0);

        var skipped1 =  asList(limit(4, skip(1, counter)));
        assertEquals(List.of(1, 2, 3, 4), skipped1);

        var skipped25 =  asList(limit(4, skip(25, counter)));
        assertEquals(List.of(25, 26, 27, 28), skipped25);
    }

    public void lambdaCounters() {

        var counter = generate(() -> {int[] i={0}; return () -> i[0]++;});

        var that = asList(limit(4, counter));
        assertEquals(List.of(0, 1, 2, 3),that);

        var skipped0 =  asList(limit(4, skip(0, counter)));
        assertEquals(List.of(0, 1, 2, 3), skipped0);

        var skipped1 =  asList(limit(4, skip(1, counter)));
        assertEquals(List.of(1, 2, 3, 4), skipped1);

        var skipped25 =  asList(limit(4, skip(25, counter)));
        assertEquals(List.of(25, 26, 27, 28), skipped25);
    }

    @Test
    public void testSuppliersIterable() {
        var some = iterable(List.of("a","b","c"));
        assertEquals("a", some.get());
        assertEquals("b", some.get());
        assertEquals("c", some.get());
        assertThrows(NoSuchElementException.class, some::get);
    }
}
