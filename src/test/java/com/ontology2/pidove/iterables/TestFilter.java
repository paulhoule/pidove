package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Fixtures.closeSpy;
import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestFilter {
    @Test
    public void onlyEven() {
        var list = List.of(1,2,3,4,5,6,7);
        var result = asList(filter(x-> x%2==0, list));
        assertEquals(List.of(2,4,6), result);
    }

    @Test
    public void onlyOdd() {
        var list = List.of(1,2,3,4,5,6,7);
        var result = asList(filter(x-> x%2==1, list));
        assertEquals(List.of(1,3,5,7), result);
    }

    @Test
    public void dividesByThree() {
        var list = List.of(1,2,3,4,5,6,7);
        var result = $(list).filter(x-> x%2==1).toList();
        assertEquals(List.of(1,3,5,7), result);
    }


    @Test
    public void oneWayOrAnother() {
        var list = List.of("one","way","or","another","I'm","gonna","find","ya");

        var result = filter(x-> x.length() % 2 == 0, list);
        var that = result.iterator();
        assertTrue(that.hasNext());
        assertEquals("or", that.next());
        assertTrue(that.hasNext());
        assertEquals("find", that.next());
        assertTrue(that.hasNext());
        assertEquals("ya", that.next());
        assertFalse(that.hasNext());
    }

    @Test
    public void oneWayOrAnotherNotNext() {
        var list = List.of("one","way","or","another","I'm","gonna","find","ya");

        var result = filter(x-> x.length() % 2 == 0, list);
        var that = result.iterator();
        assertEquals("or", that.next());
        assertEquals("find", that.next());
        assertEquals("ya", that.next());
        assertFalse(that.hasNext());
    }

    @Test
    public void oneWayOrAnotherOffEnd() {
        var list = List.of("one","way","or","another","I'm","gonna","find","ya");

        var result = filter(x-> x.length() % 2 == 0, list);
        var that = result.iterator();
        assertEquals("or", that.next());
        assertEquals("find", that.next());
        assertEquals("ya", that.next());
        assertThrows(NoSuchElementException.class, that::next);
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void andItCloses() {
        var instrumented = closeSpy(List.of());
        filter(x->false, instrumented);
        assertEquals(1, instrumented.getCloseCount());
    }

    @Test
    public void andTheOtherWayAround() {
        var list = splitOn(" ","blue light special on truth");
        var result = filterFalse(x-> x.length() % 2 == 0, list);
        assertEquals("light special truth",joinOn(" ", result));
    }


}
