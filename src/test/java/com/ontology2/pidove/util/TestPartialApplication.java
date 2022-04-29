package com.ontology2.pidove.util;

import org.junit.jupiter.api.Test;

import static com.ontology2.pidove.util.Partial.partial;
import static com.ontology2.pidove.util.Partial.partialR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPartialApplication {
    static String bff(String f, Integer a, Integer b) {
        return f.formatted(a,b);
    };

    @Test
    public void funWithFunctions() {
        var fixedNumbers = partialR(TestPartialApplication::bff,12,75);
        assertEquals("12,75",fixedNumbers.apply("%d,%d"));
    }
}
