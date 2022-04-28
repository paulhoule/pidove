package com.ontology2.pidove.util;

import com.ontology2.pidove.seq.Iterables;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.*;

import static com.ontology2.pidove.seq.Iterables.over;
import static com.ontology2.pidove.util.Composer.compose;
import static com.ontology2.pidove.util.Composer.composePredicate;
import static org.junit.jupiter.api.Assertions.*;


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

    @Test
    public void fromASupplier() {
        var j = new Janus<>(1000);
        assertEquals(1000000, compose(j.supplier(), x->x*x).get());
    }

    @Test
    public void toAConsumer() {
        var j = new Janus<>(1000);
        Function<Integer, Integer> f = x-> -x;
        compose(f, j.consumer()).accept(75);
        assertEquals(-75, j.supplier().get());
        Consumer<Integer> c = compose(x -> -x, j.consumer());
        c.accept(0);
        assertEquals(0, j.supplier().get());
        // note the following doesn't solve

        // compose(x -> -x, j.consumer()).accept(-35).accept(100);
    }

    @Test
    public void toAPredicate() {
        Predicate<Integer> isEven = composePredicate(x->x%2,x->x==0);
        assertFalse(isEven.test(7));
        assertTrue(isEven.test(1080));
    }

    @Test
    public void everybodyLovesYouWhenYourBiFunction() {
        BiFunction<String,Integer,String> b=compose((t,u) -> t.formatted(u), s->s.replace("3","33"));
        assertEquals("<3333>",b.apply("<%d>",33));
        assertEquals("33 and the ragged 7",b.apply("3 and the ragged %d",7));
    }

    @Test
    public void makingABiPredicate() {
        BiPredicate<Integer,Integer> mod7Equals = composePredicate((a,b) -> a-b,(c) -> c % 7 ==0);
        assertTrue(mod7Equals.test(3,10));
        assertFalse(mod7Equals.test(4,5));
        //
        // generics don't solve if we don't give integer here
        //
        assertTrue(composePredicate((Integer a,Integer b) -> a-b,(c) -> c % 7 ==0).test(0,700));
    }
}
