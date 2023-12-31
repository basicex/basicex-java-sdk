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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@Getter
@Setter
@Builder
public class RequestOptions {
    private final X509Certificate certificate;

    private final PrivateKey privateKey;

    private final String apiBaseUrl;

    private final Integer connectTimeout;
    private final Integer readTimeout;
    private final Integer maxNetworkRetries;
    private final Proxy connectionProxy;
    private final PasswordAuthentication proxyCredential;

    public static RequestOptions getDefault() {
        return new RequestOptions(null, null, null, null, null, null, null, null);
    }

    private RequestOptions(
            X509Certificate certificate,
            PrivateKey privateKey,
            String apiBaseUrl,
            Integer connectTimeout,
            Integer readTimeout,
            Integer maxNetworkRetries,
            Proxy connectionProxy,
            PasswordAuthentication proxyCredential) {
        this.certificate = certificate;
        this.privateKey = privateKey;
        this.apiBaseUrl = apiBaseUrl;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.maxNetworkRetries = maxNetworkRetries;
        this.connectionProxy = connectionProxy;
        this.proxyCredential = proxyCredential;
    }

    public static RequestOptions merge(RequestOptions options, BasicExConfig config) {
        if(options == null) {
            return new RequestOptions(
                    config.getCertificate(),
                    config.getPrivateKey(),
                    config.getApiBaseUrl(),
                    config.getConnectTimeout(),
                    config.getReadTimeout(),
                    config.getMaxNetworkRetries(),
                    config.getConnectionProxy(),
                    config.getProxyCredential());
        }

        return new RequestOptions(
                options.getCertificate() != null ? options.getCertificate() : config.getCertificate(),
                options.getPrivateKey() != null ? options.getPrivateKey() : config.getPrivateKey(),
                options.getApiBaseUrl() != null ? options.getApiBaseUrl() : config.getApiBaseUrl(),
                options.getConnectTimeout() != null ? options.getConnectTimeout() : config.getConnectTimeout(),
                options.getReadTimeout() != null ? options.getReadTimeout() : config.getReadTimeout(),
                options.getMaxNetworkRetries() != null ? options.getMaxNetworkRetries() : config.getMaxNetworkRetries(),
                options.getConnectionProxy() != null ? options.getConnectionProxy() : config.getConnectionProxy(),
                options.getProxyCredential() != null ? options.getProxyCredential() : config.getProxyCredential()
        );
    }

}
