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
