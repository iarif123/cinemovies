package com.aweshams.cinematch.utils.promises;

/**
 * Created by irteza on 2018-01-04.
 *
 * @param <TResult> the type parameter
 */
public final class Deferral<TResult> {

    // region instance variables

    private final Promise<TResult> _promise;

    // endregion


    // region constructors

    /**
     * Instantiates a new Deferral.
     *
     * @param promise the promise
     */
    Deferral(Promise<TResult> promise) {
        _promise = promise;
    }

    // endregion


    // region actions

    /**
     * Resolve.
     *
     * @param result Value to resolve the {@link Promise} with
     */
    public void resolve(TResult result) {
        _promise.onResolve(result);
    }

    /**
     * Reject.
     *
     * @param error Error to reject the {@link Promise} with
     */
    public void reject(Exception error) {
        _promise.onReject(new Rejection(error));
    }

    // endregion
}

