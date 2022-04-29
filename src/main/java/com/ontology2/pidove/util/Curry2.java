package com.ontology2.pidove.util;

import java.util.function.Function;

public interface Curry2<A, B, C> extends Function<A, Function<B, C>> {
}
