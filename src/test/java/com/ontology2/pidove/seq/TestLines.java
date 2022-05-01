package com.ontology2.pidove.seq;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static com.ontology2.pidove.seq.Iterables.*;
import static com.ontology2.pidove.seq.Resources.resource;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLines {
    @Test
    public void yoursIsNoDisgrace() {
        var words = asList(over(() -> resource(this, "battleships.txt")));
        assertEquals(List.of("Battleships", "confide", "in", "me"), words);
    }

    @Test
    public void aFileThatIsNotThere() {
        Iterable<String> i = over(Path.of("you_really_dont_have_this_file_do_you.txt"));
        assertThrows(RuntimeException.class, () -> asList(i));
    }
}
