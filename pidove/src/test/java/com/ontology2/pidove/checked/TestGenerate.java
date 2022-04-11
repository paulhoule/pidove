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
        assertTrue(all(map(that,x->x.equals("eleven"))));
    }

    @Test
    public void counterCase() {
        var counter = new Supplier<Integer>() {
            int i=0;
            @Override
            public Integer get() {
                return i++;
            }
        };

        var that = asList(limit(4, generate(() -> counter)));
        assertEquals(List.of(0, 1, 2, 3),that);

        var skipped =  asList(limit(4, skip(generate(() -> counter), 0)));
        assertEquals(List.of(0, 1, 2, 3), skipped);

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
