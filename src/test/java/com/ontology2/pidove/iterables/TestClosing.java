package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ontology2.pidove.iterables.Fixtures.closeSpy;
import static com.ontology2.pidove.iterables.Iterables.*;
import static java.util.stream.Collectors.counting;
import static org.junit.jupiter.api.Assertions.*;

public class TestClosing {
    @Test
    public void asListCloses() {
        var instrumented = closeSpy(List.of(5,5,5,5,5));
        asList(instrumented);
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void asSetCloses() {
        var instrumented = closeSpy(List.of(5,5,5,5,5));
        asList(instrumented);
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void asMapCloses() {
        var instrumented = closeSpy(List.of(new Pair<>("laser", "blast")));
        asMap(instrumented);
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void collectCloses() {
        var instrumented = closeSpy(List.of(5,5,5,5,5,5));
        assertEquals(6,collect(counting(),instrumented));
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void allCloses() {
        var instrumented = closeSpy(List.of(5,5,5,5,5,5));
        assertTrue(all(map(x->x.equals(5), instrumented)));
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void allClosesShort() {
        var instrumented = closeSpy(List.of(5,5,5,5,5,5));
        assertFalse(all(map(x->x.equals(6), instrumented)));
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void anyCloses() {
        var instrumented = closeSpy(List.of(5,5,5,5,5,5));
        assertTrue(any(map(x->x.equals(5), instrumented)));
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void anyClosesShort() {
        var instrumented = closeSpy(List.of(5,5,5,5,5,5));
        assertFalse(any(map(x->x.equals(6), instrumented)));
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void foreachClosesOnMapIterable() {
        var instrumented = closeSpy(List.of(5,5,5,5,5,5));
        map(x->x.equals(6), instrumented).forEach(x -> {});
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void foreachClosesOnConcatIterable() {
        var instrumented = closeSpy(concat());
        map(x->x.equals(6), instrumented).forEach(x -> {});
        assertClosed(instrumented);
    }

    @Test
    public void closesOnCollect() {
        var instrumented = closeSpy(concat(over("Channel Z")));
        assertEquals(9, collect(Collectors.counting(), instrumented));
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void flatMapClosesSourceIterable() {
        var instrumented = closeSpy(List.of());
        var nothing = flatMap(x->empty(), instrumented);
        count(nothing);
        assertClosed(instrumented);
    }

    @Test
    public void flatMapClosesInnermostIterable() {
        var instrumentedA = closeSpy(List.of(1001));
        var instrumentedB = closeSpy(List.of(1002));
        var instrumentedC = closeSpy(List.of(1003));
        var object = closeSpy(List.of(instrumentedA, instrumentedB, instrumentedC));
        var nothing = asList(flatMap(x->x, object));
        assertEquals(List.of(1001, 1002, 1003), nothing);
        assertClosed(object);
        assertClosed(instrumentedA);
        assertClosed(instrumentedB);
        assertClosed(instrumentedC);
    }

    @Test
    public void flatMapClosesInnermostIterableShort() {
        var instrumentedA = closeSpy(List.of(1001));
        var instrumentedB = closeSpy(List.of(1002));
        var instrumentedC = closeSpy(List.of(1003));
        var object = List.of(instrumentedA, instrumentedB, instrumentedC);
        var nothing = asList(takeWhile(x -> x == 1001, flatMap(x->x, object)));
        assertEquals(List.of(1001), nothing);
        assertTrue(instrumentedA.isBalanced());
        assertClosed(instrumentedA);
        assertClosed(instrumentedB);
    }

    @Test
    public void limitCloses() {
        var instrumented = closeSpy(range(100));
        var three = asList(limit(3, instrumented));
        assertEquals(List.of(0L,1L,2L), three);
        assertClosed(instrumented);
    }

    @Test
    public void maxCloses() {
        var instrumented = closeSpy(range(5));
        var four = max(instrumented).orElseThrow();
        assertEquals(4, four);
        assertClosed(instrumented);
    }

    @Test
    public void minCloses() {
        CloseSpyIterable<String> instrumented = closeSpy(List.of());
        var four = min(instrumented);
        assertFalse(four.isPresent());
        assertClosed(instrumented);
    }

    @Test
    public void noneCloses() {
        var instrumented = closeSpy(List.of(true));
        assertFalse(none(instrumented));
        assertClosed(instrumented);
    }

    @Test
    public void skipCloses() {
        var instrumented = closeSpy(List.of(true));
        assertEquals(0, count(skip(5, instrumented)));
        assertClosed(instrumented);
    }

    @Test
    public void sumIntCloses() {
        CloseSpyIterable<Integer> instrumented = closeSpy(List.of());
        assertEquals(0, sumInt(instrumented));
        assertClosed(instrumented);
    }

    @Test
    public void sumLongCloses() {
        CloseSpyIterable<Long> instrumented = closeSpy(List.of());
        assertEquals(0, sumLong(instrumented));
        assertClosed(instrumented);
    }

    @Test
    public void sumDoubleCloses() {
        CloseSpyIterable<Double> instrumented = closeSpy(List.of());
        assertEquals(0.0, sumDouble(instrumented));
        assertClosed(instrumented);
    }

    @Test
    public void reverseCloses() {
        CloseSpyIterable<String> instrumented = closeSpy(List.of("I","Like","The","Way","You","Werk","It"));
        assertEquals(Optional.of("It"), first(reversed(instrumented)));
        assertClosed(instrumented);
    }
    private void assertClosed(CloseSpyIterable<?> instrumented) {
        assertEquals(1, instrumented.getCloseCount());
    }
}
