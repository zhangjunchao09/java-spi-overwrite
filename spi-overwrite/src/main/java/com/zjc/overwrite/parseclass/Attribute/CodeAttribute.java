package com.zjc.overwrite.parseclass.Attribute;

import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantsUtf8Info;
import com.zjc.overwrite.utils.ByteUtils;

public class CodeAttribute extends AttributeInfo {

    private ClassInfo classInfo;

    public CodeAttribute(ClassInfo classInfo) {

        this.classInfo = classInfo;
    }

    public byte[] maxStack = new byte[2];
    public byte[] maxLocals = new byte[2];

    public byte[] codeLength = new byte[4];
    public byte[] code;

    public byte[] exceptionTableLength = new byte[2];
    public Attribute.ExceptionInfo[] exceptionTable;

    public byte[] attributesCount = new byte[2];
    public AttributeInfo[] attributes;

    public void print() {

        System.out.println("    code_attribute_info:{");
        int index = ByteUtils.bytesToInteger(this.attributeNameIndex);
        ConstantBase constantBase2 = classInfo.getConstantList().get(index - 1);
        ConstantsUtf8Info constantsUtf8Info2 = (ConstantsUtf8Info) constantBase2;
        System.out.println("      attributeNameIndex: " + constantsUtf8Info2.getValue());
        System.out.println("      attributeLength: " + ByteUtils.bytesToHexString(this.attributeLength));
        System.out.println("      maxStack: " + ByteUtils.bytesToInteger(this.maxStack));
        System.out.println("      maxLocals: " + ByteUtils.bytesToInteger(this.maxLocals));
        System.out.println("      codeLength: " + ByteUtils.bytesToInteger(this.codeLength));
        System.out.println("      code: " + ByteUtils.bytesToHexString(this.code));
        System.out.println("      exceptionTableLength: " + ByteUtils.bytesToHexString(this.exceptionTableLength));
        System.out.println("      exceptionTable: " + this.exceptionTable);
        System.out.println("    }");

    }

}