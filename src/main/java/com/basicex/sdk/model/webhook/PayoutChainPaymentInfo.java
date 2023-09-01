package com.basicex.sdk.model.webhook;

import lombok.*;

import java.io.Serializable;

/**
 * 代付链打款信息
 */
@Data
public class PayoutChainPaymentInfo implements Serializable {
	/**
	 * 支付网络
	 */
	private String network;

	/**
	 * 支付收款地址
	 */
	private String payeeAddress;

	/**
	 * 链交易ID
	 */
	private String transactionId;

	/**
	 * 打包区块Hash
	 */
	private String blockHash;

	/**
	 * 打包区块高度
	 */
	private Long blockHeight;

	/**
	 * 打包时间
	 */
	private Long packageTime;
}
