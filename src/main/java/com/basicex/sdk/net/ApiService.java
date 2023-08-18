package com.basicex.sdk.net;

public abstract class ApiService {
    private final BasicexResponseGetter responseGetter;

    protected ApiService(BasicexResponseGetter responseGetter) {
        this.responseGetter = responseGetter;
    }

    protected BasicexResponseGetter getResponseGetter() {
        return responseGetter;
    }
}
