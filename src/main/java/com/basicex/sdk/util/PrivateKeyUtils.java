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
