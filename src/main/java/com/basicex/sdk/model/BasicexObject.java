/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.model;

import com.basicex.sdk.net.ApiResource;
import com.basicex.sdk.net.BasicexResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.util.Objects;

public abstract class BasicexObject {
    public static final Gson PRETTY_PRINT_GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

    private transient BasicexResponse lastResponse;

    private transient JsonObject rawJsonObject;

    @Override
    public String toString() {
        return String.format(
                "<%s@%s id=%s> JSON: %s",
                this.getClass().getName(),
                System.identityHashCode(this),
                this.getIdString(),
                PRETTY_PRINT_GSON.toJson(this));
    }

    public BasicexResponse getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(BasicexResponse response) {
        this.lastResponse = response;
    }
    /**
     * Returns the raw JsonObject exposed by the Gson library
     * @return The raw JsonObject.
     */
    public JsonObject getRawJsonObject() {
        // Lazily initialize this the first time the getter is called.
        if ((this.rawJsonObject == null) && (this.getLastResponse() != null)) {
            this.rawJsonObject =
                    ApiResource.GSON.fromJson(this.getLastResponse().getBody(), JsonObject.class);
        }

        return this.rawJsonObject;
    }

    public String toJson() {
        return PRETTY_PRINT_GSON.toJson(this);
    }

    private Object getIdString() {
        try {
            Field idField = this.getClass().getDeclaredField("id");
            return idField.get(this);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            return "";
        }
    }

    protected static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

}
