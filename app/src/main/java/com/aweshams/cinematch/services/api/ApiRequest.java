package com.aweshams.cinematch.services.api;

import android.os.Handler;
import android.os.Looper;

import com.aweshams.cinematch.utils.Deferral;
import com.aweshams.cinematch.utils.Promise;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by irteza on 2018-01-04.
 */

public class ApiRequest {

    public static final String UTF8_ENCODING = "UTF-8";
    protected static String LOG_TAG = "ApiRequest";

    protected final OkHttpClient _client;
    protected Headers _headers;
    protected ApiErrorGenerator _errorGenerator;

    // region constructors

    public ApiRequest(OkHttpClient client, ApiErrorGenerator errorGenerator) {

        this(client, new Headers.Builder()
                .add(HttpConstants.Header.ACCEPT, HttpConstants.ContentType.JSON)
                .add(HttpConstants.Header.CONTENT_TYPE, HttpConstants.ContentType.JSON)
                .add(HttpConstants.Header.SOURCE, HttpConstants.Custom.SOURCE)
                .build(), errorGenerator);
    }

    public ApiRequest(OkHttpClient client, Map<String, String> headers, ApiErrorGenerator errorGenerator) {

        this(client, Headers.of(headers), errorGenerator);
    }

    public ApiRequest(OkHttpClient client, Headers headers, ApiErrorGenerator errorGenerator) {
        _client = client;
        _headers = headers;
        _errorGenerator = errorGenerator;
    }

    // endregion


    // region Service CallDetail Methods
    /**
     * Create a GET request, response can be cached by setting flag on transform
     * @param uri
     * @param params
     * @param transform
     * @param <T>
     * @return
     */

    public <T> Promise<T> get(String uri, Map<String, String> params, ApiTransform<T> transform) {

        return new Promise<>(deferral -> {
            try {

                // build request, adding headers
                final String requestUri = buildUri(uri, params);
                Request request = new Request.Builder()
                        .url(requestUri)
                        .headers(_headers)
                        .build();

                // execute request and attach callback
                execute(request, transform, deferral);
            }

            // forward any exception through the promise
            catch (Exception e) {

                // get exception from generator
                Exception error = _errorGenerator.generateError(null, null, e);

                // reject promise
                deferral.reject(error);
            }
        });
    }

