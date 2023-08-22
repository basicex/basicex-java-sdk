package com.basicex.sdk.service;


import com.basicex.sdk.BaseTest;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.PayoutObject;
import com.basicex.sdk.model.RefundObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.params.PayoutCreateParams;
import com.basicex.sdk.model.params.RefundCreateParams;
import com.basicex.sdk.model.params.constant.AmountType;
import com.basicex.sdk.model.params.constant.NetWorkType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RefundServiceTest extends BaseTest {

    @Test
    public void createPayoutTest() throws CertificateException, IOException, BasicexException {
        RefundService refund = getClient().refund();

        RefundCreateParams params = RefundCreateParams.builder()
                .amount(new BigDecimal(1))
                .amountType(AmountType.COIN_AMOUNT.getCode())
                .merOrderNo("bAtbd4nCHSRtye7t")
                .orderNo("40620230817112147382900585267402")
                .merRefundOrderNo(UUID.randomUUID().toString().replace("-", ""))
                .refundReason("TEST REFUND SDK")
                .tradeStartTime("2023-08-22 15:28:43")
                .customerEmail("3021494730@qq.com").build();

        RefundObject refundObject = refund.create(params);
        System.out.println(refundObject);

    }
}
