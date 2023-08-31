/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.exception;

import com.basicex.sdk.model.BasicexError;
import com.basicex.sdk.util.StringUtils;

/**
 * BasicexException
 */
public class BasicexException extends Exception {
    private transient BasicexError basicexError;
    /**
     * Returns the error code of the response that triggered this exception
     *
     * @return the string representation of the error code.
     */
    private String code;

    /**
     * Returns the request ID of the request that triggered this exception.
     *
     * @return the request ID.
     */
    private String requestId;

    /**
     * Returns the status code of the response that triggered this exception.
     *
     * @return the status code.
     */
    private Integer statusCode;

    /**
     * Returns the message of the response that triggered this exception.
     */
    private String message;

    protected BasicexException(String message, String requestId, String code, Integer statusCode) {
        this(message, requestId, code, statusCode, null);
    }

    /** Constructs a new BasicEx exception with the specified details. */
    protected BasicexException(
            String message, String requestId, String code, Integer statusCode, Throwable e) {
        super(message, e);
        this.code = code;
        this.requestId = requestId;
        this.statusCode = statusCode;
    }

    /**
     * Returns a description of the exception, including the HTTP status code and request ID (if
     * applicable).
     *
     * @return a string representation of the exception.
     */
    @Override
    public String getMessage() {
        String additionalInfo = "";
        if (code != null) {
            additionalInfo += "; code: " + code;
        }
        if (basicexError != null && StringUtils.isNotEmpty(basicexError.getParam())) {
            additionalInfo += "; param: " + basicexError.getParam();
        }
        if (requestId != null) {
            additionalInfo += "; request-id: " + requestId;
        }
        return super.getMessage() + additionalInfo;
    }

    /**
     * Returns a description of the user facing exception
     *
     * @return a string representation of the user facing exception.
     */
    public String getUserMessage() {
        return super.getMessage();
    }

    public void setBasicexError(BasicexError basicexError) {
        this.basicexError = basicexError;
    }

    public BasicexError getBasicexError() {
        return this.basicexError;
    }
}
