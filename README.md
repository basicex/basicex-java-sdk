# BasicEx Java SDK
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/badge/maven--central-v1.0.2-blue)](https://central.sonatype.com/artifact/com.basicex/basicex-java/1.0.2)

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

#### 配置SDK连接超时

通过全局设置SDK连接和读取超时时间:
```java
BasicExClient client = new BasicExClient(configPath, BasicExConfig.builder()
                .connectTimeout(30 * 1000)
                .readTimeout(30 * 1000)
                .build());
```

#### 配置SDK自动重试次数

BasicEx Java SDK可以设置自动重试此数，当因为网络连接超时等原因情况下能够重新发起请求:
```java
BasicExClient client = new BasicExClient(configPath, BasicExConfig.builder()
                .maxNetworkRetries(2)
                .build());
```

#### 配置SDK代理

BasicEx Java SDK可以设置代理，当你的服务器需要代理才能访问外网时:
```java
BasicExClient client = new BasicExClient(configPath, BasicExConfig.builder()
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1080)), new PasswordAuthentication("user", "password".toCharArray()))
                .build());
```

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

#### 创建一个代付请求:

```java
import com.basicex.sdk.BasicExClient;
import com.basicex.sdk.exception.BasicexException;
import com.basicex.sdk.model.InvoiceObject;
import com.basicex.sdk.model.PayoutObject;
import com.basicex.sdk.model.params.InvoiceCreateParams;
import com.basicex.sdk.model.params.PayoutCreateParams;
import com.basicex.sdk.model.params.constant.AmountType;
import com.basicex.sdk.model.params.constant.NetWorkType;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasicExPayoutsTest {
    private static String configPath = "~/4d1ebd88-8154-4ca1-b1c6-051b7d28c204/config.json";
    public static void main(String[] args) throws CertificateException, IOException, BasicexException {
        BasicExClient client = new BasicExClient(configPath);
        PayoutService payout = client.payouts();
        Map<String, String> map = new HashMap<>();
       	map.put("desc", "Hello,BasicEx");

        PayoutCreateParams params = PayoutCreateParams.builder()
                .amount(new BigDecimal(5))
                .currency("USDT")
                .notificationUrl("https://basicex.com/notify")
                .description("TEST PAYOUT SDK")
                .metadata(map)
                .physical(Boolean.TRUE)
                .targetType("ADDRESS")
                .target("0x682D39Ea8d26510BE379d30807AF61e5eF9E2XXX")
                .network(NetWorkType.ERC20.code)
                .merOrderNo(UUID.randomUUID().toString().replace("-", ""))
                .build();
        PayoutObject payoutObject = payout.create(params);
        System.out.println(payoutObject);
    }
}
```

