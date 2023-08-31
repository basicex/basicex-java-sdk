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
import com.basicex.sdk.model.RefundObject;
import com.basicex.sdk.model.params.RefundCreateParams;
import com.basicex.sdk.model.request.RefundCreateRequest;
import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.RequestOptions;

import java.math.BigDecimal;

public class RefundService extends ApiService {

    private static final String url = "/refunds";

    public RefundService(BasicexResponseGetter responseGetter) {
        super(responseGetter);
    }

    /**
     * 创建一个新的退款订单票据
     *
     * @param params 票据创建参数
     * @throws BasicexException
     */
    public RefundObject create(RefundCreateParams params) throws BasicexException {
        return create(params, null);
    }

    /**
     * 创建一个新的退款订单票据，并使用指定的请求选项
     *
     * @param params  票据创建参数
     * @param options 请求选项
     * @throws BasicexException
     */
    public RefundObject create(RefundCreateParams params, RequestOptions options) throws BasicexException {
        //校验代付订单参数
        params.checkParams();
        //构建请求参数
        RefundCreateRequest.RefundCreateRequestBuilder refundRequestBuilder = RefundCreateRequest.builder();
        if (params.getAmount() != null) {
            refundRequestBuilder.amount(params.getAmount().multiply(BigDecimal.TEN.pow(params.getAmount().scale())).toBigInteger())
                    .precision(params.getAmount().scale());
        }
        refundRequestBuilder
                .coinPrecision(params.getCoinPrecision())
                .customerEmail(params.getCustomerEmail())
                .merOrderNo(params.getMerOrderNo())
                .amountType(params.getAmountType())
                .orderNo(params.getOrderNo())
                .merRefundOrderNo(params.getMerRefundOrderNo())
                .refundReason(params.getRefundReason())
                .tradeStartTime(params.getTradeStartTime())
                .customerEmail(params.getCustomerEmail());

        return getResponseGetter().request(
                ApiResource.RequestMethod.POST,
                url,
                refundRequestBuilder.build(),
                RefundObject.class,
                true,
                options);

    }

}
