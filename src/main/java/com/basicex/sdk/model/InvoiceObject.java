package com.basicex.sdk.model;

import com.basicex.sdk.model.request.FiatCurrencyInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class InvoiceObject extends BasicexObject {
    /**
     * 支付票据ID
     */
    private String invoiceId;

    /**
     * 商户侧商户内部订单编号
     */
    private String merOrderId;

    /**
     * ISO 4217 3位字符的法币代码
     */
    private String fiat;

    /**
     * 如果支付票据订单限制了支付币种或选择了支付币种，则返回该字段
     */
    private String currency;

    /**
     * 如果支付票据订单限制了支付币种或选择了支付币种，则返回该字段
     */
    private CoinInfo currencyInfo;

    /**
     * 如果支付票据订单限制了支付网络，或选择了支付网络，则返回该字段
     */
    private String forcedChain;

    /**
     * 该支付票据折算的法币金额或要求支付的法币金额
     */
    private BigDecimal fiatAmount;

    /**
     * 法币信息
     */
    private FiatCurrencyInfo fiatInfo;

    /**
     * 订单票据描述
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
     * 商户名称
     */
    private String merchantName;


    /**
     * 支持的数字货币币种信息列表
     */
    private List<CoinInfo> currencies;

    /**
     * 支付信息
     */
    private InvoicePaymentInfo paymentInfo;

    /**
     * 汇率信息
     */
    private InvoiceExchangeRate exchangeRate;

    /**
     * 支付票据订单类型
     */
    private Integer type;
}
