/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Objects;

public class StreamUtils {
    private static final int DEFAULT_BUF_SIZE = 1024;

    /**
     * Reads the provided stream until the end and returns a string encoded with the provided charset.
     *
     * @param stream the stream to read
     * @param charset the charset to use
     * @return a string with the contents of the input stream
     * @throws NullPointerException if {@code stream} or {@code charset} is {@code null}
     * @throws IOException if an I/O error occurs
     */
    public static String readToEnd(InputStream stream, Charset charset) throws IOException {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(charset);

        final StringBuilder sb = new StringBuilder();
        final char[] buffer = new char[DEFAULT_BUF_SIZE];
        try(Reader in = new InputStreamReader(stream, charset)) {
            int charsRead = 0;
            while ((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
                sb.append(buffer, 0, charsRead);
            }
            return sb.toString();
        }
    }
}
