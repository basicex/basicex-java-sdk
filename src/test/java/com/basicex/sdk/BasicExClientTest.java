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
