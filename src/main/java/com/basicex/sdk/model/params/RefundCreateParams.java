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
