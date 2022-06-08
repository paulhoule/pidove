package com.ontology2.pidove.iterables;

import com.ontology2.pidove.util.DuctTape;
import com.ontology2.pidove.util.Pair;
import com.ontology2.pidove.util.Trio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.ontology2.pidove.util.DuctTape.unchecked;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static java.util.function.Function.*;

public class Iterables {
    /**
     * Imitates the "all" function from Python.  Stops iterating when the first
     * false value is seen.
     *
     * @param values an iterable of boolean values
     * @return true if all values are true,  otherwise false
     */
    public static boolean all(Iterable<Boolean> values) {
        Iterator<Boolean> that = values.iterator();
        try {
            while (that.hasNext()) {
                if (!that.next())
                    return false;
            }

            return true;
        } finally {
            close(that);
        }
    }

    /**
     * Imitates the "any" function from Python.  Stops iterating when the first true
     * value is seen
     *
     * @param values an iterable of boolean values
     * @return true if any values is true,  otherwise false
     */

    public static boolean any(Iterable<Boolean> values) {
        Iterator<Boolean> that = values.iterator();
        try {
            for(boolean b: values) {
                if(b)
                    return true;
            }

            return false;
        } finally {
            close(that);
        }
    }

    /**
     * Imitates the collect() method in the streams API
     *
     * @param collector a collector
     * @param values an iterable
     * @return the result of the collector,  of type Z
     * @param <X> the type of the values in the iterable
     * @param <Y> the type of the data structure that the collector store immediate results in
     * @param <Z> the type of the result returned by the collector.
     */
    public static <X,Y,Z> Z collect(Collector<? super X, Y, Z> collector, Iterable<X> values) {
        var container = collector.supplier().get();
        forEach((X item) -> collector.accumulator().accept(container, item) , values);
        if(collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            //noinspection unchecked
            return (Z) container;
        }
        return collector.finisher().apply(container);
    }

    /**
     * Imitates the "concat" function in the Java Streams API.
     *
     * @param values zero or more iterables
     * @return the values in all of the iterables concatenated together
     * @param <X> the type of the values
     */

    @SafeVarargs
    public static <X> TidyIterable<X> concat(Iterable<X>... values) {
        return new ConcatIterable<>(values);
    }


    /**
     * Imitates the "count" method in the Java Streams API
     *
     * @param values an iterable
     * @return how many values are returned by the iterable
     * @param <X> the type of the values in the iterable
     */
    public static <X> long count(Iterable<X> values) {
        return collect(Collectors.counting(), values);
    }

    /**
     * Imitates the "distinct" method in the java streams API.  Is a little different
     * from other methods because it materializes the set and returns the set which is
     * an Iterable,  but it doesn't get updated if the base Iterable changes the
     * way other functions in this class do
     *
     * @param values an iterable
     * @return a set of all distinct values that appear in said iterable
     * @param <X> the type of values of the iterable
     */
    public static <X> Set<X> distinct(Iterable<X> values) {
        return asSet(values);
    }

    /**
     * Imitates the "dropWhile" method in the java streams API.
     *
     * @param predicate boolean function of an X value
     * @param values a set of values
     * @return an iterable that starts at the first point in <i>values</i> where the predicate returns false
     * @param <X> the type of the iterable
     */
    public static <X> TidyIterable<X> dropWhile(Predicate<? super X> predicate, Iterable<X> values) {
        return new DropWhileIterable<>(values, predicate);
    }

    /**
     * Imitates the "empty" method in the Java Streams API
     *
     * @return an empty iterable
     */
    public static Iterable<?> empty() {
        return EmptyIterable.get();
    }

    /**
     * Imitates the "first" method in the Java Stream API
     *
     * @param values an interable
     * @return an Optional with the first value in the iterable unless the iterable is empty in which case returns
     *         Optional.empty()
     * @param <X> type of values in iterable
     */
    public static <X> Optional<X> first(Iterable<X> values) {
        var that = values.iterator();
        try {
            if(that.hasNext()) {
                return Optional.of(that.next());
            } else {
                return Optional.empty();
            }
        } finally {
            close(that);
        }
    }

