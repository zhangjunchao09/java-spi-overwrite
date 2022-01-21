package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsStringInfo extends ConstantBase {
    public byte[] bytes = new byte[2];

    public ConstantsStringInfo() {
        description = "CONSTANT_String_info";
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
        System.out.print(" || " + getStringValue());
        System.out.println();
        System.out.println("}");
    }

    public String getStringValue() {
        /*int index = (bytes[0] << 8) + bytes[1];
        ConstantBase constantBase = constantList.get(index - 1);
        ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;
        return constantsUtf8Info.getValue();*/
        return "";
    }
}
