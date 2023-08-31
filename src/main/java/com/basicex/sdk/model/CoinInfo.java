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

import java.util.List;

/**
 * 票据的币种信息
 */
@Data
public class CoinInfo {
    /**
     * 币种名称
     */
    private String currency;

    /**
     * 币种全称
     */
    private String fullName;

    /**
     * 币种图标
     */
    private String coinIcon;

    /**
     * 币种精度
     */
    private Integer precision;

    /**
     * 币种支持的链列表信息
     */
    private List<CoinChainInfo> networkList;
}
