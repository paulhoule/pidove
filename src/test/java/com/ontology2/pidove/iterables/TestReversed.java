package com.ontology2.pidove.iterables;

import org.junit.jupiter.api.Test;

import static com.ontology2.pidove.iterables.Dollar.$;
import static com.ontology2.pidove.iterables.Iterables.*;
import static com.ontology2.pidove.iterables.MoreCollectors.characters;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReversed {
    @Test
    public void reverseAString() {
        var chars = over("put up a front");
        var rc = collect(characters(),reversed(chars));
        assertEquals("tnorf a pu tup",rc);
    }

    @Test
    public void fluentlyReverseAString() {
        var chars = $("put up a front");
        var rc = chars.reversed().collect(characters());
        assertEquals("tnorf a pu tup",rc);
    }
}
