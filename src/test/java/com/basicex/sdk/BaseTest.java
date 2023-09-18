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

    //public String configPath = "D:\\dowloads\\1fbfcf88-7044-4fc6-a695-04388e3d13ea\\config.json";

    // public String configPath = "D:\\f22c4d55-6670-4368-82fa-a8a55e664bfd\\config.json";
    // public String configPath = "D:\\8f0867ba-085f-447c-882a-0684c4f6ded2\\config.json";
    public String configPath = "D:\\5ad02ef2-e9f7-4d5c-b097-54dc550ba4b4\\config.json";

    // public String configPath = "D:\\production_test\\config.json";
    // public String configPath = "D:\\dowloads\\zhimaDADA\\f22c4d55-6670-4368-82fa-a8a55e664bfd\\config.json";
    public BasicExClient getClient() throws CertificateException, IOException {
        return new BasicExClient(configPath);
    }
}
