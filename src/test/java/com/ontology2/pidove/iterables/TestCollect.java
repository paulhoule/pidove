package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.MoreCollectors.countDistinct;
import static com.ontology2.pidove.iterables.Iterables.collect;
import static com.ontology2.pidove.iterables.Iterables.over;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCollect {
    @Test
    public void collectIntoAList() {
        Collector<Character, List<Character>, List<Character>> collectList = Collector.of(
                ArrayList::new,
                List::add,
                (a,b) -> { a.addAll(b); return a; },
                x->x);
        var tiger = over("tiger");
        assertEquals(List.of('t','i','g','e','r'), collect(collectList, tiger));
    }

    @Test
    public void collectIntoAListWildcard() {
        var tiger = over("tiger");
        assertEquals(List.of('t','i','g','e','r'), collect(toList(), tiger));
    }

    @Test
    public void groupIntoLists() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,888);
        var grouped = collect(groupingBy(i -> i % 3,toList()), that);
        assertEquals(List.of(81, 99, 12, 54, 78, 24, 888), grouped.get(0));
        assertEquals(List.of(772, 22, 112, 55), grouped.get(1));
        assertEquals(List.of(11, 104), grouped.get(2));
    }

    @Test
    public void distinctCount() {
        var that = List.of("gotta","get","away","away","from","Z");
        var count = collect(collectingAndThen(toSet(), Set::size), that);
        assertEquals(5, count);
    }

    @Test
    public void groupIntoSetAndCount() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,55,888,55);
        var grouped = collect(groupingBy(i->i % 3, collectingAndThen(toSet(), Set::size)), that);
        assertEquals(7, grouped.get(0));
        assertEquals(4, grouped.get(1));
        assertEquals(2, grouped.get(2));
    }

    @Test
    public void groupIntoSetAndCount2() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,55,888,55);
        var grouped = collect(groupingBy(i->i % 3, collectingAndThen(toSet(),Set::size)), that);
        assertEquals(7, grouped.get(0));
        assertEquals(4, grouped.get(1));
        assertEquals(2, grouped.get(2));
    }

    @Test
    public void doubleDistinctCount() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,55,888,55);
        var grouped = collect(groupingBy(i->i % 3, collectingAndThen(countDistinct(),x -> 2 * x)), that);
        assertEquals(14, grouped.get(0));
        assertEquals(8, grouped.get(1));
        assertEquals(4, grouped.get(2));
    }

    @Test
    public void groupAndCount() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,55,888,55);
        var grouped = collect(groupingBy(i->i % 3, counting()), that);
        assertEquals(7, grouped.get(0));
        assertEquals(6, grouped.get(1));
        assertEquals(2, grouped.get(2));
    }

    @Test
    public void itsWorthADollar() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,55,888,55);
        var grouped = $(that).collect(groupingBy(i->i % 3, counting()));
        assertEquals(7, grouped.get(0));
        assertEquals(6, grouped.get(1));
        assertEquals(2, grouped.get(2));
    }
}
