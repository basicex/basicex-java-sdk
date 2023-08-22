package com.basicex.sdk.model.params.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;


/**
 * type of network
 *
 * @author zhimaDADA
 * @date 2023-8-22
 */
@Getter
@RequiredArgsConstructor
public enum NetWorkType {
    /**
     * ERC20
     */
    ERC20("ERC20", "ERC20"),
    /**
     * BSC
     */
    BSC("BSC", "BSC"),
    /**
     * TRC20
     */
    TRC20("TRC20", "TRC20"),
    /**
     * POLYGON
     */
    POLYGON("Polygon", "POLYGON");

    /**
     * type
     */
    public final String code;

    /**
     * description
     */
    public final String desc;

    public static NetWorkType valueOfCode(String code) {
        return Arrays.stream(NetWorkType.values()).filter(x -> x.getCode().equals(code)).findFirst().orElse(null);
    }
}
