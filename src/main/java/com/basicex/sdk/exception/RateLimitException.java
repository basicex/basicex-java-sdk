package com.basicex.sdk.exception;

public class RateLimitException extends BasicexException {
    public RateLimitException(String message, String requestId, String code, Integer statusCode, Throwable throwable) {
        super(message, requestId, code, statusCode, throwable);
    }
}
