package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Wrapper for a function that,  given an iterator,  returns another iterator.
 *
 * The source iterator should not be closed by gather but rather it will be
 * closed when this iterator gets closed.
 *
 * @param <X>
 */
class PrecaclulatedIterable<X> implements TidyIterable<X> {
    private final Iterable<X> source;
    private final Function<Iterator<X>, Iterator<X>> gather;

    PrecaclulatedIterable(Iterable<X> source, Function<Iterator<X>, Iterator<X>> gather) {
        this.source = source;
        this.gather = gather;
    }

    @Override
    public Iterator<X> iterator() {
        return new PrecalculatedIterator();
    }

    private class PrecalculatedIterator extends TidyIterator<X,X> {

        public PrecalculatedIterator() {
            super(source.iterator());
        }

        Iterator<X> result = gather.apply(that);

        @Override
        public boolean hasNext() {
            return result.hasNext();
        }

        @Override
        public X next() {
            return result.next();
        }
    }
}
