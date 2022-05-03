package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import static com.ontology2.pidove.seq.Iterables.*;
import static com.ontology2.pidove.seq.MoreCollectors.characters;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReversed {
    @Test
    public void reverseAString() {
        var chars = over("put up a front");
        var rc = collect(characters(),reversed(chars));
        assertEquals("tnorf a pu tup",rc);
    }
}
