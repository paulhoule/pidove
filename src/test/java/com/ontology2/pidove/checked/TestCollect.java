package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ontology2.pidove.checked.Collectors.*;
import static com.ontology2.pidove.checked.Iterables.collect;
import static com.ontology2.pidove.checked.Iterables.over;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCollect {
    @Test
    public void collectIntoAList() {
        Collector<Character, List<Character>, List<Character>> collectList = new Collector<>(ArrayList::new,(x, c)->c.add(x), x->x);
        var tiger = over("tiger");
        assertEquals(List.of('t','i','g','e','r'), collect(collectList, tiger));
    }

    @Test
    public void collectIntoAListWildcard() {
        var tiger = over("tiger");
        assertEquals(List.of('t','i','g','e','r'), collect(tiger, toList()));
    }

    @Test
    public void groupIntoLists() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,888);
        var grouped = collect(that, groupingBy(i -> i % 3,toList()));
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
        var grouped = collect(groupingBy(i->i % 3, toSet().andThen(Set::size)), that);
        assertEquals(7, grouped.get(0));
        assertEquals(4, grouped.get(1));
        assertEquals(2, grouped.get(2));
    }

    @Test
    public void doubleDistinctCount() {
        var that = List.of(11,772,22,81,99,12,54,112,78,24,55,104,55,888,55);
        var grouped = collect(groupingBy(i->i % 3, countDistinct().andThen(x -> 2 * x)), that);
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

}
