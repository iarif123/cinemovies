package com.aweshams.cinematch.utils;

/**
 * Created by irteza on 2018-05-24.
 */

/**
 * Creates a strong reference around an existing value, typically for helping share a value inside
 * and outside of a closure.
 */
public class StrongReference<T> {

    public T value;

    /**
     * Constructs a strong reference to a value that defaults to <c>null</c>.
     */
    public StrongReference() {
        this(null);
    }

    /**
     * Constructs a boxed value using the initial value to start.
     * @param initial The initial value to box.
     */
    public StrongReference(T initial) {
        value = initial;
    }
}
