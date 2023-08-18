package com.basicex.sdk.net;

import com.basicex.sdk.BasicEx;
import com.basicex.sdk.exception.ApiConnectionException;
import com.basicex.sdk.exception.BasicexException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Base abstract class for HTTP clients used to send requests to BasicEx's API.
 */
public abstract class HttpClient {
    /** Maximum sleep time between tries to send HTTP requests after network failure. */
    public static final Duration maxNetworkRetriesDelay = Duration.ofSeconds(5);

    /** Minimum sleep time between tries to send HTTP requests after network failure. */
    public static final Duration minNetworkRetriesDelay = Duration.ofMillis(500);

    /** A value indicating whether the client should sleep between automatic request retries. */
    boolean networkRetriesSleep = true;

    /** Initializes a new instance of the {@link sun.net.www.http.HttpClient} class. */
    protected HttpClient() {}

    /**
     * Sends the given request to BasicEx's API, buffering the response body into memory.
     *
     * @param request the request
     * @return the response
     * @throws BasicexException If the request fails for any reason
     */
    public abstract BasicexResponse request(BasicexRequest request) throws BasicexException;

    /**
     * Sends the given request to BasicEx's API, streaming the response body.
     *
     * @param request the request
     * @return the response
     * @throws BasicexException If the request fails for any reason
     */
    public BasicexResponseStream requestStream(BasicexRequest request) throws BasicexException {
        throw new UnsupportedOperationException("requestStream is unimplemented for this HttpClient");
    }

    @FunctionalInterface
    private interface RequestSendFunction<R> {
        R apply(BasicexRequest request) throws BasicexException;
    }

    public <T extends AbstractBasicexResponse<?>> T sendWithRetries(
            BasicexRequest request, RequestSendFunction<T> send) throws BasicexException {
        ApiConnectionException requestException = null;
        T response = null;
        int retry = 0;

        while (true) {
            requestException = null;

            try {
                response = send.apply(request);
            } catch (ApiConnectionException e) {
                requestException = e;
            }

            if (!this.shouldRetry(retry, requestException, request, response)) {
                break;
            }

            retry += 1;

            try {
                Thread.sleep(this.sleepTime(retry).toMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (requestException != null) {
            throw requestException;
        }

        response.setNumRetries(retry);

        return response;
    }

    /**
     * Sends the given request to BasicEx's API, retrying the request in cases of intermittent
     * problems.
     *
     * @param request the request
     * @return the response
     * @throws BasicexException If the request fails for any reason
     */
    public BasicexResponse requestWithRetries(BasicexRequest request) throws BasicexException {
        return sendWithRetries(request, this::request);
    }

    /**
     * Sends the given request to BasicEx's API, streaming the response, retrying the request in cases
     * of intermittent problems.
     *
     * @param request the request
     * @return the response
     * @throws BasicexException If the request fails for any reason
     */
    public BasicexResponseStream requestStreamWithRetries(BasicexRequest request)
            throws BasicexException {
        return sendWithRetries(request, this::requestStream);
    }

    /**
     * Builds the value of the {@code User-Agent} header.
     *
     * @return a string containing the value of the {@code User-Agent} header
     */
    protected static String buildUserAgentString() {
        String userAgent = String.format("BasicEx/v2 JavaBindings/%s", BasicEx.VERSION);

        return userAgent;
    }

    private <T extends AbstractBasicexResponse<?>> boolean shouldRetry(
            int numRetries, BasicexException exception, BasicexRequest request, T response) {
        // Do not retry if we are out of retries.
        if (numRetries >= request.getOptions().getMaxNetworkRetries()) {
            return false;
        }

        // Retry on connection error.
        if ((exception != null)
                && (exception.getCause() != null)
                && (exception.getCause() instanceof ConnectException
                || exception.getCause() instanceof SocketTimeoutException)) {
            return true;
        }

        // Retry on conflict errors.
        if ((response != null) && (response.getCode() == 409)) {
            return true;
        }

        return false;
    }

    private Duration sleepTime(int numRetries) {
        // We disable sleeping in some cases for tests.
        if (!this.networkRetriesSleep) {
            return Duration.ZERO;
        }

        // Apply exponential backoff with MinNetworkRetriesDelay on the number of numRetries
        // so far as inputs.
        Duration delay =
                Duration.ofNanos((long) (minNetworkRetriesDelay.toNanos() * Math.pow(2, numRetries - 1)));

        // Do not allow the number to exceed MaxNetworkRetriesDelay
        if (delay.compareTo(maxNetworkRetriesDelay) > 0) {
            delay = maxNetworkRetriesDelay;
        }

        // Apply some jitter by randomizing the value in the range of 75%-100%.
        double jitter = ThreadLocalRandom.current().nextDouble(0.75, 1.0);
        delay = Duration.ofNanos((long) (delay.toNanos() * jitter));

        // But never sleep less than the base sleep seconds.
        if (delay.compareTo(minNetworkRetriesDelay) < 0) {
            delay = minNetworkRetriesDelay;
        }

        return delay;
    }

}
