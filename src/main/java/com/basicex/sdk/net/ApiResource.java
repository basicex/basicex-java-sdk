package com.basicex.sdk.net;

import com.basicex.sdk.model.BasicexObject;
import com.basicex.sdk.model.BasicexRawJsonObject;
import com.google.gson.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class ApiResource extends BasicexObject {
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public static final Gson GSON = createGson();

    private static Gson createGson() {
        GsonBuilder builder =
                new GsonBuilder()
                        .addReflectionAccessFilter(
                                rawClass -> {
                                    if (rawClass.getTypeName().startsWith("com.basicex.")) {
                                        return ReflectionAccessFilter.FilterResult.ALLOW;
                                    }
                                    return ReflectionAccessFilter.FilterResult.BLOCK_ALL;
                                });


        return builder.create();
    }

    public enum RequestMethod {
        GET,
        POST,
        DELETE,
        PUT,
    }
}
