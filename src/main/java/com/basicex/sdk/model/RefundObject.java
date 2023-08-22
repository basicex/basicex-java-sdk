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
