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
     * Gets the date of the request, as returned by Stripe.
     *
     * @return the date of the request, as returned by Stripe
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
