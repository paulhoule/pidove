package com.ontology2.pidove.checked;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record Collector<Input, Container, Result>(
        Supplier<Container> supplier,
        BiConsumer<Input, Container> accumulator,
        Function<Container, Result> finisher
) implements AnyCollector<Input, Result> {

    public static <Input, Container, Result> Collector<Input,Container,Result> of(
            Supplier<Container> supplier,
            BiConsumer<Input, Container> accumulator,
            Function<Container, Result> finisher)
    {
        return new Collector<>(supplier, accumulator, finisher);
    }

    public static <Input, Result> SimpleCollector<Input,Result> of(
            Supplier<Result> supplier,
            BiConsumer<Input, Result> accumulator)
    {
        return new SimpleCollector<>(supplier, accumulator);
    }

    public <RR> Collector<Input,?,RR> andThen(Function<Result,RR> finisher) {
        return Collector.of(
                supplier(), accumulator(), finisher().andThen(finisher)
        );
    }
}