    /**
     * Imitates the filter method in the Streams API
     *
     * @param predicate a boolean function of X
     * @param values an iterable
     * @return an iterable of all values from values where predicate is true
     * @param <X> the type of the input iterable
     */
    public static <X> TidyIterable<X> filter(Predicate<? super X> predicate, Iterable<X> values) {
        return new FilterIterable<>(values, predicate);
    }

    /**
     *
     * skipped
     *
     * @param values an Iterable of Iterables
     * @return the values returned by all the inner iterables
     * @param <X> the type of the input Iterable
     */
    public static <X> TidyIterable<X> flatten(Iterable<? extends Iterable<X>> values) {
        return flatMap(identity(), values);
    }

    /**
     *
     * Imitates the flatmap method in the Java Streams API
     *
     * @param fn a function that turns X into an iterable of Y
     * @param values an iterable
     * @return an iterable with all the values of type Y returned by fn
     * @param <X> the input iterable type
     * @param <Y> the output iterable type
     */
    public static <X,Y> TidyIterable<Y> flatMap(Function<X, ? extends Iterable<Y>> fn, Iterable<X> values) {
        return new FlatMapIterable<>(values, fn);
    }

    /**
     *
     * skipped
     *
     * Similar to the generate method in the Java Streams API but sane.  That is,
     * instead of taking a Supplier are an argument it takes a Supplier of a Supplier,  so
     * that like a normal Iterable it can start the iteration fresh each time
     *
     * @param source a supplier that generates a supplier that supplies values of type X
     * @return values returned by the innter supplier
     * @param <X> the output type
     */
    public static <X> Iterable<X> generate(Supplier<Supplier<X>> source) {
        return new GenerateIterable<>(source);
    }

    /**
     *
     * skipped
     *
     * Imitates the iterate method of the Java Streams API.
     *
     * @param seed the first value
     * @param f a function that is applied repeatedly to the seed then it's own output to getnerate the values
     *          out of the output iterable
     * @return values generated by that function
     * @param <X> type of values in the outut iterable
     */
    public static <X> Iterable<X> iterate(X seed, UnaryOperator<X> f) {
        return new IterateIterable<>(seed, f);
    }

    /**
     * Imitates the limit method of the Java Streams API
     *
     * @param amount how many values to take
     * @param values an iterable
     * @return the first <i>amount</i> values of the iterable
     * @param <X> type of values in iterable
     */
    public static <X> TidyIterable<X> limit(final int amount, Iterable<X> values) {
        return new LimitIterable<>(values, amount);
    }

    /**
     * Imitates the map method of the Java Streams API
     *
     * @param fn a function from X to Y
     * @param values an iterable
     * @return an iterable produced by applying fn to all values of the input iterable
     * @param <X> input iterable type
     * @param <Y> output iterable type
     */
    public static <X,Y> TidyIterable<Y> map(Function<X,Y> fn, Iterable<X> values) {
        return new MapIterable<>(values, fn);
    }

    /**
     * Imitates the max method of the Java Streams API
     *
     * @param values an iterable
     * @param comparator a comparison function over the iterable
     * @return the maximum value determined by that comparison function
     * @param <X> the type of values in the input iterable
     */
    public static <X> Optional<X> max(final Iterable<X> values, Comparator<X> comparator) {
        return collect(Collectors.maxBy(comparator), values);
    }

    /**
     * skipped
     *
     * Similar to the max method of the Java Streams API but uses the natural ordering
     * for convenience.
     *
     * @param values an iterable of comparable values
     * @return the maximum value determined by the natural order over X
     * @param <X> the type of values in the input iterable,  bounded above by Comparable
     */
    public static <X extends Comparable<X>> Optional<X> max(final Iterable<X> values) {
        return max(values, Comparator.naturalOrder());
    }

