package com.basicex.sdk.service;

import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.request.InvoiceCreateRequest;
import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.RequestOptions;

import java.math.BigDecimal;

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
                .forcedChain(params.getForcedChain())
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

}
