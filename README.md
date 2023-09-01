# BasicEx Java SDK
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[English](./README-en.md) | [简体中文](./README.md)

该Java SDK完整实现了币趣支付网关中的RESTful接口

## 安装

### 必须

- Java 1.8 或更高.

### Gradle使用

添加这个依赖到你的项目的构建文件:

```groovy
implementation "com.basicex:basicex-java:1.0.2"
```

### Maven使用

添加这个依赖到你的项目的pom.xml文件:

```xml
<dependency>
    <groupId>com.basicex</groupId>
    <artifactId>basicex-java</artifactId>
    <version>1.0.2</version>
</dependency>
```

## 文档

### 使用

#### 创建一个新票据

```java
import com.basicex.sdk.BasicExClient;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.params.constant.AmountType;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.UUID;

public class BasicExTest{
    private static String configPath = "~/4d1ebd88-8154-4ca1-b1c6-051b7d28c204/config.json";
    public static void main(String[] args) throws CertificateException, IOException, BasicexException {
        BasicExClient client = new BasicExClient(configPath);
        InvoiceObject invoiceObject = client.invoices().create(InvoiceCreateParams.builder()
                .redirectUrl("https://basicex.com")
                .notificationUrl("https://basicex.com/notify")
                .currency("USDT")
                .amount(new BigDecimal("10.25"))
                .amountType(AmountType.COIN_AMOUNT)
                .orderId(UUID.randomUUID().toString())
                .description("Hello, BasicEx")
                .build());

        String cashierUrl = invoiceObject.getCashierUrl();
        System.out.println(cashierUrl);
    }
}
```