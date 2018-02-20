package com.aweshams.cinematch.utils.promises;

/**
 * Created by irteza on 2018-01-04.
 *
 * @param <T> the type parameter
 */
public interface ThenBlock<T> {
    /**
     * Execute object.
     *
     * @param value the value
     * @return the object
     * @throws Exception the exception
     */
    Object execute(T value) throws Exception;
}