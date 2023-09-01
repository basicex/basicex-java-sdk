/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.model.webhook;

import lombok.Data;

import java.io.Serializable;

/**
 * Webhook
 */
@Data
public class WebhookEvent<T> implements Serializable {
    /**
     * Webhook通知ID
     */
    private String id;

    /**
     * Webhook通知承载体的唯一标识
     */
    private String objectId;

    /**
     * Webhook的对象类型
     */
    private String object;

    /**
     * Webhook创建时间
     */
    private Long created;

    /**
     * Webhook的通知类型
     */
    private String type;

    /**
     * Webhook的承载体
     */
    private T data;

    /**
     * 此通知的重试次数
     */
    private Integer retriesNum;
}