    /**
     * Imitates the min method of the Java Streams API
     *
     * @param values an iterable
     * @param comparator a comparison function over the iterable
     * @return the minimum value determined by that comparison function
     * @param <X> the type of values in the input iterable
     */
    public static <X> Optional<X> min(Comparator<X> comparator, final Iterable<X> values) {
        return collect(Collectors.minBy(comparator), values);
    }

    /**
     * skipped
     *
     * Similar to the min method of the Java Streams API but uses the natural ordering
     * for convenience.
     *
     * @param values an iterable of comparable values
     * @return the minimum value determined by the natural order over X
     * @param <X> the type of values in the input iterable,  bounded above by Comparable
     */

    public static <X extends Comparable<X>> Optional<X> min(final Iterable<X> values) {
        return min(Comparator.naturalOrder(), values);
    }


    /**
     * skipped
     *
     * Similar to the noneMatch function of the Java Streams API but with a pythonic
     * signature.  Stops iterating and Returns early if any value is true.
     *
     * @param values an iterable of boolean values
     * @return true if all values are false,  otherwise false
     */
    public static boolean none(final Iterable<Boolean> values) {
        var that = values.iterator();
        try {
            while(that.hasNext()) {
                if(that.next())
                    return false;
            }
            return true;
        } finally {
            close(that);
        }
    }

    /**
     * skipped
     *
     * Imitates the of method of the Java Streams API
     *
     * @param x a value of type X
     * @return an iterable that has only value x in it
     * @param <X> the type of values in the output iterable
     */
    public static <X> Iterable<X> of(final X x) {
        return new SingleItemIterable<>(x);
    }

    /**
     * skipped
     *
     * Imitates the of method of the Java Streams API
     *
     * @param x an arbitrary number of values of type X
     * @return those values in an iterable
     * @param <X> type of the output iterable
     */
    @SafeVarargs
    public static <X> Iterable<X> of(final X ...x) {
        return new ArrayIterable<>(x);
    }

    /**
     * skipped
     *
     * Imitates the ofNullable method of the java streams API
     *
     * @param x a value
     * @return a single item iterable with that value unless the value is null in which case an empty iterable
     * @param <X> the type of the output iterable
     */
    public static <X> Iterable<X> ofNullable(final X x) {
        //noinspection unchecked
        return isNull(x) ? (Iterable<X>) EmptyIterable.get() : new SingleItemIterable<>(x);
    }

    /**
     * Unique to pidove
     *
     * @param s a CharSequence (ex. a String)
     * @return the characters in the CharSequence as an Iterable
     */
    public static Iterable<Character> over(CharSequence s) {
        return new CharSequenceIterable(s);
    }

    /**
     * Unique to pidove
     *
     * @param x an array
     * @return an iterable of the values in the array
     * @param <X> type of the output iterable
     */
    public static <X> Iterable<X> over(final X[] x) {
        return new ArrayIterable<>(x);
    }

    /**
     * Unique to pidove.  Similar to iterating over the items() of a map but returns sane immutable
     * pairs that have nothing to do with the internals of the Map
     *
     * @param that a Map
     * @return key-value pairs
     * @param <X> type of the keys
     * @param <Y> type of the pairs
     */
    public static <X,Y> Iterable<Pair<X,Y>> over(final Map<X,Y> that) {
        return map(e->new Pair<>(e.getKey(), e.getValue()), that.entrySet());
    }

    /**
     * Unique to pidove
     *
     * Given a path,  opens the file in character mode and returns an iterable of the
     * lines in the file
     *
     * @param path a path to the file system
     * @return lines in the file
     */
    public static Iterable<String> over(Path path) {
        return new LinesIterable(unchecked(() -> Files.newBufferedReader(path, UTF_8)));
    }

    /**
     * Unique to pidove
     *
     * Given a Supplier of an Input Stream,  opens the stream and iterates over lines in the file.
     * Note that if we used a Supplier&lt;InputStream&gt; here we couldn't also make an over
     * method that takes a Supplier&lt;BufferedReader&gt; thanks to type erasure.  Unerasing the
     * type by subclassing Supplier to make it complete makes it easy to supply a lambda here.
     *
     * @param source a supplier of an input stream
     * @return '\n'-separated lines from the input stream
     */
    public static Iterable<String> over(SupplierOfInputStream source) {
        return new LinesIterable(() -> new BufferedReader(new InputStreamReader(source.get(), UTF_8)));
    }

