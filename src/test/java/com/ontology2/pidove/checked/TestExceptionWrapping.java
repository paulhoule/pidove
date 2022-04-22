package com.ontology2.pidove.checked;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.NoSuchElementException;

import static com.ontology2.pidove.checked.Exceptions.uncheck;
import static com.ontology2.pidove.checked.Exceptions.unchecked;
import static org.junit.jupiter.api.Assertions.*;

public class TestExceptionWrapping {
    @Test
    public void areYouOverwhelmedByTheHype() {
        Runnable that = () -> uncheck(() -> {throw new NoSuchElementException();});
        assertThrows(NoSuchElementException.class, that::run);
    }

    @Test
    public void doesTheModernWorldHaveYouDown() {
        Runnable that = unchecked(() -> {throw new FontFormatException("andrew");});
        assertThrows(RuntimeException.class, that::run);
        assertFalse(Thread.currentThread().isInterrupted());
    }

    @Test
    public void scalingYourWalls() {
        Runnable that = unchecked(() -> {throw new InterruptedException();});
        that.run();
        assertTrue(Thread.currentThread().isInterrupted());
    }

    @Test
    public void treasuresOnlyWeighYouDown() {
        Runnable that = unchecked(() -> {});
        that.run();
        assertFalse(Thread.currentThread().isInterrupted());
    }
}
