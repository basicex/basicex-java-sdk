package com.basicex.sdk.util;

import org.bouncycastle.util.encoders.Hex;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * HmacUtils
 */
public class HmacUtils {

    /**
     *  Calculate HMAC-SHA512 signature
     *
     * @param secretKey secret key string
     * @param data      data to sign
     * @return hex encoded lowercase signature string
     */
    public static String signHmacSha512(String secretKey, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha512Hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        sha512Hmac.init(secretKeySpec);

        byte[] macData = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Hex.toHexString(macData).toLowerCase();
    }
}
