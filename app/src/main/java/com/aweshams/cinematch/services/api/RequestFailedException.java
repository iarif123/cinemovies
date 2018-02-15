package com.aweshams.cinematch.services.api;

/**
 * Created by irteza on 2018-01-04.
 */

public class RequestFailedException extends AvailabilityException {
    public RequestFailedException() {
        super();
    }

    public RequestFailedException(String message) {
        super(message);
    }

    public RequestFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestFailedException(Throwable cause) {
        super(cause);
    }
}