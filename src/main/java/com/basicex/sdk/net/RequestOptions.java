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
