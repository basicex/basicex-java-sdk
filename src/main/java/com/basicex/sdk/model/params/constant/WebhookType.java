/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.model.params.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebhookType {
    /**
     * 支付票据已支付成功事件通知，该事件通知在支付票据支付成功后触发，但是并不代表该支付票据已经完成，以invoice.completed事件通知为准
     */
    INVOICE_PAID("invoice.paid"),
    /**
     * 支付票据已完成事件通知，该事件通知在支付票据完成后触发，代表该支付票据已经完成
     */
    INVOICE_COMPLETED("invoice.completed"),
    /**
     * 支付票据在有效期内存在部分支付，但支付的金额小于票据的金额，商户务必根据paidAmount字段获取当前已支付的金额
     */
    INVOICE_PARTIAL_COMPLETED("invoice.partial_completed"),
    /**
     * 支付票据已过期事件通知，该事件通知在支付票据过期后触发，代表该支付票据已经过期
     */
    INVOICE_EXPIRED("invoice.expired"),
    /**
     * 代付成功事件,该事件通知在代付成功过后触发,代表代付成功
     */
    PAYOUT_SUCCESS("payout.success"),

    /**
     * 代付失败事件,该事件通知在代付失败过后触发,代表代付失败
     */
    PAYOUT_FAILED("payout.failed")
    ;

    private String type;

    public static WebhookType fromType(String type) {
        for (WebhookType webhookType : values()) {
            if (webhookType.getType().equals(type)) {
                return webhookType;
            }
        }
        return null;
    }
}
