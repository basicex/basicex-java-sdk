package com.basicex.sdk;

import java.net.PasswordAuthentication;
import java.net.Proxy;

public class BasicEx {
    public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 80 * 1000;

    public static final String VERSION = "";

    public static final String OPEN_API_BASE = "https://openapi.basicex.com";

    // Note that URLConnection reserves the value of 0 to mean "infinite
    // timeout", so we use -1 here to represent an unset value which should
    // fall back to a default.
    private static volatile int connectTimeout = -1;
    private static volatile int readTimeout = -1;

    private static volatile int maxNetworkRetries = 0;

    private static volatile Proxy connectionProxy = null;
    private static volatile PasswordAuthentication proxyCredential = null;

    private static volatile String apiBase = OPEN_API_BASE;

    /**
     * (FOR TESTING ONLY) If you'd like your API requests to hit your own (mocked) server, you can set
     * this up here by overriding the base api URL.
     */
    public static void overrideApiBase(final String overriddenApiBase) {
        apiBase = overriddenApiBase;
    }

    public static String getApiBase() {
        return apiBase;
    }

    /**
     * Set proxy to tunnel all Stripe connections.
     *
     * @param proxy proxy host and port setting
     */
    public static void setConnectionProxy(final Proxy proxy) {
        connectionProxy = proxy;
    }

    public static Proxy getConnectionProxy() {
        return connectionProxy;
    }

    /**
     * Returns the connection timeout.
     *
     * @return timeout value in milliseconds
     */
    public static int getConnectTimeout() {
        if (connectTimeout == -1) {
            return DEFAULT_CONNECT_TIMEOUT;
        }
        return connectTimeout;
    }

    /**
     * Returns the read timeout.
     *
     * @return timeout value in milliseconds
     */
    public static int getReadTimeout() {
        if (readTimeout == -1) {
            return DEFAULT_READ_TIMEOUT;
        }
        return readTimeout;
    }

    /**
     * Sets the timeout value that will be used when reading data from an established connection to
     * the BasicEx API (in milliseconds).
     *
     * <p>Note that this value should be set conservatively because some API requests can take time
     * and a short timeout increases the likelihood of causing a problem in the backend.
     *
     * @param timeout timeout value in milliseconds
     */
    public static void setReadTimeout(final int timeout) {
        readTimeout = timeout;
    }

    /**
     * Returns the maximum number of times requests will be retried.
     *
     * @return the maximum number of times requests will be retried
     */
    public static int getMaxNetworkRetries() {
        return maxNetworkRetries;
    }

    /**
     * Sets the maximum number of times requests will be retried.
     *
     * @param numRetries the maximum number of times requests will be retried
     */
    public static void setMaxNetworkRetries(final int numRetries) {
        maxNetworkRetries = numRetries;
    }

    /**
     * Provide credential for proxy authorization if required.
     *
     * @param auth proxy required userName and password
     */
    public static void setProxyCredential(final PasswordAuthentication auth) {
        proxyCredential = auth;
    }

    public static PasswordAuthentication getProxyCredential() {
        return proxyCredential;
    }
}
