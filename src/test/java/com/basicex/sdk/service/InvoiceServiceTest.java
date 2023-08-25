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
                .orderId(UUID.randomUUID().toString())
                .description("Test invoice:" + UUID.randomUUID().toString())
                .buyerIp("127.0.0.1")
                .notificationUrl("https://google.com")
                .amountType(AmountType.MONEY_PRICE)
                .amount(BigDecimal.valueOf(1000000))
                .build());

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());
    }

    @Test
    void getInvoiceTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().get("40620230822134552202883210445009");
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
        Assertions.assertEquals(invoice.getPaymentInfo().getAllowPayment(), true);
        Assertions.assertNotNull(invoice.getPaymentInfo().getRecipientAddress());
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
