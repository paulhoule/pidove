package com.ontology2.pidove.seq;

import javax.naming.OperationNotSupportedException;
import java.util.Iterator;
import java.util.function.BinaryOperator;

import static com.ontology2.pidove.util.DuctTape.uncheck;

public record AccumulateIterable<X>(BinaryOperator<X> func, Iterable<X> value) implements Iterable<X> {

    @Override
    public Iterator<X> iterator() {
        return new AccumulateIterator();
    }

    private class AccumulateIterator implements Iterator<X> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public X next() {
            return null;
        }
    }
}
