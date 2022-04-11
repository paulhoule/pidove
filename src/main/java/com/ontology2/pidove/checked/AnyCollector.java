package com.ontology2.pidove.checked;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public sealed interface AnyCollector<Input,Result> permits SimpleCollector, Collector {
}
