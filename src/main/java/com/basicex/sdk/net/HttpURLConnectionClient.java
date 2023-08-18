package com.basicex.sdk.net;

import com.basicex.sdk.exception.ApiConnectionException;
import com.basicex.sdk.exception.BasicexException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.util.*;

public class HttpURLConnectionClient extends HttpClient {
    /** Initializes a new instance of the {@link HttpURLConnectionClient}. */
    public HttpURLConnectionClient() {
        super();
    }

    /**
     * Sends the given request to BasicEx's API.
     *
     * @param request the request
     * @return the response
     * @throws ApiConnectionException if an error occurs when sending or receiving
     */
    @Override
    public BasicexResponseStream requestStream(BasicexRequest request) throws ApiConnectionException {
        try {
            final HttpURLConnection conn = createConnection(request);

            // Calling `getResponseCode()` triggers the request.
            final int responseCode = conn.getResponseCode();

            final HttpHeaders headers = HttpHeaders.of(conn.getHeaderFields());

            final InputStream responseStream =
                    (responseCode >= 200 && responseCode < 300)
                            ? conn.getInputStream()
                            : conn.getErrorStream();

            return new BasicexResponseStream(responseCode, headers, responseStream);

        } catch (IOException e) {
            throw new ApiConnectionException(
                    String.format(
                            "IOException during API request to BasicEx (%s): %s",
                            request.getOptions().getApiBaseUrl(), e.getMessage()),
                    e);
        }
    }

    /**
     * Sends the given request to Stripe's API, and returns a buffered response.
     *
     * @param request the request
     * @return the response
     * @throws ApiConnectionException if an error occurs when sending or receiving
     */
    @Override
    public BasicexResponse request(BasicexRequest request) throws BasicexException {
        final BasicexResponseStream responseStream = requestStream(request);
        try {
            return responseStream.unstream();
        } catch (IOException e) {
            throw new ApiConnectionException(
                    String.format(
                            "IOException during API request to Stripe (%s): %s ",
                            request.getOptions().getApiBaseUrl(), e.getMessage()),
                    e);
        }
    }

    private static HttpHeaders getHeaders(BasicexRequest request) {
        Map<String, List<String>> userAgentHeadersMap = new HashMap<>();

        userAgentHeadersMap.put("User-Agent", Collections.singletonList(buildUserAgentString()));
        return request.getHeaders().withAdditionalHeaders(userAgentHeadersMap);
    }


    private static HttpURLConnection createConnection(BasicexRequest request)
            throws IOException, ApiConnectionException {
        HttpURLConnection conn = null;

        if (request.getOptions().getConnectionProxy() != null) {
            conn =
                    (HttpURLConnection) request.getUrl().openConnection(request.getOptions().getConnectionProxy());
            Authenticator.setDefault(
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return request.getOptions().getProxyCredential();
                        }
                    });
        } else {
            conn = (HttpURLConnection) request.getUrl().openConnection();
        }

        conn.setConnectTimeout(request.getOptions().getConnectTimeout());
        conn.setReadTimeout(request.getOptions().getReadTimeout());
        conn.setUseCaches(false);
        for (Map.Entry<String, List<String>> entry : getHeaders(request).map().entrySet()) {
            conn.setRequestProperty(entry.getKey(), String.join(",", entry.getValue()));
        }

        conn.setRequestMethod(request.getMethod().name());

        if (request.getContent() != null) {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", request.getContent().getContentType());

            try(OutputStream output = conn.getOutputStream()) {
                output.write(request.getContent().getByteArrayContent());
            }
        }

        return conn;
    }
}
