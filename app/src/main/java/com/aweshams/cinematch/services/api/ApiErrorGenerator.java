package com.aweshams.cinematch.services.api;

import okhttp3.Response;

/**
 * Created by irteza on 2018-01-04.
 */

public interface ApiErrorGenerator {

    /**
     * Transforms response JSON into
     *
     * Implementers <b>should not</b> access the {@link Response#body()} method, since the value if
     * available will already be provided via the <c>responseData</c> argument.
     *
     * @param response
     * @return
     */
    Exception generateError(Response response, String responseData, Exception cause);
}
