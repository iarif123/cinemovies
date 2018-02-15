package com.aweshams.cinematch.utils;

/**
 * Created by irteza on 2018-01-04.
 */
public interface ErrorBlock {
    /**
     * Execute.
     *
     * @param error the error
     * @throws Exception the exception
     */
    void execute(Exception error) throws Exception;
}
