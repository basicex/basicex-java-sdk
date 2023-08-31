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

import com.basicex.sdk.util.CaseInsensitiveMap;

import java.util.*;

/**
 * A read-only view of a set of HTTP headers.
 */
public class HttpHeaders {
    private CaseInsensitiveMap<List<String>> headerMap;

    private HttpHeaders(CaseInsensitiveMap<List<String>> headerMap) {
        this.headerMap = headerMap;
    }

    /**
     * Returns an {@link HttpHeaders} instance initialized from the given map.
     *
     * @param headerMap the map containing the header names and values
     * @return an {@link HttpHeaders} instance containing the given headers
     * @throws NullPointerException if {@code headerMap} is {@code null}
     */
    public static HttpHeaders of(Map<String, List<String>> headerMap) {
        Objects.requireNonNull(headerMap);
        return new HttpHeaders(CaseInsensitiveMap.of(headerMap));
    }

    /**
     * Returns a new {@link HttpHeaders} instance containing the headers of the current instance plus
     * the provided header.
     *
     * @param name the name of the header to add
     * @param value the value of the header to add
     * @return the new {@link HttpHeaders} instance
     * @throws NullPointerException if {@code name} or {@code value} is {@code null}
     */
    public HttpHeaders withAdditionalHeader(String name, String value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        return this.withAdditionalHeader(name, Arrays.asList(value));
    }

    /**
     * Returns a new {@link HttpHeaders} instance containing the headers of the current instance plus
     * the provided header.
     *
     * @param name the name of the header to add
     * @param values the values of the header to add
     * @return the new {@link HttpHeaders} instance
     * @throws NullPointerException if {@code name} or {@code values} is {@code null}
     */
    public HttpHeaders withAdditionalHeader(String name, List<String> values) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(values);
        Map<String, List<String>> headerMap = new HashMap<>();
        headerMap.put(name, values);
        return this.withAdditionalHeaders(headerMap);
    }

    /**
     * Returns a new {@link HttpHeaders} instance containing the headers of the current instance plus
     * the provided headers.
     *
     * @param headerMap the map containing the headers to add
     * @return the new {@link HttpHeaders} instance
     * @throws NullPointerException if {@code headerMap} is {@code null}
     */
    public HttpHeaders withAdditionalHeaders(Map<String, List<String>> headerMap) {
        Objects.requireNonNull(headerMap);
        Map<String, List<String>> newHeaderMap = new HashMap<>(this.map());
        newHeaderMap.putAll(headerMap);
        return HttpHeaders.of(newHeaderMap);
    }

    /**
     * Returns an unmodifiable List of all of the header string values of the given named header.
     * Always returns a List, which may be empty if the header is not present.
     *
     * @param name the header name
     * @return a List of headers string values
     */
    public List<String> allValues(String name) {
        if (this.headerMap.containsKey(name)) {
            List<String> values = this.headerMap.get(name);
            if ((values != null) && (values.size() > 0)) {
                return Collections.unmodifiableList(values);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Returns an {@link Optional} containing the first header string value of the given named (and
     * possibly multi-valued) header. If the header is not present, then the returned {@code Optional}
     * is empty.
     *
     * @param name the header name
     * @return an {@code Optional<String>} containing the first named header string value, if present
     */
    public Optional<String> firstValue(String name) {
        if (this.headerMap.containsKey(name)) {
            List<String> values = this.headerMap.get(name);
            if ((values != null) && (values.size() > 0)) {
                return Optional.of(values.get(0));
            }
        }
        return Optional.empty();
    }

    /**
     * Returns an unmodifiable Map view of this HttpHeaders.
     *
     * @return the Map
     */
    public Map<String, List<String>> map() {
        return Collections.unmodifiableMap(this.headerMap);
    }

    /**
     * Returns this {@link HttpHeaders} as a string.
     *
     * @return a string describing the HTTP headers
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" { ");
        sb.append(map());
        sb.append(" }");
        return sb.toString();
    }
}
