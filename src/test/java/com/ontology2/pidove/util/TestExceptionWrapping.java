package com.ontology2.pidove.util;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.NoSuchElementException;

import static com.ontology2.pidove.util.DuctTape.uncheck;
import static org.junit.jupiter.api.Assertions.*;

public class TestExceptionWrapping {
    public static final boolean True=true;
    @Test
    public void areYouOverwhelmedByTheHype() {
        Runnable that = () -> uncheck(() -> {throw new NoSuchElementException();});
        assertThrows(NoSuchElementException.class, that::run);
    }

    @Test
    public void doesTheModernWorldHaveYouDown() {
        Runnable that = DuctTape.unchecked(() -> {
            if(True) throw new FontFormatException("andrew");
        });
        assertThrows(RuntimeException.class, that::run);
        assertFalse(Thread.currentThread().isInterrupted());
    }

    @Test
    public void scalingYourWalls() {
        Runnable that = DuctTape.unchecked(() -> {
            if(True) throw new InterruptedException();
        });
        assertThrows(RuntimeException.class, that::run);
        assertTrue(Thread.currentThread().isInterrupted());
    }

    @Test
    public void treasuresOnlyWeighYouDown() {
        Runnable that = DuctTape.unchecked(() -> {});
        that.run();
        assertFalse(Thread.currentThread().isInterrupted());
    }
}
