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

import com.basicex.sdk.BasicExConfig;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.BasicexObject;

import java.lang.reflect.Type;
import java.io.InputStream;

public interface BasicexResponseGetter {
    BasicExConfig getConfig();

    <T> T request(
            ApiResource.RequestMethod method,
            String path,
            Object params,
            TypeReference<T> typeToken,
            Boolean signRequest,
            RequestOptions options)
            throws BasicexException;

    InputStream requestStream(
            ApiResource.RequestMethod method,
            String path,
            Object params,
            Boolean signRequest,
            RequestOptions options)
            throws BasicexException;

    /**
     * This method should e.g. throws an ApiKeyMissingError if a proper API Key cannot be determined
     * by the ResponseGetter or from the RequestOptions passed in.
     */
    default void validateRequestOptions(RequestOptions options) {}
}
