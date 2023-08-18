package com.basicex.sdk.model;

import com.google.gson.JsonObject;

/**
 * Fallback class for when we do not recognize the object that we have received.
 */
public class BasicexRawJsonObject extends BasicexObject {
    JsonObject json;
}
