package com.ontology2.pidove.seq;

import java.io.InputStream;

public class Resources {
    public static InputStream resource(Object that, String name) {
        return that.getClass().getResourceAsStream(name);
    }
}