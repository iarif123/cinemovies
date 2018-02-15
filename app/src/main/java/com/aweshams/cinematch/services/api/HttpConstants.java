package com.aweshams.cinematch.services.api;

/**
 * Created by irteza on 2018-01-04.
 */

public class HttpConstants {
    private HttpConstants() {
    }

    /** HTTP request methods. */
    public static class Method {

        /** A HTTP GET request. */
        public static final String GET = "GET";

        /** A HTTP POST request. */
        public static final String POST = "POST";

        /** A HTTP DELETE request. */
        public static final String DELETE = "DELETE";
    }

    public static class Header {

        /** The Authorization header key for HTTP request. */
        public static final String AUTHORIZATION = "Authorization";

        /** The Content-Type header key for HTTP request. */
        public static final String CONTENT_TYPE = "Content-Type";

        /** The Accept header key for HTTP request. */
        public static final String ACCEPT = "Accept";

        /** Custom source header key to distinguish calls from Android v/s iOS in the BFF */
        public static final String SOURCE = "Source";
    }

    public static class Custom {
        /** Custom source header value to distinguish calls from Android v/s iOS in the BFF */
        public static final String SOURCE = "myaccountapp-android";
    }

    public static class Authorization {

        /** The prefix for a basic authorization request header, including the leading space. */
        public static final String BASIC_PREFIX = "Basic ";

        /** The prefix for a bearer authorization request header, including the leading space. */
        public static final String BEARER_PREFIX = "Bearer ";

        /** The OAuth2 grant type request parameter. **/
        public static final String GRANT_TYPE_PARAM = "grant_type";
    }

    public static class ContentType {

        /** A content type for url-encoded form data */
        public static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded;charset=UTF-8";

        /** A content type for url-encoded form data */
        public static final String JSON = "application/json";
    }

    public static class TokenType {

        /** Key for retrieving refresh token from OAuth server */
        public static final String REFRESH_TOKEN = "refresh_token";

        /** Key for retrieving access token from OAuth server */
        public static final String ACCESS_TOKEN = "access_token";

        /** Key for retrieving refresh token expiry from OAuth server */
        public static final String EXPIRES_IN = "expires_in";
    }
}
