/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.net;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * Common interface representing an HTTP response from BasicEx
 */

public abstract class AbstractBasicexResponse<T> {
    /** The HTTP status code of the response. */
    private final int code;

    /** The HTTP headers of the response. */
    private final HttpHeaders headers;

    /** The body of the response. */
    private final T body;

    private int numRetries;

    public final int getCode() {
        return this.code;
    }

    public final HttpHeaders getHeaders() {
        return this.headers;
    }

    public final T getBody() {
        return this.body;
    }

    /**
     * Gets the date of the request, as returned by BasicEx.
     *
     * @return the date of the request, as returned by BasicEx
     */
    public Instant getDate() {
        Optional<String> dateStr = this.headers.firstValue("Date");
        if (!dateStr.isPresent()) {
            return null;
        }
        return ZonedDateTime.parse(dateStr.get(), DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
    }

    protected AbstractBasicexResponse(int code, HttpHeaders headers, T body) {
        Objects.requireNonNull(headers);
        Objects.requireNonNull(body);

        this.code = code;
        this.headers = headers;
        this.body = body;
    }

    public int getNumRetries() {
        return numRetries;
    }

    public void setNumRetries(int numRetries) {
        this.numRetries = numRetries;
    }

    /**
     * Gets the ID of the request, as returned by BasicEx.
     *
     * @return the ID of the request, as returned by BasicEx
     */
    public String getRequestId() {
        return this.headers.firstValue("Request-Id").orElse(null);
    }
}
