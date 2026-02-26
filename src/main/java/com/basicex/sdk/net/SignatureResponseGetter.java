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
import com.basicex.sdk.exception.*;
import com.basicex.sdk.model.BasicexError;
import com.basicex.sdk.util.HmacUtils;
import com.basicex.sdk.util.PrivateKeyUtils;
import com.basicex.sdk.util.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class SignatureResponseGetter implements BasicexResponseGetter {
    private final HttpClient httpClient;
    private final BasicExConfig config;

    public SignatureResponseGetter(HttpClient httpClient, BasicExConfig config) {
        this.httpClient = httpClient != null ? httpClient : buildDefaultHttpClient();
        this.config = config;
    }

    public SignatureResponseGetter(BasicExConfig config) {
        this(null, config);
    }


    @Override
    public BasicExConfig getConfig() {
        return this.config;
    }

    @Override
    public <T> T request(ApiResource.RequestMethod method, String path, Object params, TypeReference<T> typeToken, Boolean signRequest, RequestOptions options) throws BasicexException {

        String fullUrl = String.format("%s%s", Optional.ofNullable(options).map(x -> Optional.ofNullable(x.getApiBaseUrl()).orElse(config.getApiBaseUrl())).orElse(config.getApiBaseUrl()), path);
        BasicexRequest request = new BasicexRequest(method, fullUrl, params, RequestOptions.merge(options, config));
        if (signRequest) {
            String signature = signature(params, path, request.getOptions());
            request.setHeaders(request.getHeaders().withAdditionalHeader("X-Signature", signature));
            
            if (StringUtils.isNotBlank(request.getOptions().getApiKey())) {
                request.setHeaders(request.getHeaders().withAdditionalHeader("X-Api-Key", request.getOptions().getApiKey()));
            }
        }
        BasicexResponse response = httpClient.requestWithRetries(request);

        int responseCode = response.getCode();
        String responseBody = response.getBody();
        String requestId = response.getRequestId();

        if (responseCode < 200 || responseCode >= 300) {
            handleError(response);
        }

        T resource = null;
        try {
            if(typeToken.getType() instanceof ParameterizedType && ((ParameterizedType) typeToken.getType()).getRawType().getTypeName().equals("java.util.List")) {
                JsonArray jsonArray = ApiResource.GSON.fromJson(response.getBody(), JsonObject.class).getAsJsonArray("data");
                if(jsonArray != null) {
                    resource = ApiResource.GSON.fromJson(jsonArray, typeToken.getType());
                }
            } else {
                JsonObject jsonObject =
                        ApiResource.GSON.fromJson(response.getBody(), JsonObject.class).getAsJsonObject("data");
                if (jsonObject != null) {
                    resource = ApiResource.GSON.fromJson(jsonObject, typeToken.getType());
                }
            }
        } catch (JsonSyntaxException e) {
            raiseMalformedJsonError(responseBody, responseCode, requestId, e);
        }

        return resource;
    }

    @Override
    public InputStream requestStream(ApiResource.RequestMethod method, String path, Object params, Boolean signRequest, RequestOptions options) throws BasicexException {
        String fullUrl = String.format("%s%s", Optional.ofNullable(options).map(x -> Optional.ofNullable(x.getApiBaseUrl()).orElse(config.getApiBaseUrl())).orElse(config.getApiBaseUrl()), path);
        BasicexRequest request = new BasicexRequest(method, fullUrl, params, RequestOptions.merge(options, config));
        if (signRequest) {
            String signature = signature(params, path, request.getOptions());
            request.setHeaders(request.getHeaders().withAdditionalHeader("X-Signature", signature));
            
            if (StringUtils.isNotBlank(request.getOptions().getApiKey())) {
                request.setHeaders(request.getHeaders().withAdditionalHeader("X-Api-Key", request.getOptions().getApiKey()));
            }
        }
        BasicexResponseStream responseStream = httpClient.requestStreamWithRetries(request);

        int responseCode = responseStream.getCode();

        if (responseCode < 200 || responseCode >= 300) {
            BasicexResponse response;
            try {
                response = responseStream.unstream();
            } catch (IOException e) {
                throw new ApiConnectionException(
                        String.format(
                                "IOException during API request to BasicEx (%s): %s ",
                                request.getOptions().getApiBaseUrl(), e.getMessage()),
                        e);
            }
            handleError(response);
        }

        return responseStream.getBody();
    }

    private String signature(Object params, String path, RequestOptions options) throws SignatureException {
        String signStr = Optional.ofNullable(params).map(ApiResource.GSON::toJson).orElse(null);
        String signInput = options.getApiBaseUrl() + path + Optional.ofNullable(signStr).orElse("");

        try {
            if (StringUtils.isNotBlank(options.getSecretKey())) {
                // API Key (HMAC-SHA512)
                return HmacUtils.signHmacSha512(options.getSecretKey(), signInput);
            } else {
                // Certificate (RSA)
                byte[] sign = PrivateKeyUtils.sign(options.getPrivateKey(), options.getCertificate().getSigAlgName(), signInput.getBytes());
                return Base64.toBase64String(sign);
            }
        } catch (Exception e) {
            throw new SignatureException("Signature failed:" + e.getMessage(), e);
        }
    }

    private static void handleError(BasicexResponse response) throws BasicexException {
        BasicexError error = null;
        BasicexException exception = null;

        try {
            error = ApiResource.GSON.fromJson(response.getBody(), BasicexError.class);
        } catch (JsonSyntaxException e) {
            raiseMalformedJsonError(response.getBody(), response.getCode(), response.getRequestId(), e);
        }

        if (error == null) {
            raiseMalformedJsonError(response.getBody(), response.getCode(), response.getRequestId(), null);
        }

        error.setLastResponse(response);

        switch (response.getCode()) {
            case 400:
            case 404:
                exception = new InvalidRequestException(
                        error.getMessage(),
                        error.getParam(),
                        response.getRequestId(),
                        error.getCode(),
                        response.getCode(),
                        null);
                break;
            case 401:
                exception =
                        new SignatureException(
                                error.getMessage(), response.getRequestId(), error.getCode(), response.getCode());
                break;
            case 429:
                exception =
                        new RateLimitException(
                                error.getMessage(),
                                response.getRequestId(),
                                error.getCode(),
                                response.getCode(),
                                null);
                break;
            default:
                exception =
                        new ApiException(
                                error.getMessage(), response.getRequestId(), error.getCode(), response.getCode(), null);
                break;
        }

        exception.setBasicexError(error);

        throw exception;
    }

    private static void raiseMalformedJsonError(
            String responseBody, int responseCode, String requestId, Throwable e) throws ApiException {
        String details = e == null ? "none" : e.getMessage();
        throw new ApiException(
                String.format(
                        "Invalid response object from API: %s. (HTTP response code was %d). Additional details: %s.",
                        responseBody, responseCode, details),
                requestId,
                null,
                responseCode,
                e);
    }

    private static HttpClient buildDefaultHttpClient() {
        return new HttpURLConnectionClient();
    }
}
