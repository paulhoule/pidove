package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;
import com.ontology2.pidove.util.Trio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Dollar.$$;
import static com.ontology2.pidove.iterables.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestZip {
    @Test
    public void simpleZip() {
        var a= List.of("something", "good", "can", "happen");
        var b = List.of("something", "big", "and", "lovely");
        var c = $(a,b).toList();
        assertEquals(new Pair<>("something", "something"), c.get(0));
        assertEquals(new Pair<>("good", "big"), c.get(1));
        assertEquals(new Pair<>("can", "and"), c.get(2));
        assertEquals(new Pair<>("happen", "lovely"), c.get(3));
        assertEquals(4, c.size());
    }

    @Test
    public void lopsided() {
        var a= List.of("nothing", "but", "static");
        var b = List.of(3, 6, 11, 23, 47, 106, 235);
        var c = asList(zip(a,b));
        assertEquals(new Pair<>("nothing", 3), c.get(0));
        assertEquals(new Pair<>("but", 6), c.get(1));
        assertEquals(new Pair<>("static", 11), c.get(2));
        assertEquals(3, c.size());
    }

    @Test
    public void lopsidedOther() {
        var a= List.of("nothing", "but", "static");
        var b = List.of(3, 6, 11, 23, 47, 106, 235);
        var c = asList(map(Pair::reverse,zip(b,a)));
        assertEquals(new Pair<>("nothing", 3), c.get(0));
        assertEquals(new Pair<>("but", 6), c.get(1));
        assertEquals(new Pair<>("static", 11), c.get(2));
        assertEquals(3, c.size());
    }

    @Test
    public void zip3OfThem() {
        var a = $(0,5);
        var b = $$("static", "in", "my", "attic", "from");
        var c = $$("space junk","laser bombs","put up my umbrella","giant stacks blowing smoke",
                "toxic fog");
        var z = $(a,b,c).toList();
        assertEquals(5, len(z));
        assertEquals(new Trio<>(1L,"in","laser bombs"),z.get(1));

    };
}