    /**
     * Creates a POST request, response is never cached
     * @param uri
     * @param queryParams
     * @param bodyParams
     * @param transform
     * @param <T>
     * @return
     */
    public <T> Promise<T> post(String uri, Map<String, String> queryParams,
                               Map<String, String> bodyParams, ApiTransform<T> transform) {

        return new Promise<>(deferral -> {
            try {

                // generate request uri
                final String requestUri = buildUri(uri, queryParams);

                // build POST body
                String body = buildQuery(bodyParams);

                // build request, adding headers
                Request request = new Request.Builder()
                        .url(requestUri)
                        .headers(_headers)
                        .post(RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"),
                                body))
                        .build();

                // execute request and attach callback
                execute(request, transform, deferral);
            }

            // forward any exception through the promise
            catch (Exception e) {

                // get exception from generator
                Exception error = _errorGenerator.generateError(null, null, e);

                // reject promise
                deferral.reject(error);
            }
        });
    }

    /**
     * FIXME: remove when merging native login
     * Creates a POST request with an empty body (For BFF)
     * @param uri
     * @param queryParams
     * @param transform
     * @param <T>
     * @return
     */
    public <T> Promise<T> post(String uri, Map<String, String> queryParams,
                               ApiTransform<T> transform) {

        return new Promise<>(deferral -> {
            try {

                // generate request uri
                final String requestUri = buildUri(uri, queryParams);

                // build request, adding headers
                Request request = new Request.Builder()
                        .url(requestUri)
                        .headers(_headers)
                        .post(RequestBody.create(null, new byte[0]))
                        .build();

                // execute request and attach callback
                execute(request, transform, deferral);
            }

            // forward any exception through the promise
            catch (Exception e) {

                // get exception from generator
                Exception error = _errorGenerator.generateError(null, null, e);

                // reject promise
                deferral.reject(error);
            }
        });
    }

    /**
     * Creates a POST request, builds the body as a JSON string
     * @param uri
     * @param bodyParams
     * @param transform
     * @param <T>
     * @return
     */
    public <T> Promise<T> postJson(String uri, HashMap<String, Object> bodyParams,
                                   ApiTransform<T> transform) {
        return postJson(uri, null, bodyParams, transform);
    }

    /**
     * Creates a POST request, builds the body as a JSON string
     * @param uri
     * @param queryParams
     * @param bodyParams
     * @param transform
     * @param <T>
     * @return
     */
    public <T> Promise<T> postJson(String uri, Map<String, String> queryParams,
                                   Map<String, Object> bodyParams, ApiTransform<T> transform) {
        return new Promise<>(deferral -> {
            try {

                // generate request uri
                final String requestUri = buildUri(uri, queryParams);

                // build POST body
                String body = parametersToJson(bodyParams);

                // build request, adding headers
                Request request = new Request.Builder()
                        .url(requestUri)
                        .headers(_headers)
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                body))
                        .build();

                // execute request and attach callback
                execute(request, transform, deferral);
            }

            // forward any exception through the promise
            catch (Exception e) {

                // get exception from generator
                Exception error = _errorGenerator.generateError(null, null, e);

                // reject promise
                deferral.reject(error);
            }
        });
    }

    protected final <T> void execute(final Request request,
                                     final ApiTransform<T> transform,
                                     final Deferral deferral) {

        // push the request onto the http client queue
        // TODO: log something here
        /*_logger.verbose(LOG_TAG, String.format("Making request: %s, with body: %s", request.toString(),
                request.body()));*/
        _client.newCall(request).enqueue(new Callback() {

            // use handler to move resolve / reject back onto main thread
            final Handler _handler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(Call call, IOException e) {
                _handler.post(() -> {

                    // get exception from generator
                    Exception error = _errorGenerator.generateError(null, null, e);

                    // TODO: log something here
                    /*// build log message
                    JsonObject logObj = new JsonObject();
                    logObj.addProperty(CompositeLogService.KEY_METHOD, request.method());
                    logObj.addProperty(CompositeLogService.KEY_URL, request.url().toString());
                    logObj.addProperty(CompositeLogService.KEY_MESSAGE, "failed with exception");

                    String message = new Gson().toJson(logObj);

                    // log error
                    _logger.error(LOG_TAG, message, error);*/

                    // reject promise
                    deferral.reject(error);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                byte[] responseData = null;
                try {

                    // parse response
                    responseData = response.body().bytes(); // throws IOException

                    // reject on failed response
                    final String responseBody = new String(responseData);
                    if (!response.isSuccessful()) {

                        // raise error
                        _handler.post(() -> {

                            // get exception from generator
                            Exception error = _errorGenerator.generateError(response,
                                    responseBody, null);

                            // TODO: log something here
                            // build log message
                            /*JsonObject logObj = new JsonObject();
                            logObj.addProperty(CompositeLogService.KEY_METHOD, request.method());
                            logObj.addProperty(CompositeLogService.KEY_URL, request.url().toString());
                            logObj.addProperty(CompositeLogService.KEY_MESSAGE, "non-200 response");
                            logObj.addProperty(CompositeLogService.KEY_CODE, response.code());
                            logObj.addProperty(CompositeLogService.KEY_RESPONSE, responseBody);

                            String message = new Gson().toJson(logObj);

                            // log error

                            _logger.error(LOG_TAG, message, error);*/

                            // reject promise
                            deferral.reject(error);
                        });

                        // stop processing
                        return;
                    }

                    // transform json
                    T object = transform.transform(responseData);

                    // resolve promise
                    _handler.post(() -> {
                        // TODO: log something here
                        /*_logger.info(LOG_TAG, String.format("%s responded with 200 response " +
                                "with response body: %s", request.toString(), responseBody));*/
                        deferral.resolve(object);
                    });
                }

                // handle all exceptions
                catch (Exception e) {

                    final String responseBody = responseData == null
                            ? null
                            : new String(responseData);
                    _handler.post(() -> {

                        // get exception from generator
                        Exception error = _errorGenerator.generateError(response, responseBody, e);

                        // TODO: log something here
                        // build log message
                        /*JsonObject logObj = new JsonObject();
                        logObj.addProperty(CompositeLogService.KEY_METHOD, request.method());
                        logObj.addProperty(CompositeLogService.KEY_URL, request.url().toString());
                        logObj.addProperty(CompositeLogService.KEY_CODE, response.code());
                        logObj.addProperty(CompositeLogService.KEY_MESSAGE, "failed with exception");
                        logObj.addProperty(CompositeLogService.KEY_RESPONSE, responseBody);

                        String message = new Gson().toJson(logObj);

                        // log error

                        _logger.error(LOG_TAG, message, error);*/

                        // reject promise
                        deferral.reject(error);
                    });

                    // stop processing
                    return;
                }
            }
        });
    }
    // endregion

    // region Helper Methods
    /**
     * Build full URI with parameters added as query parameters
     * @param uri
     * @param parameters
     * @return
     * @throws UnsupportedEncodingException
     */
    protected final String buildUri(String uri, Map<String, String> parameters)
            throws UnsupportedEncodingException {

        // build query string
        String query = buildQuery(parameters);

        // return appended query, or just uri if there is no query
        return query == null
                ? uri
                : uri + '?' + query;
    }

    /**
     * Builds query parameters
     * @param parameters
     * @return
     * @throws UnsupportedEncodingException
     */
    protected final String buildQuery(Map<String, String> parameters)
            throws UnsupportedEncodingException {

        // return uri if there are no parameters
        if (parameters == null
                || parameters.isEmpty()) {
            return null;
        }

        // build query
        final StringBuilder queryBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {

            // append separator (if required)
            if (!first) {
                queryBuilder.append('&');
            }

            // or clear first flag
            else {
                first = false;
            }

            // append parameter name
            queryBuilder.append(URLEncoder.encode(entry.getKey(), UTF8_ENCODING));

            // append value (if any)
            final String value = entry.getValue();
            if (value != null
                    && !value.isEmpty()) {
                queryBuilder.append('=');
                queryBuilder.append(URLEncoder.encode(value, UTF8_ENCODING));
            }
        }

        // generate query
        return queryBuilder.toString();
    }

    /**
     * Generates a unique request id to be used as a key for caching
     * @param method
     * @param uri
     * @return
     */
    protected String generateRequestId(String method, String uri) {

        return method + '/' + uri;
    }

    /**
     * Converts parameters into json for POST calls
     * @param parameters
     * @return
     */
    protected String parametersToJson(Map<String, Object> parameters) {

        JsonObject json = new JsonObject();
        Gson gson = new Gson();

        // build json object
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {

            json.add(entry.getKey(), gson.toJsonTree(entry.getValue()));
        }

        // return json
        return json.toString();
    }
    // endregion
}
