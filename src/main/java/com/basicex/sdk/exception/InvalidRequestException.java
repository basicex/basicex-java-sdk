package com.basicex.sdk.exception;

public class InvalidRequestException extends BasicexException {
    private final String param;

    public InvalidRequestException(String message, String param, String requestId, String code, Integer statusCode, Throwable e) {
        super(message, requestId, code, statusCode, e);
        this.param = param;
    }
}
