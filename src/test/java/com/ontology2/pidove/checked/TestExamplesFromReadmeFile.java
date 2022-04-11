package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ontology2.pidove.checked.Collectors.groupingBy;
import static com.ontology2.pidove.checked.Iterables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExamplesFromReadmeFile {
    @Test
    public void hereIsASample() {
        over("bushfire").forEach(System.out::println);
    }

    @Test
    public void tryASum() {
        assertEquals(10, sum(filter(x-> x%2 == 0, List.of(5,3,4,19,75,6))));
    }

    @Test
    public void groupWords() {
        var words = List.of("debate","devote","horn","seal","decide","responsibility",
                "wilderness","scheme","waste","receipt","single","tradition","noble");
        var grouped = collect(groupingBy(word-> word.charAt(0)), words);
        assertEquals(List.of("wilderness", "waste"), grouped.get('w'));
    }
}
