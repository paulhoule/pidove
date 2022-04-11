package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.ontology2.pidove.checked.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMap {
    static final Function<Integer, Iterable<Integer>> megaplier = i -> {
        List<Integer> that = new ArrayList<>();
        for(int j=0;j<i;j++) {
            that.add(i);
        }
        return that;
    };

    static <X> Function<X,Iterable<X>> filterWrapper(final Predicate<X> predicate) {
        return x -> predicate.test(x) ? List.of(x) : List.of();
    }

    @Test
    public void testMap() {
        var list = List.of(1,2,3,4,5,6,7);
        var result = asList(map(list, x->2*x));
        assertEquals(List.of(2,4,6,8,10,12,14), result);
    }

    @Test
    public void mapToBool() {
        var list = List.of(1,2,3,4,5,6,7);
        var result = asList(map(list, x-> x%2==0));
        assertEquals(List.of(false, true, false, true, false, true, false), result);
    }

    @Test
    public void testToSet() {
        var list = List.of("a","b","c");
        var result = asSet(map(list, x->x+x));
        assertEquals(Set.of("cc", "bb", "aa"), result);
    }

    @Test
    public void testOffEnd() {
        var list = List.of("a","b","c");
        var result = map(list, x->x+x).iterator();
        assertEquals("aa", result.next());
        assertEquals("bb", result.next());
        assertEquals("cc", result.next());
        assertThrows(NoSuchElementException.class, result::next);
    }

    @Test
    public void testFlatMap() {
        assertFalse(flatMap(megaplier, List.of()).iterator().hasNext());
        assertFalse(flatMap(megaplier, List.of(0)).iterator().hasNext());
        assertFalse(flatMap(megaplier, List.of(0,0)).iterator().hasNext());
        assertFalse(flatMap(megaplier, List.of(0,0,0)).iterator().hasNext());
        assertEquals(List.of(1), asList(flatMap(megaplier, List.of(1,0,0))));
        assertEquals(List.of(2,2), asList(flatMap(megaplier, List.of(0,2,0))));
        assertEquals(List.of(3,3,3), asList(flatMap(megaplier, List.of(0,0,3))));
        assertEquals(List.of(2,2,1), asList(flatMap(megaplier, List.of(2,1))));
        assertEquals(List.of(1,1), asList(flatMap(megaplier, List.of(1,0,0,0,0,0,1))));
    }

    @Test
    public void testFilterEmulation() {
        Predicate<String> startA = s -> s.startsWith("a") || s.startsWith("A");
        var wrappedPredicate = filterWrapper(startA);
        var input = List.of("acid","base","Awkward","apple","blue","Caca");
        var output = List.of("acid", "Awkward", "apple");
        assertEquals(output, asList(flatMap(wrappedPredicate, input)));
    }
}
