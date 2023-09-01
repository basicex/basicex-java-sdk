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

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

@Data
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
     * 金额字段
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
     * 传入的结构化元数据，用于商户本身记录相应的数据。该字段将在后续原样返回
     */
    private Map<String, String> metadata;

}
