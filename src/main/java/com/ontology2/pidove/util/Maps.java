package com.ontology2.pidove.util;

import com.ontology2.pidove.util.DefaultMap;

import java.util.*;

/**
 * This class contains constructors to return specialized instances of Map.
 *
 */
public class Maps {
    /**
     *
     * @return a default map where the default is an empty ArrayList
     * @param <K> key type
     * @param <V> value type
     */
    public static <K,V> Map<K, List<V>> mapOfLists() {
        return new DefaultMap<>(new HashMap<>(), ArrayList::new);
    }

    /**
     *
     * @return a default map where the default is an empty HashSet
     * @param <K> key type
     * @param <V> value type
     */
    public static <K,V> Map<K, Set<V>> mapOfSets() {
        return new DefaultMap<>(new HashMap<>(), HashSet::new);
    }

    /**
     *
     * @return a default map where the default is an empty HashMap
     * @param <K1> type of outer key
     * @param <K2> type of inner key
     * @param <V> type of inner value
     */
    public static <K1, K2, V> Map<K1, Map<K2, V>> mapOfMaps() {
        return new DefaultMap<>(new HashMap<>(), HashMap::new);
    }
}
