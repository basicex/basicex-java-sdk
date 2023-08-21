package com.basicex.sdk.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class PayoutObject extends BasicexObject {

    /**
     * 系统订单号
     */
    private String orderNo;

    /**
     * 商户订单号
     */
    private String merOrderNo;

    /**
     * 金额字段，该字段为整数格式。即如果用户支付$2.00.则这里传入200
     * 该字段根据`amountType`字段判断传入的是法币金额还是数字货币金额
     */
    private BigInteger amount;

    /**
     * 平台计算出来的币种数量
     */
    private BigDecimal conversion;

    /**
     * 币种
     */
    private String currency;

    /**
     * 订单来源 支持 APP\WEB\WAP\OPENAPI\OTHERS
     */
    private String orderSource;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 传入的结构化元数据，用于商户本身记录相应的数据。该字段将在后续原样返回
     */
    private Map<String, String> metadata;

}
