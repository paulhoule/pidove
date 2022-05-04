package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;

public class FiniteCycleIterable<X> extends TidyIterable<X> {

    private final long times;
    private final Iterable<X> values;

    public FiniteCycleIterable(long times, Iterable<X> values) {
        this.times = times;
        this.values = values;
    }

    @Override
    public Iterator<X> iterator() {
        return new FiniteCycleIterator();
    }

    private class FiniteCycleIterator implements Iterator<X>, AutoCloseable {
        private Iterator<X> inner;
        boolean isFirst;
        long cycles;

        {
            resetIterator();
        }

        private void resetIterator() {
            if(!isNull(inner)) {
                Iterables.close(inner);
                inner = null;
            }
            if (cycles<times) {
                inner = values.iterator();
                isFirst = true;
                cycles++;
            }
        }

        @Override
        public boolean hasNext() {
            if(isNull(inner))
                return false;

            while(!inner.hasNext()) {
                if(isFirst)
                    return false;
                resetIterator();
                if(isNull(inner))
                    return false;
            }
            isFirst=false;
            return true;
        }

        @Override
        public X next() {
            if(!hasNext())
                throw new NoSuchElementException();

            return inner.next();
        }

        @Override
        public void close() throws Exception {
            Iterables.close(inner);
        }
    }
}
