package com.basicex.sdk.model.webhook;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付票据已支付Webhook消息
 */
@Data
public class InvoicePaidWebhookMessage implements  Serializable {
	/**
	 * 票据ID
	 */
	private String invoiceId;

	/**
	 * 商户订单号
	 */
	private String merOrderId;

	/**
	 * 法定货币
	 */
	private String fiat;

	/**
	 * 数字货币
	 */
	private String currency;

	/**
	 * 法币金额
	 */
	private BigDecimal fiatAmount;

	/**
	 * 已付数字货币金额
	 */
	private BigDecimal paidAmount;

	/**
	 * 票据数字货币金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 数字货币汇率
	 */
	private BigDecimal currencyExchange;

	/**
	 * 支付时间
	 */
	private Long tradeTime;

	/**
	 * 支付渠道
	 */
	private String channel;

	/**
	 * 传入的结构化元数据，用于商户本身记录相应的数据。该字段将在后续原样返回
	 */
	private Map<String, String> metadata;

	/**
	 * 链支付信息
	 */
	private InvoiceChainPaymentInfo chainPaymentInfo;
}
