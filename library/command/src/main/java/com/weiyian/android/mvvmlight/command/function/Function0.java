package com.weiyian.android.mvvmlight.command.function;

/**
 * A functional interface that takes a value and returns another value, possibly with a
 * different type and allows throwing a checked exception.
 *
 * @param <R> the output value type
 */
public interface Function0<R> {
    
    R apply() throws Exception;
}