    /**
     * Unique to pidove
     *
     * Given a Supplier of an Input Stream,  opens the stream and iterates over lines in the file.
     * Note that if we used a Supplier&lt;BufferedReader&gt; here we couldn't also make an over
     * method that takes a Supplier&lt;InputStream&gt; thanks to type erasure.  Unerasing the
     * type by subclassing Supplier to make it complete makes it easy to supply a lambda here.
     *
     * @param source a supplier of an input stream
     * @return '\n'-separated lines from the input stream
     */
    public static Iterable<String> over(SupplierOfBufferedReader source) {
        return new LinesIterable(source);
    }

    /**
     * skipped
     *
     * Unique to pidove
     *
     * @param regex regular expression
     * @param source source string
     * @return Iterable of parts of the input string split by the regex
     */
    public static Iterable<String> splitOn(String regex, String source) {
        return over(source.split(regex));
    }

    /**
     * skipped
     *
     * Unique to pidove
     *
     * @param regex regular expression
     * @param limit maximum number of splits
     * @param source source string
     * @return  Iterable of parts of the input string split by the regex
     */
    public static Iterable<String> splitOn(String regex, int limit, String source) {
        return over(source.split(regex, limit));
    }

    /**
     * Imitates the peek method from the Java Streams API
     *
     * @param listener this consuemr receives a copy of each item from the iterable as it is received
     * @param values an input iterable
     * @return the same values as the input iterable
     * @param <X> type of the input and output iterables
     */
    public static <X> TidyIterable<X> peek(Consumer<X> listener, final Iterable<X> values) {
        return new PeekIterable<>(values, listener);
    }

    /**
     * skipped
     *
     * This is like the range function from the Python standard library.
     *
     * range(4) produces the values 0,1,2,3
     *
     * @param stop iteration starts at zero and stops when we reach stop
     * @return an iterable that counts from 0 to stop
     */
    public static Iterable<Long> range(long stop) {
        return range(0, stop, 1);
    }

    /**
     * skipped
     *
     * This is like the range function from the Python standard library
     *
     * range(10,12) produces the values 10,11
     *
     * @param start iteration starts at this value
     * @param stop iteration ends at this value
     * @return an iterable that counts from start to stop
     */
    public static Iterable<Long> range(long start, long stop) {
        return range(start, stop, 1);
    }

    /**
     * skipped
     *
     * This is like the range function from the Python standard library
     *
     * @param start the beginning of the range
     * @param stop iteration stops after we are equal to stop, greater, or less depending on if step is positive or negative
     * @param skip how much we add for each iteration
     * @return an iterable that counts from start to stop with skip as stride
     */
    public static Iterable<Long> range(long start, long stop, long skip) {
        return new RangeIterable(start, skip, stop);
    }

    /**
     * Imitates the reduce method from the Java Streams API
     *
     * @param accumulator a binary operator that combines two values of type X
     * @param values an iterable
     * @return the result of applying the binary operator repeatedly over values from the iterable in order
     *         wrapped in an optional,  empty if values is empty
     * @param <X> type of the iterable's values
     */
    public static <X> Optional<X> reduce(BinaryOperator<X> accumulator, final Iterable<X> values) {
        return collect(Collectors.reducing(accumulator), values);
    }

    /**
     * Imitates the reduce method from the Java Streams API
     *
     * @param identity initial value
     * @param accumulator a binary operator that combines two values of type X
     * @param values the result of applying the binary operator repeatedly over values from the iterable in order
     * @return the result of applying the binary operator repeatedly over values from the iterable in order,
     *         identity if the iterable is empty
     * @param <X> type of the iterable's values
     */
    public static <X> X reduce(X identity,BinaryOperator<X> accumulator, final Iterable<X> values) {
        return collect(Collectors.reducing(identity, accumulator), values);
    }

