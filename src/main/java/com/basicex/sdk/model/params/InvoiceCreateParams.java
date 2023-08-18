package com.basicex.sdk.model.params;


import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import java.math.BigInteger;

/**
 * 票据创建参数
 */
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
    private String forcedChain;

    /**
     * 金额字段，该字段为整数格式。即如果用户支付$2.00.则这里传入200
     * 该字段根据`amountType`字段判断传入的是法币金额还是数字货币金额
     */
    private BigInteger amount;

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
     * 金额类型字段，表示传入的`amount`是法币金额还是币种数量。
     * `money_price` 法币金额
     * `coin_amount` 币种数量
     */
    private String amountType;


}
