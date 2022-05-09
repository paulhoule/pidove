package com.ontology2.pidove.seq;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collector;

import static com.ontology2.pidove.seq.Iterables.collect;

public record WindowIterable<X,Y>(int length, Collector<X,?,Y> collector, Iterable<X> values) implements TidyIterable<Y> {
    @Override
    public Iterator<Y> iterator() {
        return new WindowIterator();
    }

    private class WindowIterator extends TidyIterator<X,Y> {

        public WindowIterator() {
            super(values.iterator());
        }

        Deque<X> window = new ArrayDeque<>(length);
        {
            for (int i = 0; i < length; i++) {
                if (that.hasNext())
                    window.addLast(that.next());
                else {
                    window = null;
                    break;
                }
            }
        }
        @Override
        public boolean hasNext() {
            return window != null;
        }

        @Override
        public Y next() {
            if(window == null)
                throw new NoSuchElementException();
            try {
                return collect(collector(), window);
            } finally {
                if (that.hasNext())  {
                    window.removeFirst();
                    window.addLast(that.next());
                } else
                    window = null;
            }
        }
    }
}
