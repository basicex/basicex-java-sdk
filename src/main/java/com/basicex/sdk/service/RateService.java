/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.service;

import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.RequestOptions;
import com.basicex.sdk.net.TypeReference;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * Rate Service
 */
public class RateService extends ApiService {
    public RateService(BasicexResponseGetter responseGetter) {
        super(responseGetter);
    }

    /**
     * 根据法币获取数字货币汇率信息
     * @param currency 法定货币
     * @return 数字货币汇率信息
     */
    public Map<String, BigDecimal> getRates(String currency) throws BasicexException {
        return getRates(currency, null);
    }

    /**
     * 根据法币获取数字货币汇率信息
     * @param currency 法定货币
     * @param options the request options
     * @return 数字货币汇率信息
     */
    public Map<String, BigDecimal> getRates(String currency, RequestOptions options) throws BasicexException {
        String path = String.format("/rates/%s", Objects.requireNonNull(currency));
        return getResponseGetter().request(
                ApiResource.RequestMethod.GET,
                path,
                null,
                new TypeReference<Map<String, BigDecimal>>(){},
                true,
                options);
    }
}
