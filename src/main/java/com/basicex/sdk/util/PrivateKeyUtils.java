package com.basicex.sdk.util;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;

/**
 * PrivateKeyUtils
 *
 * @author nidajie
 * @date 2023/8/18
 */
public class PrivateKeyUtils {
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * load private key
     * @param privateKeyStr private key string
     * @return PrivateKey
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws  IOException {
        PEMParser pemParser = new PEMParser(new StringReader(privateKeyStr));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        Object object = pemParser.readObject();

        if(object instanceof PEMKeyPair) {
            return converter.getKeyPair((PEMKeyPair) object).getPrivate();
        } else if (object instanceof PrivateKeyInfo) {
            return converter.getPrivateKey((PrivateKeyInfo) object);
        }

        return null;
    }

    /**
     * Sign data with private key
     * @param privateKey private key
     * @param algorithm algorithm
     * @param input input
     * @return byte[]
     * @throws Exception
     */
    public static byte[] sign(PrivateKey privateKey, String algorithm, byte[] input) throws Exception {
        Signature sign = Signature.getInstance(algorithm);
        sign.initSign(privateKey);

        sign.update(input);

        return sign.sign();
    }
}
