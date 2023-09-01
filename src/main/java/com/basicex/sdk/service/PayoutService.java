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
import com.basicex.sdk.model.PayoutObject;
import com.basicex.sdk.model.PayoutResp;
import com.basicex.sdk.model.params.PayoutCreateParams;
import com.basicex.sdk.model.request.PayoutCreateRequest;
import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.RequestOptions;
import com.basicex.sdk.net.TypeReference;

import java.math.BigDecimal;

public class PayoutService extends ApiService {

    private static final String url = "/payouts";

    public PayoutService(BasicexResponseGetter responseGetter) {
        super(responseGetter);
    }

    /**
     * 创建一个新的代付订单票据
     *
     * @param params 票据创建参数
     * @throws BasicexException
     */
    public PayoutObject create(PayoutCreateParams params) throws BasicexException {
        return create(params, null);
    }

    /**
     * 创建一个新的代付订单票据，并使用指定的请求选项
     *
     * @param params  票据创建参数
     * @param options 请求选项
     * @throws BasicexException
     */
    public PayoutObject create(PayoutCreateParams params, RequestOptions options) throws BasicexException {
        //校验代付订单参数
        params.checkParams();
        //构建请求参数
        PayoutCreateRequest.PayoutCreateRequestBuilder payoutRequestBuilder = PayoutCreateRequest.builder();
        if (params.getAmount() != null) {
            payoutRequestBuilder.amount(params.getAmount().multiply(BigDecimal.TEN.pow(params.getAmount().scale())).toBigInteger())
                    .precision(params.getAmount().scale());
        }
        payoutRequestBuilder
                .currency(params.getCurrency())
                .metadata(params.getMetadata())
                .notificationUrl(params.getNotificationUrl())
                .physical(params.getPhysical())
                .customerEmail(params.getCustomerEmail())
                .description(params.getDescription())
                .metadata(params.getMetadata())
                .customerIp(params.getCustomerIp())
                .physical(params.getPhysical())
                .targetType(params.getTargetType())
                .target(params.getTarget())
                .merOrderNo(params.getMerOrderNo())
                .network(params.getNetwork());

        return getResponseGetter().request(
                ApiResource.RequestMethod.POST,
                url,
                payoutRequestBuilder.build(),
                new TypeReference<PayoutObject>() {
                },
                true,
                options);
    }

    /**
     * 根据平台订单号或者商户订单号查询代付信息
     *
     * @param orderNo 平台订单号或者商户订单号
     * @throws BasicexException
     */
    public PayoutResp get(String orderNo) throws BasicexException {
        return get(orderNo, null);
    }

    /**
     * 根据平台订单号或者商户订单号查询代付信息
     *
     * @param orderNo 平台订单号或者商户订单号
     * @throws BasicexException
     */
    public PayoutResp get(String orderNo, RequestOptions options) throws BasicexException {
        String path = String.format("/payouts/%s", orderNo);
        return getResponseGetter().request(
                ApiResource.RequestMethod.GET,
                path,
                null,
                new TypeReference<PayoutResp>() {
                },
                true,
                options);
    }

}
