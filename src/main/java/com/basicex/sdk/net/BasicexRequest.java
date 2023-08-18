package com.basicex.sdk.net;

import java.net.URL;
import java.util.Map;

/**
 * A request to BasicEx's API.
 */
public class BasicexRequest {
    /**
     * The HTTP method for the request (GET, POST or DELETE).
     */
    private ApiResource.RequestMethod method;
    /**
     * The URL for the request. If this is a GET or DELETE request, the URL also includes the request
     * parameters in its query string.
     */
    private URL url;

    /**
     * The body of the request. For POST requests, this will be either a {@code
     * application/x-www-form-urlencoded} or a {@code multipart/form-data} payload. For non-POST
     * requests, this will be {@code null}.
     */
    private HttpContent content;

    /**
     * The HTTP headers of the request
     */
    private HttpHeaders headers;

    /** The parameters of the request (as an unmodifiable map). */
    private Map<String, Object> params;

    /** The special modifiers of the request. */
    private RequestOptions options;

    public RequestOptions getOptions() {
        return options;
    }

    public URL getUrl() {
        return url;
    }

    public ApiResource.RequestMethod getMethod() {
        return method;
    }

    public HttpContent getContent() {
        return content;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
