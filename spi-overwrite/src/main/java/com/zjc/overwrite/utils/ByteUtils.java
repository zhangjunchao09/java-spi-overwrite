package com.zjc.overwrite.utils;

public class ByteUtils {
    public static String bytesToHexString(byte src) {
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (1 == hv.length()) {
            hv = "0" + hv;
        }
        return hv;
    }

    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (byte aByte : bytes) {
            result += bytesToHexString(aByte);
        }

        return result;
    }

    public static Integer bytesToInteger(byte[] bytes) {

        int length = bytes.length;
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += bytes[i] << ((length - 1 - i) * 8);
        }
        return sum;
    }
}
