/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoicePaymentInfo {
    /**
     * 是否允许支付
     */
    private Boolean allowPayment;

    /**
     * 接收方地址
     */
    private String recipientAddress;

    /**
     * 网络信息
     */
    private CoinChainInfo network;

    /**
     * 总计支付的金额
     */
    private BigDecimal totalAmount;

    /**
     * 已支付的金额
     */
    private BigDecimal paidAmount;

    /**
     * 收款地址过期时间，单位毫秒，需要在过期时间前完成支付或者重新刷新收款地址
     */
    private Long recipientAddressExpireTime;
}
