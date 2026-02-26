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
    // public String configPath = "C:\\Users\\tt\\Downloads\\fc1c4e67-8b3f-4338-b4cc-78f3ed44170c\\config.json";

    // public String configPath = "D:\\production_test\\config.json";
    // public String configPath = "C:\\Users\\tt\\Downloads\\d59544ac-52e6-48e3-93bc-3c56ed469c7b\\config.json";
    public BasicExClient getClient() throws CertificateException, IOException {
        BasicExConfig config = BasicExConfig.builder()
                .apiKey("0sIBxa9U92L7Xm34jYm7VLprMcQOpe89b8f6Hu99ztwti9BDPl9GU3j619nx97bA")
                .secretKey("p0z2hM7LAoL9inH3GkVy2445d3GiU3i7DZfQ1unEISb4a6sD28RS2lg3qzw19ZrS")
                .apiBaseUrl("https://test-openapi.basicex.com/v2")
                .build();
        return new BasicExClient(config);
    }
}
