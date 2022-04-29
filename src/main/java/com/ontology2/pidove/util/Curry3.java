package com.ontology2.pidove.util;

import java.util.function.Function;

public interface Curry3<A, B, C, X> extends Function<A, Function<B, Function<C, X>>> {
}
