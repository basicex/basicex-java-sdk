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
