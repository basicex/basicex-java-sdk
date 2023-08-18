package com.basicex.sdk.model.params.constant;

/**
 * 金额类型
 */
public enum AmountType {
    /**
     * 法币金额
     */
    MONEY_PRICE("money_price"),

    /**
     * 币种数量
     */
    COIN_AMOUNT("coin_amount");

    private final String code;

    AmountType(String code) {
        this.code = code;
    }

    public static AmountType valueOfCode(String code) {
        for (AmountType amountType : AmountType.values()) {
            if (amountType.getCode().equals(code)) {
                return amountType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}
