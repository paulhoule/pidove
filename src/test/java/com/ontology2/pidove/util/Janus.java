package com.ontology2.pidove.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Janus<X> {
    X value;

    public Janus(X value) {
        this.value=value;
    }

    public Supplier<X> supplier() {
      return ()->value;
    }

    public Consumer<X> consumer() {
        return (newValue)-> { value = newValue;};
    }
}
