package com.zjc.overwrite.parseclass.Attribute;

import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantsUtf8Info;
import com.zjc.overwrite.parseclass.Handle.Handle;
import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class Attribute implements Handle {

    private int attributeCount;
    private ClassInfo classInfo;

    public Attribute(ClassInfo classInfo) {

        this.classInfo = classInfo;
    }

    @Override
    public void read(InputStream is) {

        byte[] bytes = new byte[2];

        try {
            is.read(bytes);
            attributeCount = ByteUtils.bytesToInteger(bytes);

            if (attributeCount > 0) {
                for (int i = 0; i < attributeCount; i++) {
                    AttributeInfo attributeInfo = new AttributeInfo();
                    bytes = new byte[2];
                    is.read(bytes);

                    ConstantBase constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(bytes) - 1);
                    ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;

                    String value = constantsUtf8Info.getValue();
                    if ("Code".equals(value)) {
                        //读取code
                        attributeInfo = readCode(is);
                        attributeInfo.attributeNameIndex = bytes;

                    } else if ("SourceFile".equals(value)) {
                        attributeInfo = readSourceFile(is);
                        attributeInfo.attributeNameIndex = bytes;
                    }
                    classInfo.getAttributeInfos().add(attributeInfo);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public AttributeInfo readAttributeInfo(InputStream is) throws IOException {

        AttributeInfo attributeInfo = new AttributeInfo();
        is.read(attributeInfo.attributeNameIndex);
        is.read(attributeInfo.attributeLength);
        Integer length = ByteUtils.bytesToInteger(attributeInfo.attributeLength);
        if (length > 0) {
            attributeInfo.info = new byte[length];
            is.read(attributeInfo.info);

        }
        return attributeInfo;
    }

    public CodeAttribute readCode(InputStream is) throws IOException {
        CodeAttribute codeAttribute = new CodeAttribute(classInfo);

        is.read(codeAttribute.attributeLength);
        is.read(codeAttribute.maxStack);
        is.read(codeAttribute.maxLocals);
        is.read(codeAttribute.codeLength);
        codeAttribute.code = new byte[ByteUtils.bytesToInteger(codeAttribute.codeLength)];
        is.read(codeAttribute.code);
        is.read(codeAttribute.exceptionTableLength);
        Integer exceptionLength = ByteUtils.bytesToInteger(codeAttribute.exceptionTableLength);

        if (exceptionLength > 0) {
            codeAttribute.exceptionTable = new ExceptionInfo[exceptionLength];
            for (int i = 0; i < exceptionLength; i++) {
                ExceptionInfo exceptionInfo = readException(is);
                codeAttribute.exceptionTable[i] = exceptionInfo;
            }
        }

        is.read(codeAttribute.attributesCount);
        Integer attributeCount = ByteUtils.bytesToInteger(codeAttribute.attributesCount);

        codeAttribute.attributes = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            AttributeInfo attributeInfo = readAttributeInfo(is);
            codeAttribute.attributes[i] = attributeInfo;
        }
        return codeAttribute;
    }

    private ExceptionInfo readException(InputStream is) throws IOException {
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        is.read(exceptionInfo.attributeNameIndex);
        is.read(exceptionInfo.attributeLength);
        is.read(exceptionInfo.numberOfException);
        exceptionInfo.exceptionIndexTable = new byte[2 * ByteUtils.bytesToInteger(exceptionInfo.numberOfException)];
        is.read(exceptionInfo.exceptionIndexTable);
        return exceptionInfo;
    }

    private SourceFileAttribute readSourceFile(InputStream is) throws IOException {
        SourceFileAttribute sourceFileAttribute = new SourceFileAttribute(classInfo);
        is.read(sourceFileAttribute.attributeLength);
        is.read(sourceFileAttribute.sourceFileIndex);
        return sourceFileAttribute;
    }


    @Override
    public void print() {


        System.out.println("attributes_count:" + attributeCount);
        System.out.println("attributes:");

        for (AttributeInfo attributeInfo : classInfo.getAttributeInfos()) {

            if (attributeInfo instanceof SourceFileAttribute) {
                SourceFileAttribute sourceFileAttribute = (SourceFileAttribute) attributeInfo;
                sourceFileAttribute.print();
            }

        }


    }

    class ExceptionInfo extends AttributeInfo {

        public byte[] numberOfException = new byte[2];

        public byte[] exceptionIndexTable; //长度是numberOfException的两倍，两个字节为一个单位，指向常量池中ConstantClassInfo类型的常量


    }


}
