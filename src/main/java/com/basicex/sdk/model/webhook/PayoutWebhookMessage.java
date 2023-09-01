package com.basicex.sdk.model.webhook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 代付Webhook消息
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayoutWebhookMessage implements Serializable {
	/**
	 * 代付系统订单号
	 */
	private String orderNo;

	/**
	 * 商户订单号
	 */
	private String merOrderNo;

	/**
	 * 数字货币
	 */
	private String currency;

	/**
	 * 数字货币金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 代付开始时间
	 */
	private Long tradeStartTime;

	/**
	 * 链支付信息
	 */
	private PayoutChainPaymentInfo chainPaymentInfo;

	/**
	 * 如果代付失败了 返回失败原因
	 */
	private String message;

	/**
	 * 代付订单状态
	 */
	private String status;

}
