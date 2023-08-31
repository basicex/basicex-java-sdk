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
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.params.InvoiceUpdateParams;
import com.basicex.sdk.model.params.constant.ChainNetwork;
import com.basicex.sdk.model.request.InvoiceCreateRequest;
import com.basicex.sdk.model.request.InvoiceUpdateRequest;
import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.RequestOptions;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class InvoiceService extends ApiService {


    public InvoiceService(BasicexResponseGetter getter) {
        super(getter);
    }

    /**
     * 创建一个新的支付订单票据
     * @param params 票据创建参数
     * @throws BasicexException
     */
    public InvoiceObject create(InvoiceCreateParams params) throws BasicexException {
        return create(params, null);
    }

    /**
     * 创建一个新的支付订单票据，并使用指定的请求选项
     * @param params 票据创建参数
     * @param options 请求选项
     * @throws BasicexException
     */
    public InvoiceObject create(InvoiceCreateParams params, RequestOptions options) throws BasicexException {
        // 检查参数
        params.checkParams();

        // 构建请求
        InvoiceCreateRequest.InvoiceCreateRequestBuilder builder = InvoiceCreateRequest.builder();
        if(params.getAmount() != null) {
            builder.amount(params.getAmount().multiply(BigDecimal.TEN.pow(params.getAmount().scale())).toBigInteger())
                    .precision(params.getAmount().scale())
                    .amountType(params.getAmountType().getCode());
        }
        builder.buyerId(params.getBuyerId())
                .orderId(params.getOrderId())
                .fiat(params.getFiat())
                .currency(params.getCurrency())
                .forcedChain(Optional.ofNullable(params.getForcedChain()).map(ChainNetwork::getCode).orElse(null))
                .metadata(params.getMetadata())
                .payerEmail(params.getPayerEmail())
                .notificationUrl(params.getNotificationUrl())
                .redirectUrl(params.getRedirectUrl())
                .buyerIp(params.getBuyerIp())
                .sendPaidNotification(params.getSendPaidNotification())
                .description(params.getDescription())
                .physical(params.getPhysical());

        String path = "/invoices";
        return getResponseGetter().request(
                ApiResource.RequestMethod.POST,
                path,
                builder.build(),
                InvoiceObject.class,
                true,
                options);
    }

    /**
     * 根据支付票据ID获取支付票据信息
     * @param invoiceID 支付票据ID
     * @throws BasicexException
     */
    public InvoiceObject get(String invoiceID) throws BasicexException {
        return get(invoiceID, null);
    }

    /**
     * 根据支付票据ID获取支付票据信息
     * @param invoiceID 支付票据ID
     * @throws BasicexException
     */
    public InvoiceObject get(String invoiceID, RequestOptions options) throws BasicexException {
        String path = String.format("/invoices/%s", invoiceID);
        return getResponseGetter().request(
                ApiResource.RequestMethod.GET,
                path,
                null,
                InvoiceObject.class,
                true,
                options);
    }

    /**
     * 根据商户订单ID获取支付票据信息
     * @param orderID 商户订单ID
     * @return 支付票据信息
     * @throws BasicexException
     */
    public InvoiceObject getByMerchantOrderId(String orderID) throws BasicexException {
        return getByMerchantOrderId(orderID, null);
    }

    /**
     * 根据商户订单ID获取支付票据信息
     * @param orderID 商户订单ID
     * @param options 请求选项
     * @return 支付票据信息
     * @throws BasicexException
     */
    public InvoiceObject getByMerchantOrderId(String orderID, RequestOptions options) throws BasicexException {
        String path = String.format("/invoices/merchant/%s", orderID);
        return getResponseGetter().request(
                ApiResource.RequestMethod.GET,
                path,
                null,
                InvoiceObject.class,
                true,
                options
        );
    }

    /**
     * 根据支付票据ID更新支付票据信息
     * @param invoiceID 支付票据ID
     * @param params 更新参数
     * @return
     */
    public InvoiceObject update(String invoiceID, InvoiceUpdateParams params) throws BasicexException {
        return update(invoiceID, params, null);
    }

    /**
     * 根据支付票据ID更新支付票据信息
     * @param invoiceID 支付票据ID
     * @param params 更新参数
     * @param options 请求选项
     * @return 支付票据信息
     */
    public InvoiceObject update(String invoiceID, InvoiceUpdateParams params, RequestOptions options) throws BasicexException {
        // 检查参数
        params.checkParams();

        InvoiceUpdateRequest.InvoiceUpdateRequestBuilder builder = InvoiceUpdateRequest.builder();
        if(params.getAmount() != null) {
            builder.amount(params.getAmount().multiply(BigDecimal.TEN.pow(params.getAmount().scale())).toBigInteger())
                    .precision(params.getAmount().scale())
                    .amountType(params.getAmountType().getCode());
        }

        builder.chain(Optional.ofNullable(params.getChain()).map(ChainNetwork::getCode).orElse(null))
                .ignoreParameterFailed(params.getIgnoreParameterFailed())
                .payerEmail(params.getPayerEmail())
                .redirectUrl(params.getRedirectUrl())
                .currency(params.getCurrency());

        String path = String.format("/invoices/%s", Objects.requireNonNull(invoiceID));
        return getResponseGetter().request(
                ApiResource.RequestMethod.PUT,
                path,
                builder.build(),
                InvoiceObject.class,
                true,
                options);
    }

}
