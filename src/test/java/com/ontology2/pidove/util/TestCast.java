package com.ontology2.pidove.util;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.ontology2.pidove.util.DuctTape.cast;
import static com.ontology2.pidove.util.DuctTape.lambda;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCast {
    @Test
    public void castAnythingToAnythingElse() {
        Function<String, Character> thirdCharacter = x->x.charAt(2);
        Object o = "What are words for?";
        assertEquals('a', thirdCharacter.apply(cast(o)));
    }

    @Test
    public void atRiskOfHavingARuntimeException() {
        Function<String, Character> thirdCharacter = x->x.charAt(2);
        Object o = 104;
        assertThrows(ClassCastException.class, () -> thirdCharacter.apply(cast(o)));
    }

    @Test
    public void makeVarWorkForLambda() {
        var add = lambda((Integer a, Integer b) -> a+b);
        assertEquals(15, add.apply(10,5));
    }

    @Test
    public void competesWithVoidLambda() {
        var len = lambda((String s) -> s.length());
        assertEquals(3, len.apply("abc"));
    }

    @Test
    public void howAboutAConsumer() {
        Janus<String> j = new Janus("");
        var aStar = lambda((String s) -> j.consumer().accept(s));
        aStar.accept("l00k");
        assertEquals("l00k", j.supplier().get());
    }

    @Test
    public void multiAdd() {
        var fma = lambda((Integer a, Integer b, Integer c) -> a*b+c);
        assertEquals(0, fma.apply(0,0,0));
        assertEquals(2, fma.apply(1,1,1));
        assertEquals(50, fma.apply(7,7,1));
    }
}