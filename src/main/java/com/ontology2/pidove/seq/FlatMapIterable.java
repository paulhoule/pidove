package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

class FlatMapIterable<X, Y> extends TidyIterable<Y> {

    private final Iterable<X> values;
    private final Function<X, ? extends Iterable<Y>> fn;
    Iterator<Y> current;

    public FlatMapIterable(Iterable<X> values, Function<X, ? extends Iterable<Y>> fn) {
        this.values = values;
        this.fn = fn;
        current = null;
    }

    @Override
    public Iterator<Y> iterator() {
        return new FlatMapIterator(values);
    }

    private class FlatMapIterator extends TidyIterator<X, Y> {
        public FlatMapIterator(Iterable<X> that) {
            super(that.iterator());
        }

        @Override
        public boolean hasNext() {
            while (true) {
                if (current == null || !current.hasNext()) {
                    Iterables.close(current);
                    if (that.hasNext()) {
                        current = fn.apply(that.next()).iterator();
                    } else {
                        current = null;
                        return false;
                    }
                }

                if (current.hasNext()) {
                    return true;
                }
            }
        }

        @Override
        public Y next() {
            while (true) {
                if (current == null || !current.hasNext()) {
                    Iterables.close(current);
                    if (that.hasNext()) {
                        current = fn.apply(that.next()).iterator();
                    } else {
                        current = null;
                        throw new NoSuchElementException();
                    }
                }

                if (current.hasNext()) {
                    return current.next();
                }
            }
        }

        @Override
        public void close() throws Exception {
            super.close();
            Iterables.close(current);
        }
    }
}
