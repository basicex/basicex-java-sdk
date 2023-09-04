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

import com.basicex.sdk.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BasicexResponseStream extends AbstractBasicexResponse<InputStream> {

    /**
     * Initializes a new instance of the {@link BasicexResponseStream} class.
     *
     * @param code the HTTP status code of the response
     * @param headers the HTTP headers of the response
     * @param body streaming body response
     * @throws NullPointerException if {@code headers} or {@code body} is {@code null}
     */
    public BasicexResponseStream(int code, HttpHeaders headers, InputStream body) {
        super(code, headers, body);
    }

    /**
     * Buffers the entire response body into a string, constructing the appropriate BasicexResponse
     *
     * @return the BasicexResponse
     */
    BasicexResponse unstream() throws IOException {
        final String bodyString = StreamUtils.readToEnd(this.getBody(), StandardCharsets.UTF_8);
        this.getBody().close();
        return new BasicexResponse(this.getCode(), this.getHeaders(), bodyString);
    }
}
