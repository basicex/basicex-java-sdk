/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.net;

import java.util.AbstractMap;

/**
 * A KeyValuePair holds a key and a value. This class is used to represent parameters when encoding
 * API requests.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class KeyValuePair<K, V> extends AbstractMap.SimpleEntry<K, V> {
    private static final long serialVersionUID = 1L;

    /**
     * Initializes a new instance of the {@link KeyValuePair} class using the specified key and value.
     *
     * @param key the key
     * @param value the value
     */
    public KeyValuePair(K key, V value) {
        super(key, value);
    }
}
