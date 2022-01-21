package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantMethodTypeInfo extends ConstantBase {
    public byte[] indexDescriptor = new byte[2]; //记录常量池的索引（类型必须为ConstantsUtf8Info）

    public ConstantMethodTypeInfo() {
        description = "CONSTANT_Method-Type_info";
    }

    @Override
    public void read(InputStream is) {
        try {
            is.read(indexDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println(description + "{");
        System.out.print("  indexDescriptor: ");
        for (int i = 0; i < indexDescriptor.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(indexDescriptor[i]));
        }
        System.out.println();
        System.out.println("}");
    }
}
