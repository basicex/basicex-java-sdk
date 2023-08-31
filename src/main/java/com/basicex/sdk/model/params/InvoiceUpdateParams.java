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
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 票据更新参数
 */
@Builder(toBuilder = true)
@Getter
public class InvoiceUpdateParams {
    /**
     *
     * 支付的区块链币种，例如: USDT, BTC等
     */
    private String currency;

    /**
     * 选择指定的链，例如: TRC20, ERC20等，当传入此参数时，currency为必传参数
     */
    private ChainNetwork chain;

    /**
     * 金额字段
     * 该字段根据`amountType`字段判断传入的是法币金额还是数字货币金额
     */
    private BigDecimal amount;

    /**
     * 金额类型字段，表示传入的`amount`是法币金额还是币种数量。
     * `money_price` 法币金额
     * `coin_amount` 币种数量
     */
    private AmountType amountType;

    /**
     * 商户侧的客户邮箱，如果传入，将在票据支付成功后向该邮箱发送邮件
     */
    private String payerEmail;

    /**
     * 忽略参数错误返回，当该参数为true时，将忽略掉参数错误
     * 例如：当创建支付票据订单时指定了强制传入链。并在更新支付票据订单时传入了金额，链，币种等参数时
     * 如果该参数设置为true，则将会返回错误。如果设置为false，则忽略掉这些参数，并更新支付票据订单
     */
    private Boolean ignoreParameterFailed;

    /**
     * 用于用户在收银台支付成功后，跳转至此地址。该地址必须以: `https`或`http`开头
     */
    private String redirectUrl;

    public void checkParams() {
        if(amount != null) {
            Preconditions.checkArgument(this.amountType != null, "amountType is required");

            if(amountType.equals(AmountType.COIN_AMOUNT)) {
                Preconditions.checkArgument(this.currency != null, "currency is required");
            }
        }
    }
}
