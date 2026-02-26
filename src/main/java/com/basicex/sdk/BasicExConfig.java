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
    private volatile String apiKey;
    private volatile String secretKey;

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

    public BasicExConfig(PrivateKey privateKey, X509Certificate certificate, String certificateSerialNumber, String merchantCode, int connectTimeout, int readTimeout, int maxNetworkRetries, Proxy connectionProxy, PasswordAuthentication proxyCredential, String apiBaseUrl) {
        this.privateKey = privateKey;
        this.certificate = certificate;
        this.certificateSerialNumber = certificateSerialNumber;
        this.merchantCode = merchantCode;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.maxNetworkRetries = maxNetworkRetries;
        this.connectionProxy = connectionProxy;
        this.proxyCredential = proxyCredential;
        this.apiBaseUrl = apiBaseUrl;
        validateAuth();
    }

    public BasicExConfig(String apiKey, String secretKey, int connectTimeout, int readTimeout, int maxNetworkRetries, Proxy connectionProxy, PasswordAuthentication proxyCredential, String apiBaseUrl) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.maxNetworkRetries = maxNetworkRetries;
        this.connectionProxy = connectionProxy;
        this.proxyCredential = proxyCredential;
        this.apiBaseUrl = apiBaseUrl;
        validateAuth();
    }

    private void validateAuth() {
        boolean hasKeyAuth = StringUtils.isNotBlank(this.apiKey) || StringUtils.isNotBlank(this.secretKey);
        boolean hasCertAuth = this.certificate != null || this.privateKey != null;

        if (hasKeyAuth && hasCertAuth) {
            throw new IllegalArgumentException("Cannot configure both API Key and Certificate at the same time. Please choose one.");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static BasicExConfig loadConfig(String configFilePath) throws IOException, CertificateException {
        return ConfigFileLoader.load(configFilePath);
    }

    public static BasicExConfig merge(BasicExConfig old, BasicExConfig config) {
        BasicExConfig merged = new BasicExConfig();
        if(old == null) {
            merged.privateKey = config.getPrivateKey();
            merged.certificate = config.getCertificate();
            merged.certificateSerialNumber = config.getCertificateSerialNumber();
            merged.merchantCode = config.getMerchantCode();
            merged.apiKey = config.getApiKey();
            merged.secretKey = config.getSecretKey();
            merged.connectTimeout = config.getConnectTimeout();
            merged.readTimeout = config.getReadTimeout();
            merged.maxNetworkRetries = config.getMaxNetworkRetries();
            merged.connectionProxy = config.getConnectionProxy();
            merged.proxyCredential = config.getProxyCredential();
            merged.apiBaseUrl = config.getApiBaseUrl();
            return merged;
        }

        merged.privateKey = config.getPrivateKey() != null ? config.getPrivateKey() : old.getPrivateKey();
        merged.certificate = config.getCertificate() != null ? config.getCertificate() : old.getCertificate();
        merged.certificateSerialNumber = old.getCertificateSerialNumber();
        merged.merchantCode = old.getMerchantCode();
        merged.apiKey = config.getApiKey() != null ? config.getApiKey() : old.getApiKey();
        merged.secretKey = config.getSecretKey() != null ? config.getSecretKey() : old.getSecretKey();
        merged.connectTimeout = config.getConnectTimeout() != 0 ? config.getConnectTimeout() : old.getConnectTimeout();
        merged.readTimeout = config.getReadTimeout() != 0 ? config.getReadTimeout() : old.getReadTimeout();
        merged.maxNetworkRetries = config.getMaxNetworkRetries() != 0 ? config.getMaxNetworkRetries() : old.getMaxNetworkRetries();
        merged.connectionProxy = config.getConnectionProxy() != null ? config.getConnectionProxy() : old.getConnectionProxy();
        merged.proxyCredential = config.getProxyCredential() != null ? config.getProxyCredential() : old.getProxyCredential();
        merged.apiBaseUrl = config.getApiBaseUrl() != null ? config.getApiBaseUrl() : old.getApiBaseUrl();

        return merged;
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

        public Builder apiKey(String apiKey) {
            this.config.apiKey = apiKey;
            return this;
        }

        public Builder secretKey(String secretKey) {
            this.config.secretKey = secretKey;
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
            this.config.validateAuth();
            return this.config;
        }
    }
}
