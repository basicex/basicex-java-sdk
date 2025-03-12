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
        String requestBody = "{\"id\":\"2a94f3ab-5baf-4a7d-86f8-805972a69860\",\"objectId\":\"40620231008235252701382549094684\",\"object\":\"event\",\"created\":\"1696780395439\",\"type\":\"invoice.completed\",\"data\":{\"invoiceId\":\"40620231008235252701382549094684\",\"merOrderId\":\"6b2bc44b2d4543f287c5021deaf65770\",\"fiat\":\"\",\"currency\":\"USDT\",\"fiatAmount\":\"0.00\",\"paidAmount\":\"2.000000\",\"totalAmount\":\"2.000000\",\"currencyExchange\":null,\"tradeTime\":\"1696780395437\",\"channel\":\"chain_pay\",\"metadata\":null,\"chainPaymentInfo\":{\"network\":null,\"payeeAddress\":null,\"transactionId\":\"123456\",\"blockHash\":\"\",\"blockHeight\":\"0\",\"packageTime\":\"1696780395241\"}},\"retriesNum\":0}";
        String signature = "aSp76ViiQcR4Zbz3OsCeRrJTZ34IaoB1TkAaAuNjWOooLq69mB78K/bEEOiEUBx8yTpksmtWwovtmKVdzUZlIozkdByxB4HB3glwiaI69/hmUbN9uworpM2WN9ZUDlhAyXEQyUnci5We2ssndhmQFujY7SmRb8+6yCmKZqEzNT6Ditxb3X3G7N20vBUnuvWFlgXaDo45CSlLfqFU9B3fAFAJGwvvfWi2AoQ7OxK2Eml9Sroei/Ex1N07UuO1kqZDi4S3g58Nh8vv18UzxrXzhyd9fjPO7xU1WLK/RBlRgCDa8SOad4NxGTy6imo+70iNUlEFrMEVZYKofCBBcsGwYQ==";
        String serialNo = "3d:47:36:3a:64:9c:15:ec:60:c3:ed:e5:71:89:08:52:5b:09:fb:f1";
        String notificationUrl = "https://basicex.com";
        WebhookEvent event = getClient().webhook().validate(requestBody, notificationUrl, signature, serialNo);
        Assertions.assertNotNull(event);
        Assertions.assertEquals("invoice.completed", event.getType());
        Assertions.assertTrue(event.getData() instanceof InvoiceCompletedWebhookMessage);
    }
}
