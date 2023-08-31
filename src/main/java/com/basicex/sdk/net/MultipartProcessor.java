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
import java.net.URLConnection;
import java.nio.charset.Charset;

public class MultipartProcessor {
    private final String boundary;
    private static final String LINE_BREAK = "\r\n";
    private OutputStream outputStream;
    private PrintWriter writer;

    /** Constructs a new multipart body builder. */
    public MultipartProcessor(OutputStream outputStream, String boundary, Charset charset)
            throws IOException {
        this.boundary = boundary;

        this.outputStream = outputStream;
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
    }

    /**
     * Adds a form field to the multipart message.
     *
     * @param name field name
     * @param value field value
     */
    public void addFormField(String name, String value) {
        writer.append("--" + boundary).append(LINE_BREAK);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_BREAK);
        writer.append(LINE_BREAK);
        writer.append(value).append(LINE_BREAK);
        writer.flush();
    }

    /**
     * Adds a file field to the multipart message, but takes in an InputStream instead of just a file
     * to read bytes from.
     *
     * @param name Field name
     * @param fileName Name of the "file" being uploaded.
     * @param inputStream Stream of bytes to use in place of a file.
     * @throws IOException Thrown when writing / reading from streams fails.
     */
    public void addFileField(String name, String fileName, InputStream inputStream)
            throws IOException {
        writer.append("--").append(boundary).append(LINE_BREAK);
        writer
                .append("Content-Disposition: form-data; name=\"")
                .append(name)
                .append("\"; filename=\"")
                .append(fileName)
                .append("\"")
                .append(LINE_BREAK);

        String probableContentType = URLConnection.guessContentTypeFromName(fileName);
        writer.append("Content-Type: ").append(probableContentType).append(LINE_BREAK);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_BREAK);
        writer.append(LINE_BREAK);
        writer.flush();

        streamToOutput(inputStream);

        writer.append(LINE_BREAK);
        writer.flush();
    }

    /**
     * Utility method to read all the bytes from an InputStream into the outputStream.
     *
     * @param inputStream Stream of bytes to read from.
     * @throws IOException Thrown on errors reading / writing.
     */
    private void streamToOutput(InputStream inputStream) throws IOException {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } finally {
            inputStream.close();
        }
    }

    /** Adds the final boundary to the multipart message and closes streams. */
    public void finish() throws IOException {
        writer.append("--" + boundary + "--").append(LINE_BREAK);
        writer.flush();
        writer.close();
        outputStream.flush();
        outputStream.close();
    }
}
