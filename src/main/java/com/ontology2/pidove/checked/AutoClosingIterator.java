package com.ontology2.pidove.checked;

import java.util.Iterator;

abstract public class AutoClosingIterator<X> implements AutoCloseable,Iterator<X> {
    /**
     *
     * Base class for an iterator that contains another iterator,  such as the MapIterator.
     *
     */
    protected final Iterator<X> that;
    public AutoClosingIterator(Iterator<X> that) {
        this.that = that;
    }

    /**
     *
     * If the innerIterator is AutoCloseable,  close() calls the close method of the
     * innerIterator,  otherwise does nothing
     *
     * @throws Exception if closing the innerIterator throws an Exception
     */
    @Override
    public void close() throws Exception {
        Iterables.close(that);
    }
}
