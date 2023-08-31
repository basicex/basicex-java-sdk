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

import com.basicex.sdk.net.BasicexResponseGetter;
import com.basicex.sdk.net.HttpClient;
import com.basicex.sdk.net.SignatureResponseGetter;
import com.basicex.sdk.service.InvoiceService;
import com.basicex.sdk.service.PayoutService;
import com.basicex.sdk.service.RefundService;
import com.basicex.sdk.util.PrivateKeyUtils;
import com.basicex.sdk.util.X509CertificateUtils;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * This is the primary entrypoint to make requests against BasicEx API.
 * It providers a means of accessing all the methods on the BasicEx API.
 * and the ability to set configuration such as Certificate-based Authentication
 * and connection timeouts.
 */
public class BasicExClient {
    @Getter
    private BasicexResponseGetter responseGetter;

    /**
     * Create a new BasicEx client instance.
     *
     * @param configFilePath The config file path
     */
    public BasicExClient(String configFilePath) throws CertificateException, IOException {
        BasicExConfig config = BasicExConfig.loadConfig(configFilePath);
        this.responseGetter = new SignatureResponseGetter(config);
    }

    /**
     * Create a new BasicEx client instance.
     *
     * @param privateKeyFilePath  The private key file path based on the format(PKCS#1 or PKCS#8)
     * @param certificateFilePath The  certificate file path based on the X.509 certificate format
     */
    public BasicExClient(String privateKeyFilePath, String certificateFilePath) throws IOException, CertificateException {
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(URI.create(privateKeyFilePath)));
        byte[] certificateBytes = Files.readAllBytes(Paths.get(URI.create(certificateFilePath)));

        PrivateKey privateKey = PrivateKeyUtils.loadPrivateKey(new String(privateKeyBytes));
        List<X509Certificate> certificateList = X509CertificateUtils.toX509CertificateList(new String(certificateBytes));
        if (certificateList == null || certificateList.isEmpty()) {
            throw new NullPointerException("certificateList is null or empty");
        }

        BasicExConfig config = BasicExConfig.builder()
                .privateKey(privateKey)
                .certificate(certificateList.get(0))
                .build();
        this.responseGetter = new SignatureResponseGetter(config);
    }

    public BasicExClient(PrivateKey privateKey, X509Certificate certificate) throws IOException {
        BasicExConfig config = BasicExConfig.builder()
                .privateKey(privateKey)
                .certificate(certificate)
                .build();
        this.responseGetter = new SignatureResponseGetter(config);
    }

    /**
     * Create a new BasicEx client instance.
     *
     * @param config The BasicExConfig instance
     */
    public BasicExClient(BasicExConfig config) {
        this(config, null);
    }

    /**
     * Create a new BasicEx client instance.
     *
     * @param config The BasicExConfig instance
     */
    public BasicExClient(BasicExConfig config, HttpClient client) {
        this.responseGetter = new SignatureResponseGetter(client, config);
    }

    public InvoiceService invoices() {
        return new InvoiceService(this.responseGetter);
    }

    public PayoutService payout() {
        return new PayoutService(this.responseGetter);
    }

    public RefundService refund() {
        return new RefundService(this.responseGetter);
    }

}
