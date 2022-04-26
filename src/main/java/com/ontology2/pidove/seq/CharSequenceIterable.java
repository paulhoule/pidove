package com.ontology2.pidove.seq;

import java.util.Iterator;
import java.util.NoSuchElementException;

class CharSequenceIterable implements Iterable<Character> {

    private final CharSequence s;

    public CharSequenceIterable(CharSequence s) {
        this.s = s;
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < s.length();
            }

            @Override
            public Character next() {
                if (i < s.length()) {
                    return s.charAt(i++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
