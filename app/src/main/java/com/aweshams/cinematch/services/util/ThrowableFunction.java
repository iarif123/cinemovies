package com.aweshams.cinematch.services.util;

/**
 * Created by irteza on 2018-05-24.
 */

/**
 * Implemented by expressions providing generic functions that can throw.
 */
public interface ThrowableFunction<TInput, TOutput> {
    TOutput execute(TInput value) throws Exception;
}
