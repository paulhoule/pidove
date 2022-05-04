package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

public class CycleIterable<X> implements TidyIterable<X> {
    private final Iterable<X> values;

    public CycleIterable(Iterable<X> values) {
        this.values = values;
    }

    @Override
    public Iterator<X> iterator() {
        return new CycleIterator();
    }

    private class CycleIterator implements Iterator<X>, AutoCloseable {
        private Iterator<X> inner;
        boolean isFirst;

        {
            resetIterator();
        }

        private void resetIterator() {
            if(!isNull(inner)) {
                Iterables.close(inner);
                inner = null;
            }
            inner = values.iterator();
            isFirst = true;
        }

        @Override
        public boolean hasNext() {
            while(!inner.hasNext()) {
                if(isFirst)
                    return false;
                resetIterator();
            }
            isFirst=false;
            return true;
        }

        @Override
        public X next() {
            while(!inner.hasNext()) {
                if(isFirst)
                    throw new NoSuchElementException();
                resetIterator();
            }
            isFirst=false;
            return inner.next();
        }

        @Override
        public void close() throws Exception {
            Iterables.close(inner);
        }
    }
}
