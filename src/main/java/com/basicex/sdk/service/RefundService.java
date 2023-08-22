package com.basicex.sdk.service;

import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.PayoutObject;
import com.basicex.sdk.model.params.PayoutCreateParams;
import com.basicex.sdk.model.request.PayoutCreateRequest;
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
                    .precision(params.getAmount().scale())
                    .amountType(params.getAmountType().getCode());
        }
        payoutRequestBuilder
                .fiat(params.getFiat())
                .currency(params.getCurrency())
                .metadata(params.getMetadata())
                .notificationUrl(params.getNotificationUrl())
                .sendPaidNotification(params.getSendPaidNotification())
                .physical(params.getPhysical())
                .coinPrecision(params.getCoinPrecision())
                .customerEmail(params.getCustomerEmail())
                .description(params.getDescription())
                .metadata(params.getMetadata())
                .source(params.getSource())
                .customerIp(params.getCustomerIp())
                .physical(params.getPhysical())
                .targetType(params.getTargetType())
                .target(params.getTarget())
                .merOrderNo(params.getMerOrderNo())
                .netWork(params.getNetWork());

        return getResponseGetter().request(
                ApiResource.RequestMethod.POST,
                url,
                payoutRequestBuilder.build(),
                PayoutObject.class,
                true,
                options);

    }

}
