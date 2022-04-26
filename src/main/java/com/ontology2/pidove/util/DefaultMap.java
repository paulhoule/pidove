package com.ontology2.pidove.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class DefaultMap<K,V> implements Map<K,V> {

    public final Map<K,V> innerMap;
    public final Supplier<V> defaultValue;

    public DefaultMap(Map<K, V> innerMap, Supplier<V> defaultValue) {
        this.innerMap = innerMap;
        this.defaultValue = defaultValue;
    }


    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

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
