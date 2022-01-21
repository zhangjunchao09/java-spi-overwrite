package com.zjc.overwrite.parseclass.Attribute;

import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantsUtf8Info;
import com.zjc.overwrite.utils.ByteUtils;

public class SourceFileAttribute extends AttributeInfo {

    public byte[] sourceFileIndex = new byte[2];


    private ClassInfo classInfo;

    public SourceFileAttribute(ClassInfo classInfo) {

        this.classInfo = classInfo;
    }

    public void print() {

        int index = ByteUtils.bytesToInteger(this.attributeNameIndex);
        ConstantBase constantBase = classInfo.getConstantList().get(index - 1);
        ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;

        System.out.println(" source_file{" + constantsUtf8Info.getValue());
        System.out.println("  attributeNameIndex:" + constantsUtf8Info.getValue());
        System.out.println("  attributeLength:" + ByteUtils.bytesToInteger(this.attributeLength));

        ConstantBase constantBase1 = classInfo.getConstantList().get(ByteUtils.bytesToInteger(this.sourceFileIndex) - 1);
        ConstantsUtf8Info constantsUtf8Info1 = (ConstantsUtf8Info) constantBase1;

        System.out.println("  sourcefileIndex:" + constantsUtf8Info1.getValue());
        System.out.println("}");
    }
}
