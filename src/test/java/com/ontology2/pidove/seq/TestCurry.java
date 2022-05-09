package com.ontology2.pidove.seq;

import com.ontology2.pidove.util.Pair;
import com.ontology2.pidove.util.Trio;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import java.util.function.Function;

import static com.ontology2.pidove.util.Curry.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCurry {
    @Test
    public void homemadeCurry() {
        Function<String, Function<Integer, Pair<String,Integer>>>
                coupler = a -> b -> new Pair<>(a,b);

        assertEquals(new Pair<>("Static", 26), coupler.apply("Static").apply(26));
    }

    @Test
    public void restaurantCurry() {
        Function<Integer, Function<Integer, Integer>> coupler = curry((a,b) -> a*b);
        assertEquals(4096, coupler.apply(64).apply(64));
    }

    //
    // The fact that this type inference works makes currying seem worthwhile
    //
    @Test
    public void inferredCurry() {
        var coupler = curry((Integer a,Integer b) -> a*b);
        assertEquals(1106, coupler.apply(14).apply(79));
    }

    @Test
    public void curry3() {
        var tripler =
                curry((Integer a,Integer b, Integer c) -> new Trio<>(a,b,c));
        var that = tripler.apply(4).apply(24);
        assertEquals(Trio.of(4,24,1972), that.apply(1972));
        assertEquals(Trio.of(4,24,1973), that.apply(1973));
    }

    @Test
    public void haveUncurryWillTravel() {
        var makeAPair = uncurry((Object a) -> (Object b) -> new Pair<>(a,b));
        assertEquals(new Pair<>("Blinking", 55),makeAPair.apply("Blinking",55));
    }

    @Test
    public void haveUncurry3WillTravel() {
        var threesCompany =
                uncurry3((Object a) -> (Object b) -> (Object c) -> new Trio<>(a,b,c));
        assertEquals(new Trio<>("Blinking", 55, AudioFormat.Encoding.PCM_SIGNED),
                threesCompany.apply("Blinking",55, AudioFormat.Encoding.PCM_SIGNED));
    }
}
