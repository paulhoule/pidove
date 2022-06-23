package com.ontology2.pidove.iterables;

import com.ontology2.pidove.TidyIterableWrapper;
import com.ontology2.pidove.util.Pair;
import com.ontology2.pidove.util.Trio;

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

    @SuppressWarnings("unchecked")
    public static <X> TidyIterable<X> $(Class<X> that) {
        return (TidyIterable<X>) EmptyIterable.that();
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

    public static TidyIterable<Long> $(long start) {
        return $(0,start);
    }
    public static TidyIterable<Long> $(long start, long stop) {
        return $(start, stop, 1);
    }
    public static TidyIterable<Long> $(long start, long stop, long skip) {
        return new RangeIterable(start, skip, stop);
    }

    public static <X,Y> TidyIterable<Pair<X,Y>> $(Iterable<X> x, Iterable<Y> y) {
        return new Zip2Iterable<>(x,y);
    }

    public static <X,Y,Z> TidyIterable<Trio<X,Y,Z>> $(Iterable<X> x, Iterable<Y> y, Iterable<Z> z) {
        return new Zip3Iterable<>(x,y,z);
    };

    @SafeVarargs
    public static <X> TidyIterable<X> $$(X... xs) {
        return new ArrayIterable<>(xs);
    }


}
