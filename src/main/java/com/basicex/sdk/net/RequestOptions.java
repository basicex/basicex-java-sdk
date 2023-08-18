package com.basicex.sdk.net;

import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

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

    public X509Certificate getCertificate() {
        return certificate;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public Integer getMaxNetworkRetries() {
        return maxNetworkRetries;
    }

    public Proxy getConnectionProxy() {
        return connectionProxy;
    }

    public PasswordAuthentication getProxyCredential() {
        return proxyCredential;
    }
}
