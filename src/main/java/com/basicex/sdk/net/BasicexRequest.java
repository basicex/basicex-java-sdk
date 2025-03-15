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

import com.basicex.sdk.exception.ApiConnectionException;
import com.basicex.sdk.exception.AuthenticationException;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.util.X509CertificateUtils;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * A request to BasicEx's API.
 */
@Data
public class BasicexRequest {
    /**
     * The HTTP method for the request (GET, POST or DELETE).
     */
    private ApiResource.RequestMethod method;
    /**
     * The URL for the request. If this is a GET or DELETE request, the URL also includes the request
     * parameters in its query string.
     */
    private URL url;

    /**
     * The body of the request. For POST requests, this will be either a {@code
     * application/x-www-form-urlencoded} or a {@code multipart/form-data} payload. For non-POST
     * requests, this will be {@code null}.
     */
    private HttpContent content;

    /**
     * The HTTP headers of the request
     */
    private HttpHeaders headers;

    private Object params;

    /** The special modifiers of the request. */
    private RequestOptions options;

    /**
     * Initializes a new instance of the {@link BasicexRequest} class.
     *
     * @param method the HTTP method
     * @param url the URL of the request
     * @param params the parameters of the request
     * @param options the special modifiers of the request
     * @throws BasicexRequest if the request cannot be initialized for any reason
     */
    public BasicexRequest(
            ApiResource.RequestMethod method,
            String url,
            Object params,
            RequestOptions options)
            throws BasicexException {
        try {
            this.params = params;
            this.options = options;
            this.method = method;
            this.url = new URL(url);
            this.content = buildContent(method, params);
            this.headers = buildHeaders(method, this.options);
        } catch (IOException e) {
            throw new ApiConnectionException(
                    String.format(
                            "IOException during API request to BasicEx (%s): %s ",
                            options.getApiBaseUrl(), e.getMessage()),
                    e);
        } catch (CertificateException e) {
            throw new AuthenticationException(String.format(
                    "CertificateException during API request to BasicEx (%s): %s ",
                    options.getApiBaseUrl(), e.getMessage()), null, null, null, e);
        }
    }

    private static HttpContent buildContent(
            ApiResource.RequestMethod method, Object params) throws IOException {
        if (method != ApiResource.RequestMethod.POST && method != ApiResource.RequestMethod.PUT) {
            return null;
        }

        return HttpContent.buildApplicationJsonContent(params);
    }

    private static HttpHeaders buildHeaders(ApiResource.RequestMethod method, RequestOptions options)
            throws IOException, AuthenticationException, CertificateException {
        Map<String, List<String>> headerMap = new HashMap<>();

        // Accept
        headerMap.put("Accept", Collections.singletonList("application/json"));

        // Accept-Charset
        headerMap.put("Accept-Charset", Collections.singletonList(ApiResource.CHARSET.name()));

        // Authorization
        X509Certificate certificate = options.getCertificate();
        if (certificate == null) {
            throw new AuthenticationException(
                    "No Merchant certificate provided.",
                    null,
                    null,
                    0);
        }

        headerMap.put("X-Identity", Collections.singletonList(X509CertificateUtils.toPEMString(certificate).replaceAll("[\r\n]+", "")));

        return HttpHeaders.of(headerMap);
    }
}
