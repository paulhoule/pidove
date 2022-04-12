package com.ontology2.pidove.checked;

import java.util.*;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public class Collectors {

    public static <X> Collector<X,?,Integer> countDistinct() {
        return collectingAndThen(toSet(), Set::size);
    }
}
