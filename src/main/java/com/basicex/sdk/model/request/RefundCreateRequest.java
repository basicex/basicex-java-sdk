/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 支付票据创建请求
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundCreateRequest {
    /**
     * 金额字段，该字段为整数格式。即如果用户支付$2.00.则这里传入200
     * 该字段根据`amountType`字段判断传入的是法币金额还是数字货币金额
     */
    private BigInteger amount;

    /**
     * 转换过后的金额
     */
    private BigDecimal conversion;

    /**
     * 金额精度。如果传入了，则`amount`字段根据该精度进行处理
     * 例如: `precision`为2，则`amount`传入200表示: 2.00
     * 如果`amountType`为`money_price`，则`precision`不能大于2.
     * 如果`amountType`为`coin_amount`，则`precision` 不能大于币种精度
     * 如果不传，则:
     * > 1. `amountType`为`money_price`，默认`precision` 为2
     * > 2. `amountType`为`coin_amount`，默认`precision` 为所选币种精度
     */
    private Integer precision;

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

}
