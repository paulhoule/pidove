package com.ontology2.pidove.iterables;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Note ReversedIterable uses a Deque instead of a List so it's possible objects could be
 * deallocated before the iteration is done
 *
 * @param <X>
 */
class ReversedIterable<X> implements TidyIterable<X> {
    private final Iterable<X> values;

    public ReversedIterable(Iterable<X>  values) {
        this.values = values;
    }

    @Override
    public Iterator<X> iterator() {
        return new ReversedIterator();
    }

    public class ReversedIterator implements Iterator<X> {
        Deque<X> deck=new ArrayDeque<>();

        {
            Iterables.forEach(deck::addLast, values);
        }

        @Override
        public boolean hasNext() {
            return !deck.isEmpty();
        }

        @Override
        public X next() {
            return deck.removeLast();
        }
    }
}
