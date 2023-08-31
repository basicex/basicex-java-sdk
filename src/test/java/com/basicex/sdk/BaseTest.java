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

import java.io.IOException;
import java.security.cert.CertificateException;

public abstract class BaseTest {
    // public String configPath = "D:\\70f1ad61-cb4b-4970-bf34-b0a79bfa6e92\\config.json";
    // public String configPath = "D:\\dowloads\\003ec33e-8f0a-43f8-90f2-9577396dc2ac\\config.json";

    // 新的
    // public String configPath = "D:\\4d1ebd88-8154-4ca1-b1c6-051b7d28c204\\config.json";

    // 徐哥的
    // public String configPath = "D:\\ac2e6e63-49c0-424c-86c3-10144cce3437\\ac2e6e63-49c0-424c-86c3-10144cce3437\\config.json";

    // 生产测试商户
    public String configPath = "D:\\production_test\\config.json";
    public BasicExClient getClient() throws CertificateException, IOException {
        return new BasicExClient(configPath);
    }
}
