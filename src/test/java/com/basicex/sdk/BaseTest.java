package com.basicex.sdk;

import java.io.IOException;
import java.security.cert.CertificateException;

public abstract class BaseTest {
//    public String configPath = "C:\\Users\\前端 1\\Downloads\\3e123583-6f14-4424-a42c-34e711ef7d03 (1)\\config.json";
    public String configPath = "D:\\dowloads\\003ec33e-8f0a-43f8-90f2-9577396dc2ac\\config.json";

    public BasicExClient getClient() throws CertificateException, IOException {
        return new BasicExClient(configPath);
    }
}
