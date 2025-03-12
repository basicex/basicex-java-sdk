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
import com.basicex.sdk.model.PayoutObject;
import com.basicex.sdk.model.params.PayoutCreateParams;
import com.basicex.sdk.model.params.constant.NetWorkType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PayoutServiceTest extends BaseTest {

    @Test
    void createPayoutTest() throws CertificateException, IOException, BasicexException {
        PayoutService payout = getClient().payouts();
        Map<String, String> map = new HashMap<>();
        map.put("desc", "Hello,BasicEx");

        PayoutCreateParams params = PayoutCreateParams.builder()
                .amount(new BigDecimal(24))
                .currency("USDT")
                .notificationUrl("https://basicex.com/notify")
                .description("TEST PAYOUT SDK")
                .metadata(map)
                .physical(Boolean.TRUE)
                .targetType("ADDRESS")
                .target("0x682D39Ea8d26510BE379d30807AF61e5eF9E269b")
                .network(NetWorkType.ERC20.code)
                .merOrderNo(UUID.randomUUID().toString().replace("-", ""))
                .build();
        PayoutObject payoutObject = payout.create(params);
        System.out.println(payoutObject);
    }
}
