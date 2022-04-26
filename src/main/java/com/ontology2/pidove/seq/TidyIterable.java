package com.ontology2.pidove.seq;

import java.util.function.Consumer;

/**
 *
 * A CleanIterable promises that the Iterator returned by the iterator() method
 * also implements AutoClosable if that Iterator holds resources (just as open
 * files) that need to be closed
 *
 * @param <X>
 */
public abstract class TidyIterable<X> implements Iterable<X> {
    /**
     * Overrides the default implementation such that the Iterator used internally
     * will be closed at the end of this method if that Iterator implements
     * AutoClosable
     *
     * @param action The action to be performed for each element
     */
    @Override
    public void forEach(Consumer<? super X> action) {
        Iterables.forEach(action, this);
    }
}
