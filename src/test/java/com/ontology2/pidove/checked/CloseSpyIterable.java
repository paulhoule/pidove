package com.ontology2.pidove.checked;

import java.util.Iterator;

public class CloseSpyIterable<X> implements Iterable<X> {
    final Iterable<X> innerIterable;
    int closeCount=0;

    public CloseSpyIterable(Iterable<X> innerIterable) {
        this.innerIterable = innerIterable;
    }

    @Override
    public Iterator<X> iterator() {
        return new CloseSpyIterator(innerIterable.iterator());
    }

    public int getCloseCount() {
        return closeCount;
    }

    class CloseSpyIterator extends AutoClosingIterator<X,X> {

        public CloseSpyIterator(Iterator<X> that) {
            super(that);
        }

        @Override
        public boolean hasNext() {
            return that.hasNext();
        }

        @Override
        public X next() {
            return that.next();
        }

        @Override
        public void close() throws Exception {
            closeCount ++;
            super.close();
        }
    }
}
