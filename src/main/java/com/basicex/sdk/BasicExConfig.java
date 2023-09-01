/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk;

import com.basicex.sdk.util.StringUtils;
import com.basicex.sdk.util.X509CertificateUtils;
import lombok.Data;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;

/**
 * BasicEx API Client Configuration
 */
@Data
public class BasicExConfig {
    public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 80 * 1000;

    private volatile PrivateKey privateKey;
    private volatile X509Certificate certificate;
    private volatile String certificateSerialNumber;
    private volatile String merchantCode;

    // Note that URLConnection reserves the value of 0 to mean "infinite
    // timeout", so we use -1 here to represent an unset value which should
    // fall back to a default.
    private volatile int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private volatile int readTimeout = DEFAULT_READ_TIMEOUT;

    private volatile int maxNetworkRetries = 0;

    private volatile Proxy connectionProxy = null;
    private volatile PasswordAuthentication proxyCredential = null;

    private volatile String apiBaseUrl = "https://openapi.basicex.com/v2";

    private BasicExConfig() {}

    public static Builder builder() {
        return new Builder();
    }

    public static BasicExConfig loadConfig(String configFilePath) throws IOException, CertificateException {
        return ConfigFileLoader.load(configFilePath);
    }

    public static class Builder {
        private final BasicExConfig config;

        private Builder(BasicExConfig config) {
            this.config = config;
        }

        private Builder() {
            this(new BasicExConfig());
        }

        public Builder privateKey(PrivateKey privateKey) {
            this.config.privateKey = Objects.requireNonNull(privateKey, "privateKey must not be null");
            return this;
        }

        public Builder certificate(X509Certificate certificate) throws IOException {
            this.config.certificate = Objects.requireNonNull(certificate, "certificate must not be null");

            // get serial number from certificate
            this.config.certificateSerialNumber = X509CertificateUtils.getCertificateSerialNo(certificate);

            // get merchant code from certificate
            this.config.merchantCode = X509CertificateUtils.getCommonNameFromCertificate(certificate);

            return this;
        }

        public Builder connectionProxy(Proxy connectionProxy) {
            this.config.connectionProxy = connectionProxy;
            return this;
        }

        public Builder connectTimeout(int connectTimeout) {
            this.config.connectTimeout = connectTimeout;
            return this;
        }

        public Builder maxNetworkRetries(int maxNetworkRetries) {
            this.config.maxNetworkRetries = maxNetworkRetries;
            return this;
        }

        public Builder proxyCredential(PasswordAuthentication proxyCredential) {
            this.config.proxyCredential = proxyCredential;
            return this;
        }

        public Builder proxy(Proxy connectionProxy, PasswordAuthentication proxyCredential) {
            this.config.connectionProxy = connectionProxy;
            this.config.proxyCredential = proxyCredential;
            return this;
        }

        public Builder readTimeout(int readTimeout) {
            this.config.readTimeout = readTimeout;
            return this;
        }

        public Builder apiBaseUrl(String apiBaseUrl) {
            if(StringUtils.isNotEmpty(apiBaseUrl)) {
                this.config.apiBaseUrl = apiBaseUrl;
            }
            return this;
        }

        public BasicExConfig build() {
            return this.config;
        }
    }
}
