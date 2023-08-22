package com.basicex.sdk.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InvoiceUpdateRequest {
    /**
     *
     * 支付的区块链币种，例如: USDT, BTC等
     */
    private String currency;

    /**
     * 选择指定的链，例如: TRC20, ERC20等，当传入此参数时，currency为必传参数
     */
    private String chain;

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

    /**
     * 商户侧的客户邮箱，如果传入，将在票据支付成功后向该邮箱发送邮件
     */
    private String payerEmail;

    /**
     * 用于用户在收银台支付成功后，跳转至此地址。该地址必须以: `https`或`http`开头
     */
    private String redirectUrl;

    /**
     * 忽略参数错误返回，当该参数为true时，将忽略掉参数错误
     * 例如：当创建支付票据订单时指定了强制传入链。并在更新支付票据订单时传入了金额，链，币种等参数时
     * 如果该参数设置为true，则将会返回错误。如果设置为false，则忽略掉这些参数，并更新支付票据订单
     */
    private Boolean ignoreParameterFailed;
}
