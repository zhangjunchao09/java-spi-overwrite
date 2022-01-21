package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsInterfaceMethodrefInfo extends ConstantBase {
    public byte[] indexClass = new byte[2];
    public byte[] indexNameAndType = new byte[2];

    public ConstantsInterfaceMethodrefInfo() {
        description = "CONSTANT_Interface-Methodref_info";
    }

    @Override
    public void read(InputStream is) {
        try {
            is.read(indexClass);
            is.read(indexNameAndType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println(description + "{");
        System.out.print("  indexClass: ");
        for (int i = 0; i < indexClass.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(indexClass[i]));
        }
        System.out.println();
        System.out.print("  indexNameAndType: ");
        for (int i = 0; i < indexNameAndType.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(indexNameAndType[i]));
        }
        System.out.println();
        System.out.println("}");
    }
}
