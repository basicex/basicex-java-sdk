package com.basicex.sdk.net;

import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.BasicexObject;

import java.lang.reflect.Type;
import java.io.InputStream;

public interface BasicexResponseGetter {
    <T extends BasicexObject> T request(
            ApiResource.RequestMethod method,
            String path,
            Object params,
            Type typeToken,
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
