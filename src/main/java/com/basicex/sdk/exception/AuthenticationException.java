package com.basicex.sdk.exception;

public class AuthenticationException extends BasicexException {
    public AuthenticationException(String message, String requestId, String code, Integer statusCode) {
        super(message, requestId, code, statusCode);
    }

    public AuthenticationException(String message, String requestId, String code, Integer statusCode, Throwable e) {
        super(message, requestId, code, statusCode, e);
    }
}
