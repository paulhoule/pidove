package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static com.ontology2.pidove.checked.Iterables.asList;
import static com.ontology2.pidove.checked.Iterables.dropWhile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDropAndTake {
    Predicate<Integer> isEven = i -> i % 2 == 0;
    Predicate<Integer> isOdd = isEven.negate();

    @Test
    public void dropEven() {
        assertEquals(List.of(), asList(dropWhile(isEven, List.of())));
        assertEquals(List.of(1), asList(dropWhile(isEven, List.of(1))));
        assertEquals(List.of(1, 2 , 3), asList(dropWhile(isEven, List.of(1,2,3))));
        assertEquals(List.of(), asList(dropWhile(isOdd, List.of(1))));
        assertEquals(List.of(2 , 3), asList(dropWhile(isOdd, List.of(1,2,3))));
        assertEquals(List.of(), asList(dropWhile(isOdd, List.of(77, 11, 33, 11, 505))));
        assertEquals(List.of(77, 11, 33, 11, 505), asList(dropWhile(isEven, List.of(77, 11, 33, 11, 505))));
        assertEquals(List.of(55), asList(dropWhile(isEven,List.of(108, 110, 55))));
    }
}
