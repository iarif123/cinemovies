package com.aweshams.cinematch.services.util;

/**
 * Created by irteza on 2018-05-24.
 */

import android.os.AsyncTask;

import com.aweshams.cinematch.utils.promises.Deferral;
import com.aweshams.cinematch.utils.promises.Promise;

/**
 * A helper class providing promise-like functionality around the standard {@link AsyncTask}.
 */
public class AsyncPromise<TInput, TOutput> {

    // region instance variables

    private final ThrowableFunction<TInput, TOutput> _function;

    // endregion


    // region constructors

    private AsyncPromise(ThrowableFunction<TInput, TOutput> function) {
        _function = function;
    }

    // endregion


    // region factory methods

    public static <U, V> AsyncPromise<U, V> executeAsync(ThrowableFunction<U, V> function) {
        return new AsyncPromise<>(function);
    }

    // endregion


    // region public methods

    public Promise<TOutput> on(TInput input) {
        return new Promise<>(deferral -> new AsyncOperation(deferral).execute(input));
    }

    // endregion


    // region AsyncTask

    class AsyncOperation extends AsyncTask<TInput, Void, TOutput> {

        private final Deferral _deferral;
        private Exception _error;

        AsyncOperation(Deferral deferral) {
            _deferral = deferral;
        }

        @Override
        protected TOutput doInBackground(TInput... params) {
            try {
                return _function.execute(params[0]);
            } catch (Exception e) {

                // log exception
                e.printStackTrace();

                // capture exception
                _error = e;

                // return null
                return null;
            }
        }

        @Override
        protected void onPostExecute(TOutput output) {

            // reject on exception
            if (_error != null) {
                _deferral.reject(_error);
            }

            // or resolve
            else {
                _deferral.resolve(output);
            }
        }
    }

    // endregion
}
