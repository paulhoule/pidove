package com.ontology2.pidove.seq;

import com.ontology2.pidove.util.Trio;

import java.util.Iterator;

public class Zip3Iterable<X,Y,Z> implements Iterable<Trio<X, Y, Z>> {
    private final Iterable<X> one;
    private final Iterable<Y> two;
    private final Iterable<Z> three;

    public Zip3Iterable(Iterable<X> one, Iterable<Y> two, Iterable<Z> three) {
        this.one=one;
        this.two=two;
        this.three=three;
    }

    @Override
    public Iterator<Trio<X, Y, Z>> iterator() {
        return new Zip3Iterator();
    }

    private class Zip3Iterator implements Iterator<Trio<X, Y, Z>>,AutoCloseable {

        final private Iterator<X> a = one.iterator();
        final private Iterator<Y> b = two.iterator();
        final private Iterator<Z> c = three.iterator();

        @Override
        public boolean hasNext() {
            return a.hasNext() && b.hasNext() && c.hasNext();
        }

        @Override
        public Trio<X, Y, Z> next() {
            return new Trio<>(a.next(), b.next(), c.next());
        }

        @Override
        public void close() throws Exception {
            try {
                Iterables.close(a);
            } finally {
                try {
                    Iterables.close(b);
                } finally {
                    Iterables.close(c);
                }
            }

        }
    }
}
