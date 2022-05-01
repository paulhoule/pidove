package com.ontology2.pidove.util;

import java.util.function.*;

/**
 * DuctTape methods address disagreements some people could have with Java.
 *
 * Maybe the most controversial feature of Java is checked exceptions.
 *
 * Sometimes an Exception happens inside a method which isn't allowed to throw an
 * Exception.  What do you do?  It's not impossible an Exception could be thrown in an
 * class that implements (say) Runnable#run because RuntimeExceptions are common in Java.
 *
 * The runtimize methods execute a functional interface that throws an Exception that
 * convert the Exception into a RuntimeException,  undoing some of the damage that
 * checked exceptions do to Java.
 *
 * Sometimes people find it annoying that Java enforces the types of data values at
 * compile time.  You can always cast a value to a specific type with (Type) but
 * doing that require that you know and specify the type.  The cast function,  as
 * in
 *
 * SpecificObject x = cast(anyObject);
 *
 * automatically determines the type.  You might find that the lambda() function is
 * more responsible.  Unfortunately you aren't allowed to use var with lambda definitions,
 * instead you have to write
 *
 * Function&lt;String,Integer&gt; x = s -> s.length();
 *
 * The lambda function tricks the compiler into choosing a specific functional interface,
 * which lets you write
 *
 * var x = lambda((String) s -> s.length());
 *
 * which is more concise.
 */

public class DuctTape {

    @SuppressWarnings("unchecked")
    public static <X> X cast(Object o) {
        return (X) o;
    }

    /**
     * Wraps a method that could throw an Exception such that non-RuntimeExceptions are wrapped
     * in RuntimeExceptions
     *
     * @param that a method that might throw an exception
     */
    public static void uncheck(ExceptionalRunnable that) {
        try {
            that.run();
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch(Exception e) {
            if(e instanceof RuntimeException rt) {
                throw rt;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public static <X> X uncheck(ExceptionalSupplier<X> that) {
        try {
            return that.get();
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch(Exception e) {
            if(e instanceof RuntimeException rt) {
                throw rt;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public static Runnable unchecked(ExceptionalRunnable that) {
        return () -> uncheck(that);
    }

    public static <X> Supplier<X> unchecked(ExceptionalSupplier<X> that) {
        return () -> uncheck(that);
    }

    public static <A,B> Function<A,B> lambda(Function<A,B> x) { return x; }

    public static <A,B,C> BiFunction<A,B,C> lambda(BiFunction<A,B,C> x) { return x; }

    public static <A,B,C,D> Function3<A,B,C,D> lambda(Function3<A,B,C,D> x) { return x; }

    public static <A> Consumer<A> lambda(Consumer<A> x) { return x; }

    public static <A,B> BiConsumer<A,B> lambda(BiConsumer<A,B> x) { return x; }

    public static <A,B,C> Consumer3<A,B,C> lambda(Consumer3<A,B,C> x) { return x; }

    @FunctionalInterface
    public interface ExceptionalRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface ExceptionalSupplier<X> {
        X get() throws Exception;
    }
}