    /**
     * Imitates the skip method from the Java Streams API
     *
     * @param amount number of items to skip
     * @param values an iterable
     * @return items from the input iterable skipping the first amount
     * @param <X> type of items from the iterable
     */
    public static <X> TidyIterable<X> skip(final int amount, final Iterable<X> values) {
        return new SkipIterable<>(values, amount);
    }

    /**
     * skipped
     *
     * Convenience method to get sum of integer Iterable
     *
     * @param values Iterable with integer items
     * @return the sum of the iterable's items
     */

    public static int sumInt(Iterable<Integer> values) {
        return collect(Collectors.summingInt(x-> x), values);
    }

    /**
     * skipped
     *
     * Convenience method to get sum of Long Iterable
     *
     * @param values Iterable with long items
     * @return the sum of the iterable's items
     */

    public static long sumLong(Iterable<Long> values) {
        return collect(Collectors.summingLong(x->x), values);
    }

    /**
     * skipped
     *
     * Convenience method to get sum of Double Iterable
     *
     * @param values Iterable with double items
     * @return the sum of the iterable's items
     */

    public static double sumDouble(Iterable<Double> values) {
        return collect(Collectors.summingDouble(x->x), values);
    }

    /**
     * Imitates the takeWhile method of the Java Streams API
     *
     * Stops iterating when the predicate evaluates false
     *
     * @param predicate boolean-valued function of X
     * @param values an iterable
     * @return items from values so long as the predicate evaluates true
     * @param <X> type of the items in the Iterable
     */
    public static <X> TidyIterable<X> takeWhile(Predicate<? super X> predicate, Iterable<X> values) {
        return new TakeWhileIterable<>(values, predicate);
    }

    /**
     * Imitates the toList method of the Java Streams API
     *
     * @param values an iterable
     * @return a List populated with items of the iterable in order
     * @param <X> type of items in the iterable
     */
    public static <X> List<X> asList(Iterable<X> values) {
        return collect(Collectors.toList(), values);
    }

    /**
     * Imitates the forEach/forEachOrdered methods of the Java Stream API since
     * pidove does an ordered iteration.
     *
     * Unlike the Streams API,  this method closes the iterator when the iteration
     * is done if the iterator supports AutoClosable.
     *
     * @param action consumer called for each item in the iterable
     * @param values an iterable
     * @param <X> type of items in the iterable
     */
    public static <X> void forEach(Consumer<? super X> action, Iterable<X> values) {
        var source = values.iterator();
        try {
            while (source.hasNext()) {
                action.accept(source.next());
            }
        } finally {
            close(source);
        }
    }

    /**
     * Imitates the toSet method in the Java Streams API
     *
     * @param values an iterable
     * @return a set of the unique members of the iterable
     * @param <X> type of items in the iterable
     */
    public static <X> Set<X> asSet(Iterable<X> values) {
        return collect(Collectors.toSet(), values);
    }

    /**
     * Unique to pidove.
     *
     * @param separator separator between items
     * @param values an iterable
     * @return a string consisting of iterable items alternating with separators
     */
    public static String joinOn(CharSequence separator,Iterable<? extends CharSequence> values) {
        return collect(Collectors.joining(separator), values);
    }

    /**
     *
     * skipped
     *
     * @param pairs an iterable of key value pairs
     * @return a map with those key value pairs
     * @param <X> type of the keys
     * @param <Y> type of the values
     */
    public static <X,Y> Map<X,Y> asMap(Iterable<Pair<X,Y>> pairs) {
        return collect(Collectors.toMap(Pair::left, Pair::right), pairs);
    }

    /**
     * Given an arbitrary object,  calls the close method if it is AutoCloseable,  otherwise
     * do nothing
     *
     * @param that any Object
     * @throws RuntimeException if the AutoCloseable#close method is called and throws one
     */
    public static void close(Object that) throws RuntimeException {
        if(isNull(that))
            return;

        if(that instanceof AutoCloseable ac) {
            DuctTape.uncheck(ac::close);
        }
    }

