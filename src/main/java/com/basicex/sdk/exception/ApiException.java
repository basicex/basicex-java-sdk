package com.basicex.sdk.exception;

public class ApiException extends BasicexException {
    public ApiException(String message, String requestId, String code, Integer statusCode, Throwable throwable) {
        super(message, requestId, code, statusCode, throwable);
    }
}
