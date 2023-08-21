package com.basicex.sdk.service;

import com.basicex.sdk.net.BasicexResponseGetter;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class ApiService {
    @Getter(AccessLevel.PROTECTED)
    private final BasicexResponseGetter responseGetter;

    protected ApiService(BasicexResponseGetter responseGetter) {
        this.responseGetter = responseGetter;
    }
}
