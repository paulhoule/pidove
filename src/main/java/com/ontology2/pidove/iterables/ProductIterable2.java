package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;

import java.util.Iterator;
import java.util.NoSuchElementException;

record ProductIterable2<X,Y>(Iterable<X> left, Iterable<Y> right) implements TidyIterable<Pair<X, Y>> {

    @Override
    public Iterator<Pair<X, Y>> iterator() {
        return new ProductIterator2();
    }

    private class ProductIterator2 implements Iterator<Pair<X, Y>>,AutoCloseable {
        Iterator<X> l = left.iterator();
        Iterator<Y> r = right.iterator();
        X currentL;
        State state = State.START;

        
        @Override
        public boolean hasNext() {
            if(state==State.START) {
                if(l.hasNext()) {
                    currentL = l.next();
                    state = State.RUN;
                } else
                    return false;
            }

            if(r.hasNext())
                return true;
            else if(l.hasNext()) {
                currentL = l.next();
                Iterables.close(r);
                r = right.iterator();
                return r.hasNext();
            }
            return false;
        }

        @Override
        public Pair<X, Y> next() {
            if(hasNext())
                return new Pair<>(currentL,r.next());
            else
                throw new NoSuchElementException();
        }

        @Override
        public void close() throws Exception {
            try {
                Iterables.close(l);
            } finally {
                Iterables.close(r);
            }
        }

        enum State {START, RUN}
    }
}
