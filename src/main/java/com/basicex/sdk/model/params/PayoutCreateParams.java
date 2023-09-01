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
import com.basicex.sdk.util.Preconditions;
import com.basicex.sdk.util.StringUtils;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 代付票据创建参数
 */
@Getter
@Builder(toBuilder = true)
public class PayoutCreateParams {

    /**
     * 代付金额
     */
    private BigDecimal amount;

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
     * 商户侧客户端IP
     */
    private String customerIp;

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
    private String network;


    public void checkParams() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.target), "target is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.currency), "currency is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.targetType), "targetType is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.description), "description is required");
        Preconditions.checkArgument(StringUtils.isNotEmpty(this.notificationUrl), "notificationUrl is required");
    }
}
