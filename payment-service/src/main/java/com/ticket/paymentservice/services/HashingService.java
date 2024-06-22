package com.ticket.paymentservice.services;

import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class HashingService {
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] calculateHmacSHA512(byte[] data, byte[] key) {
        HMac hmac = new HMac(new SHA512Digest());
        hmac.init(new KeyParameter(key));
        hmac.update(data, 0, data.length);
        byte[] result = new byte[hmac.getMacSize()];
        hmac.doFinal(result, 0);
        return result;
    }

    public String hashing(String message,String key) {
        try {
            byte[] hmacSha512 = calculateHmacSHA512(message.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to hex string (optional)
            String hmacSha512Hex = bytesToHex(hmacSha512);
            return hmacSha512Hex;
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }
}
