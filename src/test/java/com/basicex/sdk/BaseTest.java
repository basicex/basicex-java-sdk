package com.basicex.sdk;

import java.io.IOException;
import java.security.cert.CertificateException;

public abstract class BaseTest {
    public String configPath = "D:\\70f1ad61-cb4b-4970-bf34-b0a79bfa6e92\\config.json";
    // public String configPath = "D:\\dowloads\\003ec33e-8f0a-43f8-90f2-9577396dc2ac\\config.json";

    public BasicExClient getClient() throws CertificateException, IOException {
        return new BasicExClient(configPath);
    }
}
