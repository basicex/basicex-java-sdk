/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.model;

import java.math.BigDecimal;
import java.util.Map;

public class PayoutResp extends BasicexObject {
    /**
     * 商户订单号
     */
    private String merOrderNo;

    /**
     * 系统订单号
     */
    private String orderNo;

    /**
     * 代付对象类型
     */
    private String targetType;

    /**
     * 代付对象（email/手机号/币趣id/用户名）
     */
    private String target;

    /**
     * 订单金额
     */
    private BigDecimal actualAmount;

    /**
     * 币种 支持USDT BUSD
     */
    private String currency;

    /**
     * 网络 TRC20 BSC ERC20 Polygon
     * 当targetType为address时必传
     */
    private String network;

    /**
     * 代付描述
     */
    private String description;

    /**
     * 交易完成时间
     */
    private Long tradeFinishTime;

    /**
     * 附加数据v2
     */
    private Map<String, String> metadata;

    /**
     * 状态
     */
    private String status;

    /**
     * 交易订单号
     */
    private String transactionId;

}
