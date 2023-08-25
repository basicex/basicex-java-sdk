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
    public void createPayoutTest() throws CertificateException, IOException, BasicexException {
        PayoutService payout = getClient().payout();
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhimaDADA");
        map.put("money", "200000000");
        map.put("age", "24");

        PayoutCreateParams params = PayoutCreateParams.builder()
                .amount(new BigDecimal(5))
                .amountType(AmountType.MONEY_PRICE)
                .fiat("USD")
                .currency("BTC")
                .customerEmail("3021494730@qq.com")
                .notificationUrl("https://192.168.31.125:7013/notify/test")
                .description("TEST PAYOUT SDK")
                .metadata(map)
                .source("APP")
                .customerIp("192.168.31.125")
                .physical(Boolean.TRUE)
                .targetType("ADDRESS")
                .target("TLeozhe6ozXW88vKnvkfF3Xmo9JrnbNfpZ")
                .netWork(NetWorkType.ERC20.code)
                .merOrderNo(UUID.randomUUID().toString().replace("-", ""))
                .build();
        PayoutObject payoutObject = payout.create(params);
        System.out.println(payoutObject);

    }
}
