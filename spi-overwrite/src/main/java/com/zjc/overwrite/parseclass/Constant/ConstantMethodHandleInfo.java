package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantMethodHandleInfo extends ConstantBase {
    public byte[] referenceKind = new byte[1]; //记录方法句柄的类型
    public byte[] indexReference = new byte[2]; //记录常量池的索引

    public ConstantMethodHandleInfo() {
        description = "CONSTANT_Method-Handle_info";
    }

    @Override
    public void read(InputStream is) {
        try {
            is.read(referenceKind);
            is.read(indexReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println(description + "{");
        System.out.print("  referenceKind: ");
        for (int i = 0; i < referenceKind.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(referenceKind[i]));
        }
        System.out.println();
        System.out.print("  indexReference: ");
        for (int i = 0; i < indexReference.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(indexReference[i]));
        }
        System.out.println();
        System.out.println("}");
    }
}