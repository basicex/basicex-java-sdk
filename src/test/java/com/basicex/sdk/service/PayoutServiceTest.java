package com.basicex.sdk.service;


import com.basicex.sdk.BaseTest;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.PayoutObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.params.PayoutCreateParams;
import com.basicex.sdk.model.params.constant.AmountType;
import com.basicex.sdk.model.params.constant.NetWorkType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PayoutServiceTest extends BaseTest {

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
    }

    @Test
    public void createPayoutTest() throws CertificateException, IOException, BasicexException {
        PayoutService payout = getClient().payout();
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhimaDADA");
        map.put("money", "200000000");
        map.put("age", "24");

        PayoutCreateParams params = PayoutCreateParams.builder()
                .amount(new BigDecimal(10))
                .amountType(AmountType.COIN_AMOUNT)
                .coinPrecision(6)
                .fiat("USD")
                .currency("USDT")
                .customerEmail("3021494730@qq.com")
                .notificationUrl("https://www.google.com")
                .description("TEST PAYOUT SDK")
                .metadata(map)
                .source("APP")
                .customerIp("192.168.31.125")
                .physical(Boolean.TRUE)
                .targetType("ADDRESS")
                .target("0x682D39Ea8d26510BE379d30807AF61e5eF9E269b")
                .netWork(NetWorkType.ERC20.code)
                .merOrderNo(UUID.randomUUID().toString().replace("-", ""))
                .build();
        PayoutObject payoutObject = payout.create(params);
        System.out.println(payoutObject);

    }
}
