package com.basicex.sdk.exception;

public class SignatureException extends BasicexException {

    public SignatureException(String message, String requestId, String code, Integer statusCode) {
        super(message, requestId, code, statusCode);
    }

    protected SignatureException(String message, String requestId, String code, Integer statusCode, Throwable e) {
        super(message, requestId, code, statusCode, e);
    }

    public SignatureException(String message, Throwable e) {
        super(message, null, null, null, e);
    }
}
