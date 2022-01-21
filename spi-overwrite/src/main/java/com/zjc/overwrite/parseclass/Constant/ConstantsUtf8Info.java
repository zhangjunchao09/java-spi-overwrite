package com.zjc.overwrite.parseclass.Constant;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsUtf8Info extends ConstantBase {
    public int length;
    public byte[] bytes;

    public ConstantsUtf8Info() {
        description = "CONSTANT_Utf8_info";
    }

    public void read(InputStream is) {

        try {
            this.bytes = new byte[2];
            is.read(bytes);
            int length = (bytes[0] << 8) + bytes[1];
            this.length = length;
            //循环度length个byte
            bytes = new byte[length];
            is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bytes = bytes;

    }

    @Override
    public void print() {
        System.out.println(description + "{");
        System.out.println("  length: " + length);

        System.out.println("  bytes: " + getValue());
        System.out.println("}");
    }

    public String getValue() {
        return new String(bytes);
    }
}
