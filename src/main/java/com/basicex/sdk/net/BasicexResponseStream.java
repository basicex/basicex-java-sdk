package com.basicex.sdk.net;

import com.basicex.sdk.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BasicexResponseStream extends AbstractBasicexResponse<InputStream> {

    /**
     * Initializes a new instance of the {@link BasicexResponseStream} class.
     *
     * @param code the HTTP status code of the response
     * @param headers the HTTP headers of the response
     * @param body streaming body response
     * @throws NullPointerException if {@code headers} or {@code body} is {@code null}
     */
    public BasicexResponseStream(int code, HttpHeaders headers, InputStream body) {
        super(code, headers, body);
    }

    /**
     * Buffers the entire response body into a string, constructing the appropriate BasicexResponse
     *
     * @return the StripeResponse
     */
    BasicexResponse unstream() throws IOException {
        final String bodyString = StreamUtils.readToEnd(this.getBody(), StandardCharsets.UTF_8);
        this.getBody().close();
        return new BasicexResponse(this.getCode(), this.getHeaders(), bodyString);
    }
}
