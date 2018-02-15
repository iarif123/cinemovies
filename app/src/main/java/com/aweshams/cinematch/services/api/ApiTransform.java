package com.aweshams.cinematch.services.api;

/**
 * Created by irteza on 2018-01-04.
 */

public abstract class ApiTransform<T> {

    public ApiTransform() {
    }

    // Abstract Methods
    public abstract T transform(byte[] data) throws TransformException;

}
