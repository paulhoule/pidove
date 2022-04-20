package com.ontology2.pidove.checked;

import java.util.Iterator;
import java.util.NoSuchElementException;

class RangeIterable implements Iterable<Long> {
    private final long start;
    private final long skip;
    private final long finish;

    public RangeIterable(long start, long skip, long finish) {
        this.start = start;
        this.skip = skip;
        this.finish = finish;
    }

    @Override
    public Iterator<Long> iterator() {
        return new Iterator<>() {
            long i = start;

            @Override
            public boolean hasNext() {
                return skip > 0 ? i < finish : i > finish;
            }

            @Override
            public Long next() {
                if (hasNext()) {
                    try {
                        return i;
                    } finally {
                        i += skip;
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
