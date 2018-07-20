package com.aweshams.cinematch.utils;

/**
 * Created by irteza on 2018-05-24.
 */

/**
 * A <em>checked exception</em> extending the {@link Exception} class
 * <p>
 * Allows for preservation of other errors thrown by ensuring that an application calling a method
 * that throws this exception will not have to catch a {@link NullPointerException}.
 * </p>
 * This exception is thrown when a nullable reference has become {@code null} and a strong reference
 * is being requested.
 */
public class ReferenceNullException extends Exception {
    /**
     * Constructs a {@code ReferenceNullException} with no detail message.
     */
    public ReferenceNullException() {
        super();
    }

    /**
     * Constructs a {@code ReferenceNullException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ReferenceNullException(String s) {
        super(s);
    }
}