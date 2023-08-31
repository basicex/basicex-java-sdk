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

import com.basicex.sdk.BasicExClient;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.params.constant.AmountType;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.UUID;

public class BasicExTest{
    private static String configPath = "~/4d1ebd88-8154-4ca1-b1c6-051b7d28c204/config.json";
    public static void main(String[] args) throws CertificateException, IOException, BasicexException {
        BasicExClient client = new BasicExClient(configPath);
        InvoiceObject invoiceObject = client.invoices().create(InvoiceCreateParams.builder()
                .redirectUrl("https://basicex.com")
                .notificationUrl("https://basicex.com/notify")
                .fiat("USD")
                .amount(new BigDecimal("10.25"))
                .amountType(AmountType.MONEY_PRICE)
                .orderId(UUID.randomUUID().toString())
                .description("Hello, BasicEx")
                .build());

        String cashierUrl = invoiceObject.getCashierUrl();
        System.out.println(cashierUrl);
    }
}
