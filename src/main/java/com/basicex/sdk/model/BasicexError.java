package com.basicex.sdk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BasicexError extends BasicexObject{
    private String code;

    private String message;

    private String param;
}
