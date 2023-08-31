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

/**
 * 金额类型
 */
public enum AmountType {
    /**
     * 法币金额
     */
    MONEY_PRICE("money_price"),

    /**
     * 币种数量
     */
    COIN_AMOUNT("coin_amount");

    private final String code;

    AmountType(String code) {
        this.code = code;
    }

    public static AmountType valueOfCode(String code) {
        for (AmountType amountType : AmountType.values()) {
            if (amountType.getCode().equals(code)) {
                return amountType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}
