package com.aweshams.cinematch.utils;

/**
 * Created by irteza on 2018-01-04.
 *
 * @param <TResult> the type parameter
 */
public abstract class ErrorPromise<TResult> extends Promise<TResult> {

    // region package private methods

    /**
     * Execute.
     *
     * @param rejection the rejection
     */
    protected abstract void execute(Rejection rejection);

    // endregion
}
