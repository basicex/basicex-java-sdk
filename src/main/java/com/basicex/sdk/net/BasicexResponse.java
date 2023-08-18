package com.basicex.sdk.net;


/**
 * A response from Basicex's API, with body represented as a String.
 */
public class BasicexResponse extends AbstractBasicexResponse<String> {
    /**
     * Initializes a new instance of the {@link BasicexResponse} class.
     *
     * @param code the HTTP status code of the response
     * @param headers the HTTP headers of the response
     * @param body the body of the response
     * @throws NullPointerException if {@code headers} or {@code body} is {@code null}
     */
    public BasicexResponse(int code, HttpHeaders headers, String body) {
        super(code, headers, body);
    }
}
