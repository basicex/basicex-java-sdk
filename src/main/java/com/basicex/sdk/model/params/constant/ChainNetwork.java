package com.basicex.sdk.model.params.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChainNetwork {
    TRC20("TRC20"),
    ERC20("ERC20"),
    POLYGON("Polygon"),
    BSC("BSC");

    private String code;
}
