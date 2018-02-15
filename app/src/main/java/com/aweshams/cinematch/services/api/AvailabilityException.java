package com.aweshams.cinematch.services.api;

/**
 * Created by irteza on 2018-01-04.
 */

public class AvailabilityException extends Exception {

    public AvailabilityException() {
        super();
    }

    public AvailabilityException(String message) {
        super(message);
    }

    public AvailabilityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvailabilityException(Throwable cause) {
        super(cause);
    }
}
