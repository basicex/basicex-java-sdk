/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.cert.CertificateException;

public class BasicExClientTest {

    @Test
    void basicExClientConfigTest() throws CertificateException, IOException {
        BasicExClient client = new BasicExClient("C:\\Users\\前端 1\\Downloads\\3e123583-6f14-4424-a42c-34e711ef7d03 (1)\\config.json");
        Assertions.assertNotNull(client);
    }

    @Test
    void basicExClientPrivateKeyAndCertificateTest() throws CertificateException, IOException {
        BasicExClient client = new BasicExClient("C:\\Users\\前端 1\\Downloads\\3e123583-6f14-4424-a42c-34e711ef7d03 (1)\\811324051595265.key", "C:\\Users\\前端 1\\Downloads\\3e123583-6f14-4424-a42c-34e711ef7d03 (1)\\811324051595265.crt");
        Assertions.assertNotNull(client);
    }
}
