package com.basicex.sdk.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Map;

/**
 * 支付票据创建请求
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayoutCreateRequest {
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
     * ISO 4217 3位字符的法币代码，这个法币代码将在收银台或其余地方展示价格字段。
     */
    private String fiat;

    /**
     * 支付的区块链币种，例如: USDT, BTC等
     */
    private String currency;

    /**
     * 商户侧的客户邮箱，如果传入，将在票据支付成功后向该邮箱发送邮件
     */
    private String customerEmail;

    /**
     * 用于BasicEx向商户指定地址发送Webhook通知的网址，必须`HTTPS`
     */
    private String notificationUrl;

    /**
     * 该票据的描述，该描述将展示在收银台页面
     */
    private String description;

    /**
     * 传入的结构化元数据，用于商户本身记录相应的数据。该字段将在后续原样返回
     */
    private Map<String, String> metadata;

    /**
     * 支付来源
     */
    private String source;

    /**
     * 商户侧客户端IP
     */
    private String customerIp;

    /**
     * 是否发送已支付状态的通知数据。默认为false
     * 如果设置为true，当我们在区块链上收到用户的款项且有一个块确认后。我们将发送`paid`状态通知
     * 基于区块链特性，这并不意味着这笔订单完全有效。我们内部将会有一个确认机制，当我们确认该笔
     * 交易完全不可逆后，将发送`complete`状态通知，`paid`状态转`complete`状态可能需要一些时间。
     */
    private Boolean sendPaidNotification = false;

    /**
     * 是否是实物商品，而非虚拟商品或服务
     */
    private Boolean physical = false;

    /**
     * 代付对象类型 可传EMAIL、TELEPHONE、BEID、USERID、ADDRESS
     */
    private String targetType;

    /**
     * 代付对象（email/手机号/币趣id/用户名|地址）
     */
    private String target;

    /**
     * 商户订单号
     */
    private String merOrderNo;

    /**
     * 网络 当为外链的时候必填
     */
    private String netWork;
}
