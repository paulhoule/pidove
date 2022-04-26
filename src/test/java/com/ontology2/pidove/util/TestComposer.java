package com.ontology2.pidove.util;

import com.ontology2.pidove.seq.Iterables;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.ontology2.pidove.seq.Iterables.over;
import static com.ontology2.pidove.util.Composer.compose;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestComposer {
    @Test
    public void someMath() {
        Function<Integer,Integer> fn = compose(x->x*2, x->x+1);
        assertEquals(1, fn.apply(0));
        assertEquals(3, fn.apply(1));
        assertEquals(5, fn.apply(2));
    }

    @Test
    public void threeWay() {
        Function<Object,Integer> fn = compose(Object::getClass, Class::getName, String::length);
        assertEquals(16, fn.apply("Five Hundred"));
        assertEquals(18, fn.apply(Optional.of(7)));
    }

    @SuppressWarnings("SameParameterValue")
    Function<String, Iterable<String>> splitOn(String pattern) {
        return s -> over(s.split(pattern));
    }

    @Test
    public void fourWay() {
        Function<Object,Long> fn = compose(Object::getClass, Class::getName, splitOn("[.]"), Iterables::count);
        assertEquals(3, fn.apply("Five Hundred"));
    }
}
