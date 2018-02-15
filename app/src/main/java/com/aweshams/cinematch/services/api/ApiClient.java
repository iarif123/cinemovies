package com.aweshams.cinematch.services.api;

import android.support.annotation.NonNull;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Base API client that should be extended as a data access layer for remote APIs.
 */
public class ApiClient implements ApiErrorGenerator {

    // region instance variables

    protected final OkHttpClient _client;

    // endregion


    // region constructors

    public ApiClient(@NonNull OkHttpClient client) {
        _client = client;
    }

    // endregion


    // region instance methods

    public ApiRequest createRequest() {
        return new ApiRequest(_client, this);
    }

    public ApiRequest createRequest(Map<String, String> header) {
        return new ApiRequest(_client, header, this);
    }

    public void reset() {
    }

    protected <T extends Enum<T>> String enumToParameterValue(T enumeration) {
        return enumeration.name().replace("_", "-");
    }

    @Override
    public Exception generateError(Response response, String responseData, Exception cause) {

        // assume the request failed if there is no response
        final Exception exception;
        if (response == null) {
            exception = cause == null
                    ? new RequestFailedException()
                    : new RequestFailedException(cause);
        }

        // check if we threw a custom exception
        else if (cause != null) {
            exception = cause;
        }

        // otherwise, assume transform failed
        else {
            exception = new TransformException();
        }

        // return exception
        return exception;
    }

    // endregion

}
