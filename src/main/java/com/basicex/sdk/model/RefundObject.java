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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class RefundObject extends BasicexObject {

    /**
     * 原交易系统订单号
     */
    private String orderNo;

    /**
     * 原交易商户订单号
     */
    private String merOrderNo;

    /**
     * 商户退款订单号
     */
    private String merRefundOrderNo;

    /**
     * 系统退款订单号
     */
    private String refundOrderNo;

    /**
     * 退款订单金额，
     */
    private BigDecimal refundAmount;

    /**
     * 退款状态  0 退款中 1 审批中 2 退款成功  3 退款失败
     */
    private Integer refundStatus;

    /**
     * 消息
     */
    private String message;

}