    /**
     * Based on Python's enumerate() function,  which given an iterable that returns A, B, C, ... returns the
     * following pairs (0,A), (1,B), (2,C), ...
     *
     * @param values an input iterable
     * @return an iterable of numbered pairs
     * @param <X> type of the inner iterable
     */
    public static <X> Iterable<Pair<Long,X>> enumerate(Iterable<X> values) {
        return new EnumerateIterable<>(values,0L);
    }

    /**
     * Based on Python's enumerate() function,  which given an iterable that returns A, B, C, ... returns the
     * following pairs (start,A), (start+1,B), (start+2,C), ...
     *
     * @param start beginning value of iterator
     * @param values an input iterable
     * @return an iterable of numbered pairs
     * @param <X> type of the inner iterable
     */

    public static <X> Iterable<Pair<Long,X>> enumerate(Long start, Iterable<X> values) {
        return new EnumerateIterable<>(values,start);
    }

    /**
     * Based on the Python reversed() function.  Stores items in a Deque so potentially these can be
     * deallocated after they are consumed.
     *
     * @param values an iterator
     * @return the same items in reversed order
     * @param <X> type of the output and input
     */
    public static <X> Iterable<X> reversed(Iterable<X> values) {
        return new ReversedIterable<>(values);
    }

    /**
     * Based on the Python zip function
     *
     * @param one first iterable
     * @param two second iterable
     * @return an iterable of pairs taken from the first and second iterable
     * @param <X> type of items from the first iterable
     * @param <Y> type of items from the second iterable
     */
    public static <X,Y> Iterable<Pair<X,Y>> zip(Iterable<X> one,Iterable<Y> two) {

        return new Zip2Iterable<>(one,two);
    }

    /**
     * Based on the Python zip function
     *
     * Note that Python's zip function is capable of combining an arbitrary number of iterables
     * into tuples.  It's not so natural to do this in Java because of the desire for tuples to
     * be typed.
     *
     * @param one first iterable
     * @param two second iterable
     * @param three third iterable
     * @return Trios taken from the three iterables
     * @param <X> type of items from first iterable
     * @param <Y> type of items from second iterable
     * @param <Z> type of items from third iterable
     */
    public static <X,Y,Z> Iterable<Trio<X,Y,Z>> zip(Iterable<X> one, Iterable<Y> two, Iterable<Z> three) {
        return new Zip3Iterable<>(one,two,three);
    }

    /**
     * Based on the cycle function from the Python itertools modules
     *
     * @param values an iterable
     * @return the items in the iterable repeated forever
     * @param <X> the type of items in the input and output iterables
     */
    public static <X> TidyIterable<X> cycle(Iterable<X> values) {
        return new CycleIterable<>(values);
    }

    /**
     * Like the cycle function from Python's itertools but you can specify how
     * many cycles you want
     *
     * @param times number of cycles
     * @param values we draw from these values
     * @return an Iterable that repeats values times times
     * @param <X> arbitrary type
     */

    public static <X> TidyIterable<X> cycle(long times, Iterable<X> values) {
        return new FiniteCycleIterable<>(times,values);
    }

    /**
     * Like the repeat function in Python's itertools
     *
     * @param times number of times to repeat the value
     * @param value a value that will be repeated
     * @return an iterable that repeats the value times times
     * @param <X> type of the value
     */
    public static <X> Iterable<X> repeat(long times, X value) {
        return new RepeatIterable<>(times,value);
    }

    /**
     * Like the accumulate function from Python's itertools.  Like the reduce method in pidove,  this
     * method repeatedly applies a binary operator to values from the iterable,  but instead of returning
     * only the final result, it returns all of the intermediate results
     *
     * @param func a function that accumulates values
     * @param value an iterable
     * @return items derived by applying func cumulatively to the values in the iterable
     * @param <X> type of items in the input and output iterables
     */
    public static <X> Iterable<X> accumulate(BinaryOperator<X> func, Iterable<X> value) {
        return new AccumulateIterable<>(func, value);
    }

