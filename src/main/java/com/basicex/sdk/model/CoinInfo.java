package com.basicex.sdk.model;

import lombok.Data;

import java.util.List;

/**
 * 票据的币种信息
 */
@Data
public class CoinInfo {
    /**
     * 币种名称
     */
    private String currency;

    /**
     * 币种全称
     */
    private String fullName;

    /**
     * 币种图标
     */
    private String coinIcon;

    /**
     * 币种精度
     */
    private Integer precision;

    /**
     * 币种支持的链列表信息
     */
    private List<CoinChainInfo> networkList;
}
