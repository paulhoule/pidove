package com.ontology2.pidove.util;

import com.ontology2.pidove.util.DefaultMap;

import java.util.*;

public class Maps {
    public static <K,V> Map<K, List<V>> mapOfLists() {
        return new DefaultMap<>(new HashMap<>(), ArrayList::new);
    }

    public static <K,V> Map<K, Set<V>> mapOfSets() {
        return new DefaultMap<>(new HashMap<>(), HashSet::new);
    }

    public static <K1, K2, V> Map<K1, Map<K2, V>> mapOfMaps() {
        return new DefaultMap<>(new HashMap<>(), HashMap::new);
    }
}
