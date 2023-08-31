/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.model.params;


import com.basicex.sdk.util.Preconditions;
import com.basicex.sdk.util.StringUtils;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 代付票据创建参数
 */
@Getter
@Builder(toBuilder = true)
public class RefundCreateParams {

    /**
     * 代付金额，该支付金额将根据`amount_type`字段进行判断。
     * 如果`amount_type`字段为`money_price`，则该字段为法币金额，例如: 2.00 USD
     * 如果`amount_type`字段为`coin_amount`，则该字段为币种数量，例如: 2.00 USDT
     */
    private BigDecimal amount;

    /**
     * 币种精度 不能大于6 如果不传默认为6
     */
    private Integer coinPrecision;

    /**
     * 金额类型字段，表示传入的`amount`是法币金额还是币种数量。
     * `money_price` 法币金额
     * `coin_amount` 币种数量
     */
    private String amountType;

    /**
     * 原商户订单号
     */
    private String merOrderNo;

    /**
     * 原系统订单号
     */
    private String orderNo;

    /**
     * 商户退款订单号
     */
    private String merRefundOrderNo;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 交易发起时间，单位为分 最长1小时
     */
    private String tradeStartTime;

    /**
     * 客户邮箱号
     */
    private String customerEmail;


    public void checkParams() {
        Preconditions.checkArgument(this.amount != null, "amount is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.amountType), "amountType is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.merOrderNo) && StringUtils.isNotEmpty(this.orderNo),
                "Merchant Order Number System order number cannot be empty at the same time");

        Preconditions.checkArgument(StringUtils.isNotEmpty(this.merRefundOrderNo), "merRefundOrderNo is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.refundReason), "refundReason is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.tradeStartTime), "tradeStartTime is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.customerEmail), "customerEmail is required");
    }
}
