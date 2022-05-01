package com.ontology2.pidove.util;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.ontology2.pidove.util.Partial.partial;
import static com.ontology2.pidove.util.Partial.partialR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPartialApplication {
    String bff(String f, Integer a, Integer b) {
        return f.formatted(a,b);
    };

    @Test
    public void putTheFunInFunction() {
        var fixedNumbers = partialR(this::bff,12,75);
        assertEquals("12,75",fixedNumbers.apply("%d,%d"));
        assertEquals("a=12 b=75",fixedNumbers.apply("a=%d b=%d"));
        var fixedFormat = partial(this::bff,"%s or %s");
        assertEquals("25 or 624", fixedFormat.apply(25,624));
    }

    public Function<String,String> p3(Function3<String,Integer,Integer,String> f, Integer a, Integer b) {
        return partialR(f,a,b);
    }

    //
    // I wasn't so happy with how this test turned out.  The idea here is to partially apply the partial
    // application itself,  applying the variables that the inner apply function  would apply to the
    // function that the outer apply function takes as a parameter.
    //
    // It doesn't look so bad  however,  because I put effort into reworking it to make it look easy.
    //
    // Note we can't reference the apply function directly from the Apply class with a method
    // reference because there are so many different function with the same name.  Thus we put the p3
    // method into this class which has an unambiguous name.  I was hoping to get away with leaving
    // p3 generic but found that the compiler wouldn't solve the types that way.
    //
    @Test
    public void notSoMuchFun() {
        var pp = partialR(this::p3,117,204);
        assertEquals("00117",pp.apply(this::bff).apply("%05d"));
    };
}
