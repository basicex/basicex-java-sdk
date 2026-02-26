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

import com.basicex.sdk.util.PrivateKeyUtils;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;

public class SignatureTest {
    @Test
    public void signatureTest() throws Exception {
        String privateKeyFilePath = "D:\\production_test\\819275770875906.key";
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(new File(privateKeyFilePath).toURI()));

        PrivateKey privateKey = PrivateKeyUtils.loadPrivateKey(new String(privateKeyBytes));
        byte[] sign = PrivateKeyUtils.sign(privateKey, "SHA256withRSA", "aaa".getBytes());



        System.out.println(Base64.toBase64String(sign));
    }

    @Test
    public void signatureTest1() throws Exception {
        PrivateKey privateKey = PrivateKeyUtils.loadPrivateKey("-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCy5LoV4Wj+Uvvr\n" +
                "rQK63L/TYlAXkEscDPQDssYYxFDN4hWoumwGwlHZC/2eqpz5S9vlfNJij5pjToWl\n" +
                "sGyz+4+EMtPuxuERORcdbPI0Phd/rJYMAnk7OOxlJPKcIRi0Jepgj08hcYKEszT6\n" +
                "uxQtA3i6ZrKPNeNGVm0IZicYkTxKb1+cSR2gV+YwgTxGKkh/drZwboUUYAkPGenQ\n" +
                "iJMRKnqP0bNSfIkOl6VPyPFCJ2aOFtkRhdd9cPSAu26iKYQzo9DZvU9qvW72su5F\n" +
                "gfiKeAf6F2F/ADrxJqePmadJz5boo+YFR6HruR1mLCWKvvj5cJ+UkU+XzsTBuFbO\n" +
                "PMig/DBTAgMBAAECggEAQogBUwDlkOxg57oM8V0G9IE0Q0c1P+XrXHLA7NYaLS+/\n" +
                "mWRXp73ePcgRV1Chl6+oRPWCswgwRW2fE8uE6f0NV3+lxUBu1P50A8wQWfCbwa54\n" +
                "kVfcKXPWqxkuJ3XBn8IYJDg4GDqEbYDOm882xjTr0fs+vjcu4upim4SOVrsw/odE\n" +
                "9Lf8Nmac6gmrqF2NB0EZazIDVlJZ2V4qymttrz6QlgvRYyrAJs2dGC26pG1d9eB1\n" +
                "MP/WtJq8BxK/WDwLdfUPlhpdHdUmq/oCSiZuqas1Cy6/GNktc66xel7/zjbVPtD7\n" +
                "Xe/PCb/eZP7am1UVmZ0ROw665nQtzVbY2yPwNPNJFQKBgQDdbeIR77rBCMwbnaR2\n" +
                "OwKhj0HF050HBL+M+pE0R7jMpqd6agoneDEJ9zmCdgVupBf7oXE2H3XIfuLl2vcv\n" +
                "WmGdYo5suGN0XmKYkZxfbBAWNxQ1SfvFZfvF/PoBsvGP+E7DFzZHQqFcOIEnBumb\n" +
                "UZ9m246G1yBh8A16KbsKlyvhRwKBgQDO0sST1W3vZelyf7ngWq/5a81YmKlAlwsW\n" +
                "hsJgDlOlriy2VmXxKnOG30K0Fzb8jHRv7THLrLVlzvMbt9zy4/S1OHKvy8ruLHBN\n" +
                "wrhGAtTKy8ePC+u0hxCBgnxVS5g2ctEamgz+3fo5KyJR8f03Ojry0pDLFRrsaoeT\n" +
                "BT5YR3ZelQKBgFeJ2mcG5EjuJmsAfoUzPQ4L6Y/A4gJlUSj14jPN6vQVKn7Nryf9\n" +
                "bz2mXDkEWY9tFgInx+8CrvjNmPwP/crdq1uiG/ZFSyQ1MDtqzWw394Ag1D9g+UUH\n" +
                "1WpnYZsMWE13eAPOiGnRoKMpv31jUTVTJnZudEFAiQ/O7DUQjF5ad0F/AoGAOJcb\n" +
                "Cog/iF2OPrIMHgMyW0DP8wnyZaXudr9wt++zm4XI3itNwWsoKSM1kSk49mlIAsgX\n" +
                "8+7Rw4WYp2Drp9oPvvYvC0Lrx7eJEv3QhfeIWeTgOZe9aqv6LDWT55LDl0JbTLgS\n" +
                "DjqWmMXgoiNgcZgRQRe0XpzrvPx5FBU/NdB35vECgYBVvLlD0KbFf+cGtR+UmcGN\n" +
                "qUr1FWLKgwtR+fu/wSHmO03z7Nk3ZwnwkDfxmBjT7qu7Sr+Q3lhp0BPCzTDMKTRu\n" +
                "fdY+Qk/dX6cATnQidTTfJLFy9E9SZOa69Czjr7w4WLM0k9/oXq3RJuAiDqTnnzGs\n" +
                "VOyZxHY0H9NXhPIcAlNDTQ==\n" +
                "-----END PRIVATE KEY-----\n");
        byte[] sign = PrivateKeyUtils.sign(privateKey, "SHA256withRSA", "http://test-openapi.basicex.com/v2/getBalance{\"currency\":\"USDT\"}".getBytes());
        String signature = java.util.Base64.getEncoder().encodeToString(sign);

        System.out.println(signature);
    }

    @Test
    public void hmacSignatureTest() throws Exception {
        String secretKey = "my_secret_key";
        String signInput = "https://openapi.basicex.com/v2/invoices{\"t\": \"123\"}";
        String signature = com.basicex.sdk.util.HmacUtils.signHmacSha512(secretKey, signInput);
        System.out.println("HMAC Signature: " + signature);
        org.junit.jupiter.api.Assertions.assertNotNull(signature);
    }
}
