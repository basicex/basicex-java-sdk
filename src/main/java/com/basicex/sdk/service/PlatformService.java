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
import com.basicex.sdk.model.PlatformCertificateObject;
import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.RequestOptions;
import com.basicex.sdk.net.TypeReference;

import java.util.List;

/**
 * Platform Service
 */
public class PlatformService extends ApiService {
    public PlatformService(BasicexResponseGetter responseGetter) {
        super(responseGetter);
    }

    /**
     * 获取平台证书列表
     */
    public List<PlatformCertificateObject> certificates() throws BasicexException {
        return certificates(null);
    }

    /**
     * 获取平台证书列表
     * @param options the request options
     * @return the platform certificate object
     * @throws BasicexException
     */
    public List<PlatformCertificateObject> certificates(RequestOptions options) throws BasicexException {
        String path = "/platform/certificate";
        return getResponseGetter().request(
                ApiResource.RequestMethod.GET,
                path,
                null,
                new TypeReference<List<PlatformCertificateObject>>(){},
                false,
                options);
    }


}