    /**
     * Like the compress function from Python's itertools.
     *
     * @param filter Iterable of boolean values
     * @param value Iterable of input values
     * @return Those values from values for which the corresponding value from filter is true
     * @param <X> the type of the input and output interables
     */
    public static <X> Iterable<X> compress(Iterable<Boolean> filter, Iterable<X> value) {
        return map(Pair::right,filter(Pair::left,zip(filter,value)));
    }

    /**
     * Like the filterfalse function from Python's itertools.  Similar to the filter method in pidove
     * except that the sense of matching is reversed
     *
     * @param p a boolean function of X
     * @param values an iterable that yields values of type X
     * @return all values for which p evaluates false
     * @param <X> type of the input and output iterables.
     */
    public static <X> Iterable<X> filterFalse(Predicate<X> p, Iterable<X> values) {
        return filter(p.negate(), values);
    }

    /**
     * Like the indexing operator in Python.
     *
     * Has the advantage that methods with the same name can be used for arrays,  Lists and all Iterables and
     * Collections.  Also allows indexing from the end of the array started by -1
     *
     * @param index index into the array
     * @param values an array
     * @return the index-th element from the beginning (starting from zero) for a positive index and will
     * return the (-index)-th element from the end (starting at -1) for a negative index
     * @param <X> component type of the array
     */
    public static <X> X at(int index, X[] values) {
        if (index>=0)
            return values[index];

        return values[values.length+index];
    }

