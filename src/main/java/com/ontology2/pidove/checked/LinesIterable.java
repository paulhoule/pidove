package com.ontology2.pidove.checked;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static com.ontology2.pidove.checked.Exceptions.uncheck;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

class LinesIterable extends TidyIterable<String> {
    private final Supplier<BufferedReader> source;

    public LinesIterable(Supplier<BufferedReader> source) {
        this.source = source;
    }

    @Override
    public Iterator<String> iterator() {
        return new LinesIterator(source.get());
    }

    private static class LinesIterator implements Iterator<String>,AutoCloseable {
        private final BufferedReader bufferedReader;
        private String nextLine;

        public LinesIterator(BufferedReader bufferedReader) {
            this.bufferedReader=bufferedReader;
            readNextItem();
        }

        private void readNextItem() {
            nextLine=uncheck(() -> bufferedReader.readLine());
        }

        @Override
        public boolean hasNext() {
            return nonNull(nextLine);
        }

        @Override
        public String next() {
            if(isNull(nextLine)) {
                throw new NoSuchElementException();
            }
            try {
                return nextLine;
            } finally {
                readNextItem();
            }
        }

        @Override
        public void close() throws IOException {
            bufferedReader.close();
        }
    }
}
