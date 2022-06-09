package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Dollar.$$;
import static com.ontology2.pidove.iterables.Fixtures.equalItemsAssert;
import static com.ontology2.pidove.iterables.Fixtures.closeSpy;
import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDropAndTake {
    Predicate<Integer> isEven = i -> i % 2 == 0;
    Predicate<Integer> isOdd = isEven.negate();

    @Test
    public void dropEvenOrOdd() {
        assertEquals(List.of(), asList(dropWhile(isEven, List.of())));
        assertEquals(List.of(1), asList(dropWhile(isEven, List.of(1))));
        assertEquals(List.of(1, 2 , 3), asList(dropWhile(isEven, List.of(1,2,3))));
        assertEquals(List.of(), asList(dropWhile(isOdd, List.of(1))));
        assertEquals(List.of(2 , 3), asList(dropWhile(isOdd, List.of(1,2,3))));
        assertEquals(List.of(), asList(dropWhile(isOdd, List.of(77, 11, 33, 11, 505))));
        assertEquals(List.of(77, 11, 33, 11, 505), asList(dropWhile(isEven, List.of(77, 11, 33, 11, 505))));
        assertEquals(List.of(55), asList(dropWhile(isEven,List.of(108, 110, 55))));
    }

    @Test
    public void dropEvenOrOdd$() {
        assertEquals(List.of(1), $(List.of(1)).dropWhile(isEven).toList());
        assertEquals(List.of(1, 2 , 3), $(List.of(1,2,3)).dropWhile(isEven).toList());
        assertEquals(List.of(), $(List.of(1)).dropWhile(isOdd).toList());
        assertEquals(List.of(2 , 3), $(List.of(1,2,3)).dropWhile(isOdd).toList());
        assertEquals(List.of(), $(List.of(77, 11, 33, 11, 505)).dropWhile(isOdd).toList());
        assertEquals(List.of(77, 11, 33, 11, 505), $(List.of(77, 11, 33, 11, 505)).dropWhile(isEven).toList());
        assertEquals(List.of(55), $(List.of(108, 110, 55)).dropWhile(isEven).toList());
    }

    @Test
    public void closesAfterDrop() {
        var instrumented = closeSpy(List.of(5,5,5,5,5));
        asList(dropWhile(isEven, instrumented));
        asList(dropWhile(isOdd, instrumented));
        assertEquals(2, instrumented.getCloseCount());
    }
    @Test
    public void takeEvenOrOdd() {
        assertEquals(List.of(), asList(takeWhile(isEven, List.of())));
        assertEquals(List.of(), asList(takeWhile(isEven, List.of(1))));
        assertEquals(List.of(1), asList(takeWhile(isOdd, List.of(1))));
        assertEquals(List.of(1), asList(takeWhile(isOdd, List.of(1, 2))));
        assertEquals(List.of(1, 3), asList(takeWhile(isOdd, List.of(1, 3))));
        assertEquals(List.of(1, 3, 5), asList(takeWhile(isOdd, List.of(1, 3, 5))));
        assertEquals(List.of(1, 3, 5), asList(takeWhile(isOdd, List.of(1, 3, 5, 8, 1))));
    }

    @Test
    public void takeEvenOrOdd$() {
        equalItemsAssert($(Integer.class).takeWhile(isEven));
        equalItemsAssert($$(1).takeWhile(isEven));
        equalItemsAssert($$(1).takeWhile(isOdd),1);
        equalItemsAssert($$(1,2).takeWhile(isOdd),1);
        equalItemsAssert($$(1,3).takeWhile(isOdd),1,3);
        equalItemsAssert($$(1,3,5).takeWhile(isOdd), 1,3,5);
        equalItemsAssert($$(1, 3, 5, 8, 1).takeWhile(isOdd),1,3,5);
    }

    @Test
    public void closesAfterTake() {
        var instrumented = closeSpy(List.of(5,5,5,5,5));
        asList(takeWhile(isEven, instrumented));
        asList(takeWhile(isOdd, instrumented));
        assertEquals(2, instrumented.getCloseCount());
    }


}