    /**
     * Like the indexing operator in Python.
     *
     * Has the advantage that methods with the same name can be used for arrays,  Lists and all Iterables and
     * Collections.  Also allows indexing from the end of the array started by -1
     *
     * This function uses optimized algorithms for lists (the get() method) and collections (where we know
     * how what the length of the stucture is)
     *
     * @param index index into the iterable
     * @param values an iterable
     * @return the index-th element from the beginning (starting from zero) for a positive index and will
     * return the (-index)-th element from the end (starting at -1) for a negative index
     * @param <X> type contained by iterable.
     */
    public static <X> X at(int index, Iterable<X> values) {
        try {
            return switch (values) {
                case List<X> l -> index >= 0 ? l.get(index) : l.get(l.size() + index);
                case Iterable<X> x && index >= 0 -> first(skip(index, x)).orElseThrow();
                case Collection<X> x -> atNegativeCollection(index, x);
                default -> atNegative(-index, values);
            };
        } catch(NoSuchElementException x) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private static <X> X atNegativeCollection(int index, Collection<X> x) {
        if(x.size()>index) {
            return first(skip(x.size()-index,x)).orElseThrow();
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private static <X> X atNegative(int index, Iterable<X> x) {
        var i = tail(index, x).iterator();
        try {
            try {
                return i.next();
            } finally {
                for(int j=1;j<index;j++) {
                    i.next();
                }
            }
        } finally {close(i);}
    }

    /**
     * Similar to the len function in Python.
     *
     * Something nice about Python is that you use the same function to get the length of anything that
     * has a length.  Pidove does that for you in Java
     *
     * @param s a CharSequence
     * @return the length of the CharSequence in characters
     */
    public static int len(CharSequence s) {
        return s.length();
    }

    /**
     *
     * @param a an array
     * @return the length of the array
     * @param <T> the component type of the array
     */
    public static <T> int len(T[] a) {
        return a.length;
    }

    /**
     *
     * If the iterable implements collections,  this method uses the size method of the collection,  otherwise
     * it counts the members.
     *
     * @param i an iterable
     * @return the length of the iterable
     * @param <T> the component type of the array
     */
    public static <T> int len(Iterable<T> i) {
        if(i instanceof Collection c) {
            return c.size();
        }
        return (int) count(i);
    }

    /**
     *
     * Note this uses somewhat different algorithms for different cases,  since it is easy
     * to pick the last N elements off the end of a RandomAccess List,  but quite a hassle
     * in comparison in the generic case where we have to keep a running memory of the
     * last N elements we've seen.
     *
     * @param amount number of elements
     * @param x an iterable
     * @return an iterable of the last "amount" elements of the iterable
     * @param <X> type returned by iterable
     */
    public static <X> Iterable<X> tail(int amount, Iterable<X> x) {
        return switch(x) {
            case List<X> l && l instanceof RandomAccess -> tailList(amount,l);
            // not the special case of using ListIterator to read a LinkedList backwards.
            case Collection<X> c -> tailCollection(amount,c);
            default -> tailDefault(amount,x);
        };
    }

    private static<X> TidyIterable<X> tailList(int index,List<X> x) {
        int thatIndex = x.size()-index;
        thatIndex = Math.max(thatIndex, 0);
        return map(i -> x.get(i.intValue()),range(thatIndex,x.size()));
    }

    private static<X> TidyIterable<X> tailCollection(int index,Collection<X> x) {
        int thatIndex = x.size()-index;
        thatIndex = Math.max(thatIndex, 0);
        return skip(thatIndex,x);
    }

    private static<X> Iterable<X> tailDefault(int index,Iterable<X> x) {
        return new PrecaclulatedIterable<>(x, (y) -> {
            final var d = new ArrayDeque<X>();
            for(final var item: x) {
                d.addLast(item);
                if (d.size()>index)
                    d.removeFirst();
            }
            return d.iterator();
        });
    }

    /**
     * Like the pairwise function from Python's itertools.
     *
     * If the input is [a,b,c,d,e,f], the output is [(a,b), (b,c), (c,d), (d,e), (e,f)]
     *
     *
     * @param values an iterable that yields values of type X
     * @return pairs of consequetive values from values
     * @param <X> type of the input iterable
     */
    public static <X> Iterable<Pair<X,X>> pairwise(Iterable<X> values) {
        return new PairwiseIterable<>(values);
    }

    /**
     * Unique to pidove, but taking advantage of Collectors from the Stream API
     *
     * Gathers values yielded by values into overlapping groups of length and passes these through the
     * collector (in the case of N=2 and the toList() collector this has an effect similar to the pairwise() method.)
     *
     * Could be used to compute moving averages and other convolutions
     *
     * @param length length of the window
     * @param collector a Collector function that gets applied to each window derived from the input
     * @param values an iterable that yields type X
     * @return an iterable of type Y values generated by the Collector
     * @param <X> input type
     * @param <Y> output type
     */
    public static <X,Y> Iterable<Y> window(int length, Collector<X,?,Y> collector, Iterable<X> values) {
        return new WindowIterable<>(length, collector, values);
    }

    /**
     * Generates the Cartesian product of two iterators.  If left is [1,2,3] and right is [a,b] the output is
     *
     * [(1,a),(1,b),(2,a),(2,b),(3,a),(3,b)]
     *
     * Similar to the product function in Python's itertools but different in that instead of materializing the
     * input iterables,  it iterates over the iterables repeatedly.  If you want to materialize the iterables
     * turn them into lists.
     *
     * Another difference from Python (similar to the zip function) we don't support an arbitrary number
     * of input iterables because the Java equivalent of a Tuple is typed and thus needs a class declared
     * for every tuple length.
     *
     * @param left first input iterable
     * @param right second input iterable
     * @return an iterable of pairs of the cartesian product of the two inputs
     * @param <X> type of left
     * @param <Y> type of right
     */
    public static <X,Y> Iterable<Pair<X,Y>> product(Iterable<X> left,Iterable<Y> right) {
        return new ProductIterable2<>(left,right);
    }

    /**
     * Generates the Cartesian product of three iterators.
     *
     * @param left first input iterable
     * @param middle second input iterable
     * @param right third input iterable
     * @return an iterable of trios of the cartesian product of the three inputs
     * @param <X> type of left
     * @param <Y> type of middle
     * @param <Z> type of right
     */
    public static <X,Y,Z> Iterable<Trio<X,Y,Z>> product(Iterable<X> left,Iterable<Y> middle,Iterable<Z> right) {
        return new ProductIterable3<>(left,middle,right);
    }

}
