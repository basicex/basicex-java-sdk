package com.basicex.sdk.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class InvoiceExchangeRate {
    /**
     * 法币对应数字货币的汇率
     */
    private Map<String, BigDecimal> rate = new HashMap<>();
}
