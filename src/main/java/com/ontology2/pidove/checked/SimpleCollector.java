package com.ontology2.pidove.checked;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record SimpleCollector<Input, Result> (
    Supplier<Result> supplier,
    BiConsumer<Input, Result> accumulator
) implements AnyCollector<Input, Result> {
    public <FinalResult> Collector<Input,Result,FinalResult> andThen(Function<Result,FinalResult> finisher) {
        return Collector.of(supplier(), accumulator(), finisher);
    }
}
