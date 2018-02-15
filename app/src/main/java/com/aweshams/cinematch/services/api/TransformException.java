package com.aweshams.cinematch.services.api;

/**
 * Created by irteza on 2018-01-04.
 */

public class TransformException extends Exception {

    public TransformException() {
    }

    public TransformException(String message) {
        super(message);
    }

    public TransformException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformException(Throwable cause) {
        super(cause);
    }
}
