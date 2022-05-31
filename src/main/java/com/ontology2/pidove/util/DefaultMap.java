package com.ontology2.pidove.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Like the defaultdict in Python.  If you get() a key that doesn't have a value,  it sets() a value for it.
 *
 * Among other things,  the DefaultMap is a solution to the multimap problem,  thatis,  you can write
 *
 * var m = new DefaultMap(ArrayList::new);
 * m.get("one").add(7)
 * m.get("one").add(8)
 * m.get("one") // equals List.of(7,8)
 *
 * @param <K> key data type
 * @param <V> value data type
 */
public class DefaultMap<K,V> implements Map<K,V> {

    public final Map<K,V> innerMap;
    public final Supplier<V> defaultValue;

    /**
     * @param innerMap the map contained inside
     * @param defaultValue a supplier that generates default values for the map;  this has to be a Supplier because
     *                     if you want this to be a container like a List you need a new one each time.
     */
    public DefaultMap(Map<K, V> innerMap, Supplier<V> defaultValue) {
        this.innerMap = innerMap;
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new DefaultMap backed by a new HashMap
     *
     * @param defaultValue a supplier that generates default values for the map;  this has to be a Supplier because
     *                     if you want this to be a container like a List you need a new one each time.
     */
    public DefaultMap(Supplier<V> defaultValue) {
        this(new HashMap<>(), defaultValue);
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    /**
     * It's not so clear what this function should return,  since even if a key is not contained in the inner
     * map it would be if you got it.  That kind of thinking would mean isEmpty() is always false and size()
     * undefined in most cases.  If this bothers you than try a different Multimap implementation.
     *
     * @param key key whose presence in this map is to be tested
     * @return the same thing the inner map would return
     */
    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

    /**
     *
     * @param key the key whose associated value is to be returned;  if there is no associated value for the key,
     *            it assigns the default value to that key
     * @return the associated value if it exists,  otherwise the newly created default value;
     */
    @Override
    public V get(Object key) {
        //noinspection SuspiciousMethodCalls
        if(!innerMap.containsKey(key)) {
            //noinspection unchecked
            innerMap.put((K) key, defaultValue.get());
        }

        return innerMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        return innerMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(var e: m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        innerMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return innerMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return innerMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return innerMap.entrySet();
    }
}
