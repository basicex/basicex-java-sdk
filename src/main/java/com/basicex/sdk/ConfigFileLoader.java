/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk;


import com.basicex.sdk.model.JsonConfigModel;
import com.basicex.sdk.util.PrivateKeyUtils;
import com.basicex.sdk.util.X509CertificateUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * ConfigFileLoader
 */
public class ConfigFileLoader {

    public static BasicExConfig load(String configFilePath) throws IOException, CertificateException {
        JsonConfigModel configModel = loadJson(configFilePath);

        if(configModel == null) {
            throw new NullPointerException("configModel is null");
        }

        PrivateKey privateKey = PrivateKeyUtils.loadPrivateKey(configModel.getPrivateKey());
        List<X509Certificate> certificateList =  X509CertificateUtils.toX509CertificateList(configModel.getCertificate());
        if(certificateList == null || certificateList.isEmpty()) {
            throw new NullPointerException("certificateList is null or empty");
        }

        return BasicExConfig.builder()
                .privateKey(privateKey)
                .certificate(certificateList.get(0))
                .apiBaseUrl(configModel.getApiBaseUrl())
                .build();
    }

    private static JsonConfigModel loadJson(String configPath) throws IOException {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        try(BufferedReader input = Files.newBufferedReader(Paths.get(configPath))) {
            return gson.fromJson(input, JsonConfigModel.class);
        }
    }
}
