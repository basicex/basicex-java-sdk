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
import com.basicex.sdk.BasicExClient;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.params.InvoiceUpdateParams;
import com.basicex.sdk.model.params.constant.AmountType;
import com.basicex.sdk.model.params.constant.ChainNetwork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InvoiceServiceTest extends BaseTest {

    @Test
    void createEmptyInvoiceTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().create(InvoiceCreateParams.builder()
               .fiat("USD")
                .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
                .description("Test invoice:" + UUID.randomUUID().toString())
                .buyerIp("127.0.0.1")
                .notificationUrl("https://baidu.com")
                .redirectUrl("https://baidu.com")
                .amountType(AmountType.COIN_AMOUNT)
                .currency("USDT")
                //.forcedChain(ChainNetwork.TRC20)
                .amount(BigDecimal.valueOf(12.1))
                .build());

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());

        System.out.println(invoice.getInvoiceId());
    }

    @Test
    void getInvoiceTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().get("40620230831171234553743040512291");
        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());
    }

    @Test
    void updateInvoiceFiatAmountTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().update("40620230822134552202883210445009", InvoiceUpdateParams.builder()
                .amount(BigDecimal.valueOf(10.23))
                .amountType(AmountType.MONEY_PRICE).build());

        Assertions.assertNotNull(invoice);
    }

    @Test
    void updateInvoiceCurrencyTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().update("40620230822134552202883210445009", InvoiceUpdateParams.builder()
                .chain(ChainNetwork.TRC20).build());

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());
    }

    @Test
    void invoiceTest1() throws CertificateException, IOException, BasicexException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("a", "1");
        metadata.put("b", "2");
        InvoiceObject invoice = getClient().invoices().create(InvoiceCreateParams.builder()
                .amount(BigDecimal.TEN)
                .amountType(AmountType.COIN_AMOUNT)
                .description("Test:" + UUID.randomUUID().toString())
                .fiat("USD")
                .currency("USDT")
                .forcedChain(ChainNetwork.TRC20)
                .orderId(UUID.randomUUID().toString())
                .payerEmail("test@test.com")
                .metadata(metadata)
                .notificationUrl("https://google.com")
                .build());

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());
        Assertions.assertEquals(invoice.getPayerEmail(), "test@test.com");
        Assertions.assertNotNull(invoice.getMetadata());
        Assertions.assertEquals(invoice.getMetadata().size(), 2);
        Assertions.assertNotNull(invoice.getPaymentInfo());
        Assertions.assertEquals(invoice.getPaymentInfo().getAllowChainPayment(), true);
        Assertions.assertNotNull(invoice.getPaymentInfo().getPayeeAddress());
        Assertions.assertEquals(invoice.getPaymentInfo().getTotalAmount().compareTo(BigDecimal.TEN), 0);

        Assertions.assertThrows(BasicexException.class, () -> {
            getClient().invoices().update(invoice.getInvoiceId(), InvoiceUpdateParams.builder()
                    .amount(BigDecimal.valueOf(10.23))
                    .amountType(AmountType.MONEY_PRICE).build());

            getClient().invoices().update(invoice.getInvoiceId(), InvoiceUpdateParams.builder()
                    .currency("BCNY")
                    .build());
        });

        InvoiceObject invoice2 = getClient().invoices().update(invoice.getInvoiceId(), InvoiceUpdateParams.builder()
                        .payerEmail("test1@test.com")
                .build());
        Assertions.assertEquals(invoice2.getPayerEmail(), "test1@test.com");
    }
}
