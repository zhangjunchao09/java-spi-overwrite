package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantsFieldrefInfo extends ConstantBase {
    public byte[] indexClass = new byte[2];
    public byte[] indexNameAndType = new byte[2];

    public ConstantsFieldrefInfo(ClassInfo classInfo) {
        description = "CONSTANT_Fieldref_info";
        this.classInfo = classInfo;
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

        System.out.print(" || " + getindexClassValue());
        System.out.println();
        System.out.print("  indexNameAndType: ");
        for (int i = 0; i < indexNameAndType.length; i++) {
            System.out.print(ByteUtils.bytesToHexString(indexNameAndType[i]));
        }
        System.out.print(" || " + getindexNameAndTypeValue());
        System.out.println();
        System.out.println("}");
    }

    public String getindexClassValue() {
        ConstantBase constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(indexClass) - 1);
        ConstantsClassInfo constantsClassInfo = (ConstantsClassInfo) constantBase;
        return constantsClassInfo.getClassName();
    }

    public String getindexNameAndTypeValue() {
        ConstantBase constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(indexNameAndType) - 1);
        ConstantsNameAndTypeInfo constantsNameAndTypeInfo = (ConstantsNameAndTypeInfo) constantBase;
        return constantsNameAndTypeInfo.getNameAndType();
    }
}
