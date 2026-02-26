/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.service;

import com.basicex.sdk.BaseTest;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.webhook.InvoiceCompletedWebhookMessage;
import com.basicex.sdk.model.webhook.InvoicePartialCompletedWebhookMessage;
import com.basicex.sdk.model.webhook.WebhookEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.cert.CertificateException;

/**
 * WebhookServiceTest
 */
public class WebhookServiceTest extends BaseTest {

    @Test
    public void testWebhook() throws CertificateException, IOException, BasicexException {
        String requestBody = "{\"id\":\"0e9ab3a4-e15d-404e-bed8-f5e0745ce411\",\"objectId\":\"40620260209151154753501394125045\",\"object\":\"event\",\"created\":\"1770621716791\",\"type\":\"invoice.partial_completed\",\"data\":{\"invoiceId\":\"40620260209151154753501394125045\",\"merOrderId\":\"3453454654428\",\"fiat\":\"\",\"currency\":\"USDT\",\"fiatAmount\":\"0.00\",\"paidAmount\":\"44.000000\",\"totalAmount\":\"50.000000\",\"currencyExchange\":null,\"tradeTime\":\"1770621716791\",\"channel\":\"chain_pay\",\"metadata\":{},\"chainPaymentInfo\":{\"network\":\"TRC20\",\"payeeAddress\":\"TPpsBWRYfhUaBDmCcv2zViCVDiUvpz8pyM\",\"payerAddress\":\"TBwcXyXYMpjGfdWw31LfS3UCREoREwkEN5\",\"transactionId\":\"224f241e1ed3a10e5f3c7704bd7bbac7f949dafbfdfb53d2ba558ddd61577d33\",\"blockHash\":null,\"blockHeight\":null,\"packageTime\":null}},\"retriesNum\":8}";
        String signature = "9bd0085ced5f622780078137d13def3c3751105cc86f58effe08ce644825acd3994501b45fc8d8ff48a61536dea02a107e2354d27b3be242d8c71b578beaf7c1";
        // String serialNo = "3d:47:36:3a:64:9c:15:ec:60:c3:ed:e5:71:89:08:52:5b:09:fb:f1";
        String notificationUrl = "https://google.com";
        String signType = "key";
        WebhookEvent event = getClient().webhook().validate(requestBody, notificationUrl, signature, signType);
        Assertions.assertNotNull(event);
        Assertions.assertEquals("invoice.partial_completed", event.getType());
        Assertions.assertTrue(event.getData() instanceof InvoicePartialCompletedWebhookMessage);
    }

    @Test
    public void testWebhookApiKey() throws CertificateException, IOException, BasicexException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        String notificationUrl = "https://basicex.com/webhook";
        String secretKey = "test_secret_key";
        String requestBody = "{\"id\":\"111\",\"object\":\"event\",\"type\":\"invoice.partial_completed\",\"data\":{\"invoiceId\":\"222\",\"paidAmount\":\"1.5\"}}";
        
        String signInput = notificationUrl + requestBody;
        String signature = com.basicex.sdk.util.HmacUtils.signHmacSha512(secretKey, signInput);

        com.basicex.sdk.BasicExConfig config = com.basicex.sdk.BasicExConfig.builder()
                .apiKey("test_api_key")
                .secretKey(secretKey)
                .build();
        com.basicex.sdk.BasicExClient client = new com.basicex.sdk.BasicExClient(config);

        WebhookEvent event = client.webhook().validate(requestBody, notificationUrl, signature, null, "key");
        
        Assertions.assertNotNull(event);
        Assertions.assertEquals("invoice.partial_completed", event.getType());
        Assertions.assertTrue(event.getData() instanceof com.basicex.sdk.model.webhook.InvoicePartialCompletedWebhookMessage);
    }
}
