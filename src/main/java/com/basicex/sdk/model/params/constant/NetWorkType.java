/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
     * BEP20
     */
    BEP20("BEP20", "BEP20"),
    /**
     * TRC20
     */
    TRC20("TRC20", "TRC20"),
    /**
     * MATIC
     */
    MATIC("MATIC", "MATIC");

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
