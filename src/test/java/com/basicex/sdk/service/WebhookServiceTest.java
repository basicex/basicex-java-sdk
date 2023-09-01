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
        String requestBody = "{\"id\":\"b634bd51-8e11-45a1-a520-e67ab30d90c7\",\"objectId\":\"40620230901142435564818874368171\",\"object\":\"event\",\"created\":\"1693549562828\",\"type\":\"invoice.completed\",\"data\":{\"invoiceId\":\"40620230901142435564818874368171\",\"merOrderId\":\"caf7568b141b4d2b8bd7cff0c60b512d\",\"fiat\":\"USD\",\"currency\":\"USDT\",\"fiatAmount\":\"14.89\",\"paidAmount\":\"14.891348\",\"totalAmount\":\"14.891348\",\"currencyExchange\":\"0.99990947\",\"tradeTime\":\"1693549562825\",\"channel\":\"chain_pay\",\"metadata\":null,\"chainPaymentInfo\":{\"network\":\"TRC20\",\"payeeAddress\":\"TXuBpGFEKQ64XQDRjMq4X7dwrtW4vN6VCX\",\"transactionId\":\"f122bf71a4e486ae0763426f99b8555da31f181ec5414827153bfb7b27dfc437\",\"blockHash\":\"00000000025f115d1c9662cd65bf8030717fde35a2214ad930cd6dc5abffc4d1\",\"blockHeight\":\"39784797\",\"packageTime\":\"1693549559497\"}},\"retriesNum\":0}";
        String signature = "MmwS064fan/NWm5pj49nugVYl1hNX7ayzK8gOLwT9DnInxP/kkxQQzivrCey4tYCP0bv6Snhn8fkEe6r965oHtRYMAI4DmxOKjRZWgZHhArGxU+kXfIJq6BP0A0xQP1BhtYWkC9S/rc7q4Qyquv90eWGVeuTowYho8V9wA7kN+p5/IwEFUzpH9JyTwx2uad+nnzsB9dzVSjz+434IF0X8qk2za3BlUS0mDVESOvLTJBRz7ZdihzNHZG5S2FA8yD83ClOsRiX1imXzjb7q7DgcKgGEdbw2Up2CVJDwnDd5zG9cc5cwIEJwJvxypVdcbFNDni0oStGEVwwVCAfrxHSpg==";
        String serialNo = "3d:47:36:3a:64:9c:15:ec:60:c3:ed:e5:71:89:08:52:5b:09:fb:f1";
        String notificationUrl = "https://baidu.com";
        WebhookEvent event = getClient().webhook().validate(requestBody, notificationUrl, signature, serialNo);
        Assertions.assertNotNull(event);
        Assertions.assertEquals("invoice.completed", event.getType());
        Assertions.assertTrue(event.getData() instanceof InvoiceCompletedWebhookMessage);
    }
}
