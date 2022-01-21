package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsLongInfo extends ConstantBase {
    public byte[] bytes = new byte[8];

    public ConstantsLongInfo() {
        description = "CONSTANT_Long_info";
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
        System.out.println();
        System.out.println("}");
    }
}
