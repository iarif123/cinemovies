package com.aweshams.cinematch.services.data;

/**
 * Created by irteza on 2018-01-23.
 */

import com.aweshams.cinematch.services.api.TransformException;

/**
 * Provides data deserialization services for a specific type.
 */
public interface ITransform<T> {

    T transform(byte[] data) throws TransformException;
}