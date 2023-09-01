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
import com.basicex.sdk.net.BasicexResponseGetter;

import javax.servlet.http.HttpServletRequest;

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
     */
    public void validate(HttpServletRequest request) throws BasicexException {
        if(request.getHeader("X-Webhook-Signature") == null || request.getHeader("X-Webhook-Signature-Serial") == null) {
            throw new SignatureException("Webhook请求头缺少X-Webhook-Signature或X-Webhook-Signature-Serial", null, null, null);
        }

        String signature = request.getHeader("X-Webhook-Signature");
        String serial = request.getHeader("X-Webhook-Signature-Serial");

        // 获取平台证书
        platformService.certificates();
    }
}
