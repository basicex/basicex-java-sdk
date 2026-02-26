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
import com.basicex.sdk.exception.SignatureException;
import com.basicex.sdk.exception.WebhookException;
import com.basicex.sdk.model.PlatformCertificateObject;
import com.basicex.sdk.model.params.constant.WebhookType;
import com.basicex.sdk.model.webhook.InvoiceCompletedWebhookMessage;
import com.basicex.sdk.model.webhook.InvoicePaidWebhookMessage;
import com.basicex.sdk.model.webhook.InvoicePartialCompletedWebhookMessage;
import com.basicex.sdk.model.webhook.PayoutWebhookMessage;
import com.basicex.sdk.model.webhook.WebhookEvent;
import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.TypeReference;
import com.basicex.sdk.util.HmacUtils;
import com.basicex.sdk.util.StringUtils;
import com.basicex.sdk.util.X509CertificateUtils;
import com.google.gson.JsonObject;
import org.bouncycastle.util.encoders.Base64;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Webhook Service
 */
public class WebhookService extends ApiService {
    private final PlatformService platformService;
    public WebhookService(BasicexResponseGetter responseGetter) {
        super(responseGetter);
        this.platformService = new PlatformService(responseGetter);
    }

    /**
     * 验证Webhook请求是否合规
     * @param request Webhook请求
     * @param notificationUrl Webhook通知地址
     */
    public WebhookEvent<?> validate(HttpServletRequest request, String notificationUrl) throws BasicexException, IOException {
        if(request.getHeader("X-Webhook-Signature") == null) {
            throw new SignatureException("Webhook请求头缺少X-Webhook-Signature", null, null, null);
        }

        String signature = request.getHeader("X-Webhook-Signature");
        String serial = request.getHeader("X-Webhook-Signature-Serial");
        String signatureType = request.getHeader("X-Webhook-Signature-Type");

        return validate(httpServletRequestToString(request), notificationUrl, signature, serial, signatureType);
    }

    /**
     * 验证Webhook请求是否合规
     * @param requestBody Webhook请求体
     * @param notificationUrl Webhook通知地址
     * @param signature Webhook请求头中的X-Webhook-Signature
     * @param signatureType Webhook请求头中的X-Webhook-Signature-Type (可为空)
     */
    public WebhookEvent<?> validate(String requestBody, String notificationUrl, String signature, String signatureType) throws BasicexException {
        return this.validate(requestBody, notificationUrl, signature, null, signatureType);
    }

    /**
     * 验证Webhook请求是否合规
     * @param requestBody Webhook请求体
     * @param notificationUrl Webhook通知地址
     * @param signature Webhook请求头中的X-Webhook-Signature
     * @param certificateSerialNo Webhook请求头中的X-Webhook-Signature-Serial
     * @param signatureType Webhook请求头中的X-Webhook-Signature-Type (可为空)
     */
    public WebhookEvent<?> validate(String requestBody, String notificationUrl, String signature, String certificateSerialNo, String signatureType) throws BasicexException {
        String signInput = String.format("%s%s", notificationUrl, requestBody);

        if ("key".equalsIgnoreCase(signatureType)) {
            // API Key 模式 HMAC 验签
            String secretKey = this.getResponseGetter().getConfig().getSecretKey();
            if (StringUtils.isBlank(secretKey)) {
                throw new SignatureException("SecretKey is not configured for webhook HMAC verification", null, null, null);
            }
            try {
                String expectedSignature = HmacUtils.signHmacSha512(secretKey, signInput);
                if (!expectedSignature.equalsIgnoreCase(signature)) {
                    throw new SignatureException("Signature verification failure, illegal webhook request", null, null, null);
                }
            } catch (Exception e) {
                throw new SignatureException("Signature verification failure: " + e.getMessage(), e);
            }
        } else {
            // 平台证书模式 RSA 验签
            if (StringUtils.isBlank(certificateSerialNo)) {
                throw new SignatureException("Webhook请求头缺少X-Webhook-Signature-Serial", null, null, null);
            }
            X509Certificate certificate = getPlatformCertificate(certificateSerialNo);
            if(!validateSignature(signInput.getBytes(), Base64.decode(signature), certificate)) {
                throw new SignatureException("Signature verification failure, illegal webhook request", null, null, null);
            }
        }

        // parse request body
        JsonObject obj = ApiResource.GSON.fromJson(requestBody, JsonObject.class);
        String typeStr = obj.get("type").getAsString();

        WebhookType type = WebhookType.fromType(typeStr);
        if(type == null) {
            throw new WebhookException("Unknown webhook type");
        }

        TypeReference<?> typeToken = null;
        switch (type) {
            case INVOICE_COMPLETED:
                typeToken = new TypeReference<WebhookEvent<InvoiceCompletedWebhookMessage>>(){};
                break;
            case INVOICE_PARTIAL_COMPLETED:
                typeToken = new TypeReference<WebhookEvent<InvoicePartialCompletedWebhookMessage>>(){};
                break;
            case INVOICE_PAID:
                typeToken = new TypeReference<WebhookEvent<InvoicePaidWebhookMessage>>(){};
                break;
            case PAYOUT_FAILED:
            case PAYOUT_SUCCESS:
                typeToken = new TypeReference<WebhookEvent<PayoutWebhookMessage>>(){};
                break;
        }

        if(typeToken == null) {
            throw new WebhookException("Unknown webhook type");
        }

        return ApiResource.GSON.fromJson(obj, typeToken.getType());
    }

    private boolean validateSignature(byte[] body, byte[] signature, X509Certificate certificate) throws SignatureException {
        try {
            Signature sign = Signature.getInstance(certificate.getSigAlgName());
            sign.initVerify(certificate.getPublicKey());

            // 更新签名
            sign.update(body);

            // 验证签名
            return sign.verify(signature);
        } catch (NoSuchAlgorithmException | java.security.SignatureException | InvalidKeyException e) {
            throw new SignatureException(e.getMessage(), e);
        }
    }

    private X509Certificate getPlatformCertificate(String serialNo) throws BasicexException {
        // 获取平台证书
        List<PlatformCertificateObject> certificates =  platformService.certificates();
        Optional<PlatformCertificateObject> certificate = certificates.stream().filter(x->x.getSerialNumber().equals(serialNo)).findFirst();
        if(!certificate.isPresent()) {
            throw new SignatureException("platform certificate does not exist", null, null, null);
        }

        try {
            List<X509Certificate> certificateList = X509CertificateUtils.toX509CertificateList(certificate.get().getCertificate());
            if(certificateList == null || certificateList.isEmpty()) {
                throw new SignatureException("Failed to parse platform certificate", null, null, null);
            }

            return certificateList.get(0);
        } catch (CertificateException e) {
            throw new SignatureException("Failed to parse platform certificate", e);
        }
    }

    private String httpServletRequestToString(HttpServletRequest request) throws IOException {
        ServletInputStream mServletInputStream = request.getInputStream();
        byte[] httpInData = new byte[request.getContentLength()];
        int retVal = -1;
        StringBuilder stringBuilder = new StringBuilder();

        while ((retVal = mServletInputStream.read(httpInData)) != -1) {
            for (int i = 0; i < retVal; i++) {
                stringBuilder.append((char) httpInData[i]);
            }
        }

        return stringBuilder.toString();
    }

}
