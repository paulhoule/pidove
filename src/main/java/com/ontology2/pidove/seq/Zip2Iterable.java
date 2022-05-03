package com.ontology2.pidove.seq;

import com.ontology2.pidove.util.Pair;

import java.util.Iterator;

public class Zip2Iterable<X,Y> extends TidyIterable<Pair<X, Y>> {
    private final Iterable<X> one;
    private final Iterable<Y> two;

    public Zip2Iterable(Iterable<X> one, Iterable<Y> two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public Iterator<Pair<X, Y>> iterator() {
        return new Zip2Iterator<X,Y>(one.iterator(), two.iterator());
    }

    private record Zip2Iterator<X, Y>(Iterator<X> a, Iterator<Y> b) implements Iterator<Pair<X, Y>>,AutoCloseable {

        @Override
        public boolean hasNext() {
            return a.hasNext() & b.hasNext();
        }

        @Override
        public Pair<X, Y> next() {
            return new Pair<>(a.next(),b.next());
        }

        @Override
        public void close() throws Exception {
            try {
                Iterables.close(a);
            } finally {
                Iterables.close(b);
            }
        }
    }
}
