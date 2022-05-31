package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Trio;

import java.util.Iterator;
import java.util.NoSuchElementException;

record ProductIterable3<X,Y,Z>(Iterable<X> left, Iterable<Y> middle,
                               Iterable<Z> right) implements Iterable<Trio<X, Y, Z>> {
    @Override
    public Iterator<Trio<X, Y, Z>> iterator() {
        return new ProductIterator3();
    }

    private class ProductIterator3 implements Iterator<Trio<X, Y, Z>>,AutoCloseable {
        Iterator<X> l = left.iterator();
        Iterator<Y> m = middle.iterator();
        Iterator<Z> r = right.iterator();
        X currentL;
        Y currentM;
        State state = State.START;

        @Override
        public boolean hasNext() {
            if(state== State.START) {
                if(l.hasNext()) {
                    currentL = l.next();
                } else
                    return false;

                if(m.hasNext()) {
                    currentM = m.next();
                    state = State.RUN;
                } else
                    return false;
            }

            if(r.hasNext())
                return true;
            else if(m.hasNext()) {
                currentM = m.next();
                Iterables.close(r);
                r = right.iterator();
                return r.hasNext();
            } else if(l.hasNext()) {
                currentL = l.next();
                Iterables.close(m);
                m = middle.iterator();
                if(m.hasNext()) {
                    currentM = m.next();
                    Iterables.close(r);
                    r=right.iterator();
                    return true;
                }
            }
            return false;
        }

        @Override
        public Trio<X, Y, Z> next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return Trio.of(currentL, currentM, r.next());
        }

        @Override
        public void close() throws Exception {
            try {
                Iterables.close(l);
            } finally {
                try {
                    Iterables.close(m);
                } finally {
                    Iterables.close(r);
                }
            }
        }

        enum State {START, RUN}
    }
}
