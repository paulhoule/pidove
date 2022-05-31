package com.ontology2.pidove.util;

@FunctionalInterface
public interface ExceptionalSupplier<X> {
    X get() throws Exception;
}
