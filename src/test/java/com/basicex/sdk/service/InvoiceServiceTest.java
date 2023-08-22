package com.basicex.sdk.service;


import com.basicex.sdk.BaseTest;
import com.basicex.sdk.BasicExClient;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.cert.CertificateException;
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
                .build());

        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());
    }

    @Test
    void getInvoiceTest() throws CertificateException, IOException, BasicexException {
        InvoiceObject invoice = getClient().invoices().get("40620230822110512159365430886561");
        Assertions.assertNotNull(invoice);
        Assertions.assertNotNull(invoice.getInvoiceId());
    }
}
