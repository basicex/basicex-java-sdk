/*
 * Copyright (c) 2023 BasicEx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.basicex.sdk.util;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * X509Certificate Util
 */
public class X509CertificateUtils {

    /**
     * To retrieve the serial number of an X.509 certificate
     * @param certificate X.509 certificate
     * @return serial number of the certificate
     */
    public static String getCertificateSerialNo(X509Certificate certificate) {
        return formatByteArray(certificate.getSerialNumber().toByteArray());
    }

    public static String toPEMString(X509Certificate certificate) throws CertificateException, IOException {
        StringWriter stringWriter = new StringWriter();
        try (PemWriter pemWriter = new PemWriter(stringWriter)) {
            PemObject pemObject = new PemObject("CERTIFICATE", certificate.getEncoded());
            pemWriter.writeObject(pemObject);
        } catch (IOException e) {
            throw e;
        }

        return stringWriter.toString();
    }

    /**
     * To extract the Common Name (CN) from an X.509 certificate
     * @param certificate X509 certificate
     * @return CommonName
     */
    public static String getCommonNameFromCertificate(X509Certificate certificate) throws IOException {
        X500Name subject = new X500Name(certificate.getSubjectX500Principal().getName());
        RDN[] rdns = subject.getRDNs(RFC4519Style.cn);

        if (rdns != null && rdns.length > 0) {
            return rdns[0].getFirst().getValue().toString();
        }
        return null;
    }

    /**
     * 将原始PEM Raw数据转换为X509证书链列表
     * @param pemRaw PEM原始数据
     * @return X509证书链列表
     */
    public static List<X509Certificate> toX509CertificateList(String pemRaw) throws CertificateException {
        List<X509Certificate> certificates;

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        certificates = (List<X509Certificate>) certificateFactory.generateCertificates(new ByteArrayInputStream(pemRaw.getBytes()));

        return certificates;
    }

    private static String formatByteArray(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x:", b));
        }
        return result.substring(0, result.length() - 1); // Remove trailing colon
    }
}
