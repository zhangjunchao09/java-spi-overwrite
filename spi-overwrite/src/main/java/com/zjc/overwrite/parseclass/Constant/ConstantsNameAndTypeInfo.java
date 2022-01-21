package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsNameAndTypeInfo extends ConstantBase {
    public byte[] indexName = new byte[2]; //记录变量名
    public byte[] indexType = new byte[2]; //记录变量类型

    public ConstantsNameAndTypeInfo(ClassInfo classInfo) {
        description = "CONSTANT_Name-AndType_info";
        this.classInfo = classInfo;
    }

    @Override
    public void read(InputStream is) {
        try {
            is.read(indexName);
            is.read(indexType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println(description + "{");
        System.out.print("  indexName: ");
        for (int i = 0; i < indexName.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(indexName[i]));
        }

        System.out.print(" || " + getName());
        System.out.println();
        System.out.print("  indexType: ");
        for (int i = 0; i < indexType.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(indexType[i]));
        }

        System.out.print(" || " + getType());
        System.out.println();
        System.out.println("}");
    }

    public String getNameAndType() {
        return getType() + " " + getName();
    }

    public String getType() {

        ConstantBase constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(indexType) - 1);
        ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;
        return constantsUtf8Info.getValue();
    }

    public String getName() {

        ConstantBase constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(indexName) - 1);
        ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;
        return constantsUtf8Info.getValue();
    }
}
