package com.basicex.sdk.util;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.RFC4519Style;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
