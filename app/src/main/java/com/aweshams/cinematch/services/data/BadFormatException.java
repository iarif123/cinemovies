package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import com.aweshams.cinematch.services.api.TransformException;

/**
 * A value in transform could not be parsed correctly.
 */
public class BadFormatException extends TransformException {
    public BadFormatException() {
    }

    public BadFormatException(String message) {
        super(message);
    }

    public BadFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadFormatException(Throwable cause) {
        super(cause);
    }
}