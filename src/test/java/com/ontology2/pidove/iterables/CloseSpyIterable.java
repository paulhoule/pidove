package com.ontology2.pidove.iterables;

import java.util.Iterator;

public class CloseSpyIterable<X> implements Iterable<X> {
    final Iterable<X> innerIterable;
    int openCount=0;
    int closeCount=0;


    public CloseSpyIterable(Iterable<X> innerIterable) {
        this.innerIterable = innerIterable;
    }

    @Override
    public Iterator<X> iterator() {
        openCount++;
        return new CloseSpyIterator(innerIterable.iterator());
    }

    public int getOpenCount() {
        return openCount;
    }

    public int getCloseCount() {
        return closeCount;
    }

    public boolean isBalanced() {
        return openCount == closeCount;
    }

    class CloseSpyIterator extends TidyIterator<X,X> {

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
