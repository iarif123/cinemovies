package com.aweshams.cinematch.services.data;

import com.aweshams.cinematch.services.api.TransformException;

/**
 * Created by irteza on 2018-01-23.
 */

public class ValidationException extends TransformException {
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
