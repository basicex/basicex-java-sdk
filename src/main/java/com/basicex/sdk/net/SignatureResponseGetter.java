package com.basicex.sdk.net;

import com.basicex.sdk.BasicExConfig;
import com.basicex.sdk.exception.*;
import com.basicex.sdk.model.BasicexError;
import com.basicex.sdk.model.BasicexObject;
import com.basicex.sdk.util.PrivateKeyUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
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
    public <T extends BasicexObject> T request(ApiResource.RequestMethod method, String path, Object params, Type typeToken, Boolean signRequest, RequestOptions options) throws BasicexException {

        String fullUrl = String.format("%s%s", Optional.ofNullable(options).map(x->Optional.ofNullable(x.getApiBaseUrl()).orElse(config.getApiBaseUrl())).orElse(config.getApiBaseUrl()), path);
        BasicexRequest request = new BasicexRequest(method, fullUrl, params, RequestOptions.merge(options, config));
        if (signRequest) {
            request.getHeaders().withAdditionalHeader("X-Signature", signature(params, path, request.getOptions()));
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
            JsonObject jsonObject =
                    ApiResource.GSON.fromJson(response.getBody(), JsonObject.class).getAsJsonObject("data");
            if(jsonObject != null) {
                resource = ApiResource.GSON.fromJson(jsonObject, typeToken);
            }
        } catch (JsonSyntaxException e) {
            raiseMalformedJsonError(responseBody, responseCode, requestId, e);
        }

        resource.setLastResponse(response);

        return resource;
    }

    @Override
    public InputStream requestStream(ApiResource.RequestMethod method, String path, Object params, Boolean signRequest, RequestOptions options) throws BasicexException {
        String fullUrl = String.format("%s%s", Optional.ofNullable(options).map(x->Optional.ofNullable(x.getApiBaseUrl()).orElse(config.getApiBaseUrl())).orElse(config.getApiBaseUrl()), path);
        BasicexRequest request = new BasicexRequest(method, fullUrl, params, RequestOptions.merge(options, config));
        if (signRequest) {
            request.getHeaders().withAdditionalHeader("X-Signature", signature(params, path, request.getOptions()));
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
        String signStr = ApiResource.GSON.toJson(params);
        String signInput = options.getApiBaseUrl() + path + signStr;

        try {
            byte[] sign = PrivateKeyUtils.sign(options.getPrivateKey(), options.getCertificate().getSigAlgName(), signInput.getBytes());
            return Base64.toBase64String(sign);
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

        exception.setStripeError(error);

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
