package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsIntegerInfo extends ConstantBase{
    public byte[] bytes = new byte[4];

    public ConstantsIntegerInfo(){
        description = "CONSTANT_Integer_info";
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
        System.out.println(description+"{");
        System.out.print("  bytes: ");
        for(int i = 0;i<bytes.length;i++){
            System.out.print(ByteUtils.bytesToHexString(bytes[i]));
        }
        System.out.print(" ||| "+getValue());
        System.out.println();
        System.out.println("}");
    }

    public Integer getValue(){
        return ByteUtils.bytesToInteger(bytes);
    }
}
