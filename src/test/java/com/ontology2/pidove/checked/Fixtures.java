package com.ontology2.pidove.checked;

public class Fixtures {
    public static <X> CloseSpyIterable<X> closeSpy(Iterable<X> that) {
        return new CloseSpyIterable<>(that);
    }
}
