package com.ontology2.pidove.util;

import java.util.function.Supplier;

/**
 * Sometimes an Exception happens inside a method which isn't allowed to throw an
 * Exception.  What do you do?  It's not impossible an Exception could be thrown in an
 * class that implements (say) Runnable#run because RuntimeExceptions are common in Java.
 *
 * The runtimize methods execute a functional interface that throws an Exception that
 * convert the Exception into a RuntimeException,  undoing some of the damage that
 * checked exceptions do to Java
 *
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

    @FunctionalInterface
    public interface ExceptionalRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface ExceptionalSupplier<X> {
        X get() throws Exception;
    }
}
