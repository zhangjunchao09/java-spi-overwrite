package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsFloatInfo extends ConstantBase {
    public byte[] bytes = new byte[4];

    public ConstantsFloatInfo() {
        description = "CONSTANT_Float_info";
    }

    @Override
    public void read(InputStream is) {
        try {
            is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println(description + "{");
        System.out.print("  bytes: ");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(bytes[i]));
        }
        System.out.print(" ||| " + getValue());
        System.out.println();
        System.out.println("}");
    }

    public Float getValue() {
        int accum = 0;
        accum = accum | (bytes[0] & 0xff) << 0;
        accum = accum | (bytes[1] & 0xff) << 8;
        accum = accum | (bytes[2] & 0xff) << 16;
        accum = accum | (bytes[3] & 0xff) << 24;
        System.out.println(accum);
        return Float.intBitsToFloat(accum);
    }
}