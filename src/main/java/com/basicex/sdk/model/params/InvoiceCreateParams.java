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


import com.basicex.sdk.model.params.constant.AmountType;
import com.basicex.sdk.model.params.constant.ChainNetwork;
import com.basicex.sdk.util.Preconditions;
import com.basicex.sdk.util.StringUtils;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 票据创建参数
 */
@Builder(toBuilder = true)
@Getter
public class InvoiceCreateParams {
    /**
     * 商户侧客户识别编号，用于支付该票据的商户侧客户ID
     */
    private String buyerId;

    /**
     * 商户内部订单编号，该订单编号在同一个商户内唯一。
     */
    private String orderId;

    /**
     * ISO 4217 3位字符的法币代码，这个法币代码将在收银台或其余地方展示价格字段。
     */
    private String fiat;

    /**
     *
     * 支付的区块链币种，例如: USDT, BTC等
     */
    private String currency;

    /**
     * 强制选择指定的链，例如: TRC20, ERC20等。当该参数传入时，用户只能在此链下进行支付，且不能使用币趣钱包支付。
     */
    private ChainNetwork forcedChain;

    /**
     * 支付金额，该支付金额将根据`amount_type`字段进行判断。
     * 如果`amount_type`字段为`money_price`，则该字段为法币金额，例如: 2.00 USD
     * 如果`amount_type`字段为`coin_amount`，则该字段为币种数量，例如: 2.00 USDT
     *
     */
    private BigDecimal amount;

    /**
     * 金额类型字段，表示传入的`amount`是法币金额还是币种数量。
     * `money_price` 法币金额
     * `coin_amount` 币种数量
     */
    private AmountType amountType;

    /**
     * 该票据的描述，该描述将展示在收银台页面
     */
    private String description;

    /**
     * 传入的结构化元数据，用于商户本身记录相应的数据。该字段将在后续原样返回
     */
    private Map<String, String> metadata;

    /**
     * 商户侧的客户邮箱，如果传入，将在票据支付成功后向该邮箱发送邮件
     */
    private String payerEmail;

    /**
     * 用于BasicEx向商户指定地址发送Webhook通知的网址，必须`HTTPS`
     */
    private String notificationUrl;

    /**
     * 用于用户在收银台支付成功后，跳转至此地址。该地址必须以: `https`或`http`开头
     */
    private String redirectUrl;

    /**
     * 商户侧客户端IP
     */
    private String buyerIp;

    /**
     * 是否发送已支付状态的通知数据。默认为false
     *
     * 如果设置为true，当我们在区块链上收到用户的款项且有一个块确认后。我们将发送`paid`状态通知
     * 基于区块链特性，这并不意味着这笔订单完全有效。我们内部将会有一个确认机制，当我们确认该笔
     * 交易完全不可逆后，将发送`complete`状态通知，`paid`状态转`complete`状态可能需要一些时间。
     */
    @Builder.Default
    private Boolean sendPaidNotification = false;

    /**
     * 是否是实物商品，而非虚拟商品或服务
     */
    @Builder.Default
    private Boolean physical = false;

    public void checkParams() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.fiat), "fiat is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.orderId), "orderId is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.description), "description is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.notificationUrl), "notificationUrl is required");

        if(this.amountType != null) {
            Preconditions.checkArgument(this.amount != null, "amount is required");

             if(this.amountType.equals(AmountType.COIN_AMOUNT) && StringUtils.isEmpty(this.currency)) {
                throw new IllegalArgumentException("currency is required");
             }
        }

        if(this.forcedChain != null) {
            Preconditions.checkArgument(StringUtils.isNotEmpty(this.currency), "currency is required");
            Preconditions.checkArgument(this.amount != null, "amount is required");
            Preconditions.checkArgument(this.amountType != null, "amountType is required");
        }
    }
}
