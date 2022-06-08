package com.ontology2.pidove.iterables;

import com.ontology2.pidove.TidyIterableWrapper;
import com.ontology2.pidove.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static com.ontology2.pidove.util.DuctTape.unchecked;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Dollar {

    public static TidyIterable<?> $() {
        return EmptyIterable.that();
    }

    public static <X> TidyIterable<X> $(Iterable<X> x) {
        if (x instanceof TidyIterable<X> tidy) {
            return tidy;
        }

        return new TidyIterableWrapper<>(x);
    }

    public static TidyIterable<Character> $(CharSequence s) {
        return new CharSequenceIterable(s);
    }

    public static <X> TidyIterable<X> $(final X[] x) {
        return new ArrayIterable<>(x);
    }

    public static <X,Y> TidyIterable<Pair<X,Y>> $(final Map<X,Y> that) {
        return $(that.entrySet()).map(e->new Pair<>(e.getKey(), e.getValue()));
    }

    public static TidyIterable<String> $(Path path) {
        return new LinesIterable(unchecked(() -> Files.newBufferedReader(path, UTF_8)));
    }

    public static TidyIterable<String> $(SupplierOfInputStream source) {
        return new LinesIterable(() -> new BufferedReader(new InputStreamReader(source.get(), UTF_8)));
    }

    public static TidyIterable<String> $(SupplierOfBufferedReader source) {
        return new LinesIterable(source);
    }


}
