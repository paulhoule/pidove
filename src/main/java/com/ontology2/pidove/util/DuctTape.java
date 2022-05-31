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
     * Executes a method that could throw an Exception but wraps any non-RuntimeExceptions
     * in RuntimeExceptions.  If the exception is an InterruptedException it also interrupts the
     * thread.
     *
     * @param that a void method that might throw an exception
     */
    public static void uncheck(ExceptionalRunnable that) {
        try {
            that.run();
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch(Exception e) {
            if(e instanceof RuntimeException rt) {
                throw rt;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Executes a method that could throw an Exception but wraps any non-RuntimeExceptions
     * in RuntimeExceptions.  If the exception is an InterruptedException it also interrupts the
     * thread.
     *
     * @param that a non-void method that might throw an exception
     */
    public static <X> X uncheck(ExceptionalSupplier<X> that) {
        try {
            return that.get();
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch(Exception e) {
            if(e instanceof RuntimeException rt) {
                throw rt;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Converts a method that could throw a non-runtime Exception into one that doesn't,
     * using the method in the uncheck method.
     *
     * (It would probably be worthwhile to create methods with signatures such as Function since these would be
     * useful to pass into other methods in pidove)
     *
     * @param that a Runnable
     * @return nothing
     */
    public static Runnable unchecked(ExceptionalRunnable that) {
        return () -> uncheck(that);
    }

    public static <X> Supplier<X> unchecked(ExceptionalSupplier<X> that) {
        return () -> uncheck(that);
    }

    /**
     * The following method tricks out the type inference system so you can write
     *
     * var fma = lambda((Integer a, Integer b, Integer c) -> a*b+c);
     *
     * that is,  use var to save the a lambda function while defining the input types of
     * the lambda function explictly in the definition
     *
     * @param x a function
     * @return a Function
     * @param <A> input type
     * @param <B> output type
     */

    public static <A,B> Function<A,B> lambda(Function<A,B> x) { return x; }

    public static <A,B,C> BiFunction<A,B,C> lambda(BiFunction<A,B,C> x) { return x; }

    public static <A,B,C,D> Function3<A,B,C,D> lambda(Function3<A,B,C,D> x) { return x; }

    public static <A> Consumer<A> lambda(Consumer<A> x) { return x; }

    public static <A,B> BiConsumer<A,B> lambda(BiConsumer<A,B> x) { return x; }

    public static <A,B,C> Consumer3<A,B,C> lambda(Consumer3<A,B,C> x) { return x; }

}
