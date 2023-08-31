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

import java.io.*;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the content of an HTTP request, i.e. the request's body. This class also holds the
 * value of the {@code Content-Type} header, which can depend on the body in some cases (e.g. for
 * multipart requests).
 */
public class HttpContent {
    /** The request's content, as a byte array. */
    private final byte[] byteArrayContent;

    /** The value of the {@code Content-Type} header. */
    private final String contentType;

    private HttpContent(byte[] byteArrayContent, String contentType) {
        this.byteArrayContent = byteArrayContent;
        this.contentType = contentType;
    }

    /**
     * Builds a new HttpContent for name/value tuples encoded using {@code
     * application/x-www-form-urlencoded} MIME type.
     *
     * @param nameValueCollection the collection of name/value tuples to encode
     * @return the encoded HttpContent instance
     * @throws IllegalArgumentException if nameValueCollection is null
     */
    public static HttpContent buildFormURLEncodedContent(
            Collection<KeyValuePair<String, String>> nameValueCollection) throws IOException {
        Objects.requireNonNull(nameValueCollection);

        return new HttpContent(
                FormEncoder.createQueryString(nameValueCollection).getBytes(ApiResource.CHARSET),
                String.format("application/x-www-form-urlencoded;charset=%s", ApiResource.CHARSET));
    }

    /** The request's content, as a string. */
    public String stringContent() {
        return new String(this.byteArrayContent, ApiResource.CHARSET);
    }

    /**
     * Builds a new HttpContent for name/value tuples encoded using {@code multipart/form-data} MIME
     * type.
     *
     * @param nameValueCollection the collection of name/value tuples to encode
     * @return the encoded HttpContent instance
     * @throws IllegalArgumentException if nameValueCollection is null
     */
    public static HttpContent buildMultipartFormDataContent(
            Collection<KeyValuePair<String, Object>> nameValueCollection) throws IOException {
        String boundary = UUID.randomUUID().toString();
        return buildMultipartFormDataContent(nameValueCollection, boundary);
    }

    public static HttpContent buildApplicationJsonContent(Object params) throws IOException {
        String body = ApiResource.GSON.toJson(params);
        return new HttpContent(
                body.getBytes(), String.format("application/json;charset=%s", ApiResource.CHARSET));
    }


    /**
     * Builds a new HttpContent for name/value tuples encoded using {@code multipart/form-data} MIME
     * type.
     *
     * @param nameValueCollection the collection of name/value tuples to encode
     * @param boundary the boundary
     * @return the encoded HttpContent instance
     * @throws IllegalArgumentException if nameValueCollection is null
     */
    public static HttpContent buildMultipartFormDataContent(
            Collection<KeyValuePair<String, Object>> nameValueCollection, String boundary)
            throws IOException {
        Objects.requireNonNull(nameValueCollection);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MultipartProcessor multipartProcessor = null;
        try {
            multipartProcessor = new MultipartProcessor(baos, boundary, ApiResource.CHARSET);

            for (KeyValuePair<String, Object> entry : nameValueCollection) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof File) {
                    File file = (File) value;
                    multipartProcessor.addFileField(key, file.getName(), new FileInputStream(file));
                } else if (value instanceof InputStream) {
                    multipartProcessor.addFileField(key, "blob", (InputStream) value);
                } else {
                    multipartProcessor.addFormField(key, (String) value);
                }
            }
        } finally {
            if (multipartProcessor != null) {
                multipartProcessor.finish();
            }
        }

        return new HttpContent(
                baos.toByteArray(), String.format("multipart/form-data; boundary=%s", boundary));
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getByteArrayContent() {
        return byteArrayContent;
    }
}
