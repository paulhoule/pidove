package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.Pair;

import java.util.Iterator;

class Zip2Iterable<X,Y> implements TidyIterable<Pair<X, Y>> {
    private final Iterable<X> one;
    private final Iterable<Y> two;

    public Zip2Iterable(Iterable<X> one, Iterable<Y> two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public Iterator<Pair<X, Y>> iterator() {
        return new Zip2Iterator<>(one.iterator(), two.iterator());
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
        public void close() {
            try {
                Iterables.close(a);
            } finally {
                Iterables.close(b);
            }
        }
    }
}
