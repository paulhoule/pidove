package com.ontology2.pidove.util;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.ontology2.pidove.util.DuctTape.cast;
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

}
