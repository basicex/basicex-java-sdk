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
import com.basicex.sdk.exception.InvalidRequestException;
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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class InvoiceServiceTest extends BaseTest {
    @Test
    public void createEmptyInvoiceTest() throws BasicexException, CertificateException, IOException {
        InvoiceObject obj = createFixedCurrencyInvoice();

        System.out.println(obj.getInvoiceId());
    }

    public void updateInvoiceTest() throws CertificateException, IOException, BasicexException {
        getClient().invoices().update("40620230918161349373246929715463", InvoiceUpdateParams.builder().currency("USDT").build());
    }

    public void concurrentTest() throws CertificateException, IOException, InterruptedException {
        BasicExClient client1 = new BasicExClient("D:\\d57c7885-a1c5-449e-b8c5-9cb1eb1f4518\\config.json");
        BasicExClient client2 = new BasicExClient("D:\\8f0867ba-085f-447c-882a-0684c4f6ded2\\config.json");

        InvoiceCreateParams params1 = InvoiceCreateParams.builder()
                .fiat("USD")
                .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
                .description("Test invoice:" + UUID.randomUUID().toString())
                .buyerIp("127.0.0.1")
                .notificationUrl("https://baidu.com")
                .redirectUrl("https://baidu.com")
                .amountType(AmountType.MONEY_PRICE)
                .amount(BigDecimal.valueOf(1))
                .build();

        InvoiceCreateParams params2 = InvoiceCreateParams.builder()
                .fiat("USD")
                .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
                .description("Test invoice:" + UUID.randomUUID().toString())
                .buyerIp("127.0.0.1")
                .notificationUrl("https://baidu.com")
                .redirectUrl("https://baidu.com")
                .amountType(AmountType.MONEY_PRICE)
                .amount(BigDecimal.valueOf(12.58))
                .build();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
        try {
            executor.execute(() -> {
                try {
                    InvoiceObject invoice = client1.invoices().create(params1);
                    Assertions.assertNotNull(invoice);
                    Assertions.assertNotNull(invoice.getInvoiceId());
                    System.out.println(invoice.getInvoiceId());
                } catch (BasicexException e) {
                    e.printStackTrace();
                }
            });

            executor.execute(() -> {
                try {
                    InvoiceObject invoice = client2.invoices().create(params2);
                    Assertions.assertNotNull(invoice);
                    Assertions.assertNotNull(invoice.getInvoiceId());
                    System.out.println(invoice.getInvoiceId());
                } catch (BasicexException e) {
                    e.printStackTrace();
                }
            });
        } finally {
            Thread.sleep(5000L);
            executor.shutdown();
        }
    }

    public InvoiceObject createFiatCurrencyInvoice() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().create(InvoiceCreateParams.builder()
                .fiat("USD")
                .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
                .description("Test invoice:" + UUID.randomUUID().toString())
                .buyerIp("127.0.0.1")
                .notificationUrl("https://basicex.com")
                .redirectUrl("https://basicex.com")
                .amountType(AmountType.MONEY_PRICE)
                .amount(BigDecimal.valueOf(12))
                .build());

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());

        return invoice;
    }

    private InvoiceObject createFixedCurrencyInvoice() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().create(InvoiceCreateParams.builder()
                // .fiat("USD")
                .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
                .description("Test invoice:" + UUID.randomUUID().toString())
                .buyerIp("127.0.0.1")
                .notificationUrl("https://basicex.com")
                .redirectUrl("https://basicex.com")
                .amountType(AmountType.COIN_AMOUNT)
                .currency("USDT")
                .amount(BigDecimal.valueOf(1))
                .build());

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());

        return invoice;
    }

    @Test
    void getInvoiceTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject o = createFiatCurrencyInvoice();
        InvoiceObject invoice = getClient().invoices().get(o.getInvoiceId());
        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());
    }

    void updateTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject o = createFixedCurrencyInvoice();
        InvoiceObject invoice = getClient().invoices().update(o.getInvoiceId(), InvoiceUpdateParams.builder()
                .payerEmail("test@basicex.com").build());

        Assertions.assertNotNull(invoice);
        Assertions.assertEquals(invoice.getPayerEmail(), "test@basicex.com");

        invoice = getClient().invoices().update(o.getInvoiceId(), InvoiceUpdateParams.builder()
                .payerEmail("test1@basicex.com")
                .build());
        Assertions.assertNotNull(invoice);
        Assertions.assertEquals(invoice.getPayerEmail(), "test1@basicex.com");

        InvoiceObject finalO = o;
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            getClient().invoices().update(finalO.getInvoiceId(), InvoiceUpdateParams.builder()
                    .currency("USDT")
                    .build());
        });

        o = createFiatCurrencyInvoice();
        invoice = getClient().invoices().update(o.getInvoiceId(), InvoiceUpdateParams.builder()
                .currency("USDT")
                .chain(ChainNetwork.TRC20)
                .build());
        Assertions.assertNotNull(invoice);
        Assertions.assertTrue(invoice.getPaymentInfo().getAllowChainPayment());
        Assertions.assertEquals(invoice.getPaymentInfo().getNetwork().getNetwork(), ChainNetwork.TRC20.getCode());
        Assertions.assertEquals(invoice.getCurrency(), "USDT");

        Assertions.assertNotNull(invoice.getPaymentInfo().getPayeeAddress());
        Assertions.assertTrue(invoice.getPaymentInfo().getPayeeAddress().startsWith("T"));

        invoice = getClient().invoices().update(o.getInvoiceId(), InvoiceUpdateParams.builder()
                .currency("USDT")
                .chain(ChainNetwork.ERC20)
                .build());

        Assertions.assertNotNull(invoice);
        Assertions.assertTrue(invoice.getPaymentInfo().getAllowChainPayment());
        Assertions.assertEquals(invoice.getPaymentInfo().getNetwork().getNetwork(), ChainNetwork.ERC20.getCode());
        Assertions.assertEquals(invoice.getCurrency(), "USDT");

        Assertions.assertNotNull(invoice.getPaymentInfo().getPayeeAddress());
        Assertions.assertTrue(invoice.getPaymentInfo().getPayeeAddress().startsWith("0x"));
    }


    void invoiceTest1() throws CertificateException, IOException, BasicexException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("a", "1");
        metadata.put("b", "2");
        InvoiceObject invoice = getClient().invoices().create(InvoiceCreateParams.builder()
                .amount(BigDecimal.TEN)
                .amountType(AmountType.COIN_AMOUNT)
                .description("Test:" + UUID.randomUUID().toString().replaceAll("-", ""))
                .fiat("USD")
                .currency("USDT")
                .forcedChain(ChainNetwork.TRC20)
                .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
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
                    .currency("BCNY")
                    .build());
        });

        InvoiceObject invoice2 = getClient().invoices().update(invoice.getInvoiceId(), InvoiceUpdateParams.builder()
                        .payerEmail("test1@test.com")
                .build());
        Assertions.assertEquals(invoice2.getPayerEmail(), "test1@test.com");
    }
}
