package com.zjc.overwrite.parseclass.Method;

import com.zjc.overwrite.parseclass.Attribute.Attribute;
import com.zjc.overwrite.parseclass.Attribute.AttributeInfo;
import com.zjc.overwrite.parseclass.Attribute.CodeAttribute;
import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantsUtf8Info;
import com.zjc.overwrite.parseclass.Handle.Handle;
import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class Method implements Handle {

    Integer methodCount = 0;

    private int currentIndex = 0;
    private int[] flagValueArray = new int[11];
    private String[] flagNameArray = new String[11];

    private ClassInfo classInfo;

    public Method(ClassInfo classInfo) {

        this.classInfo = classInfo;

        add(1, "ACC_PUBLIC");
        add(2, "ACC_PRIVATE");
        add(4, "ACC_PROTECTED");
        add(8, "ACC_STATIC");
        add(16, "ACC_FINAL");
        add(32, "ACC_SYNCHRONIZED");
        add(64, "ACC_BRIDGE");
        add(128, "ACC_VARARGS");
        add(1024, "ACC_ABSTRACT");
        add(2048, "ACC_STRICTFP");
        add(4096, "ACC_SYNTHETIC");
    }

    private void add(int value, String name) {
        flagValueArray[currentIndex] = value;
        flagNameArray[currentIndex] = name;
        currentIndex++;
    }

    private String calculateAccessFlag(byte[] bytes) {

        String result = "";
        Integer accessValue = ByteUtils.bytesToInteger(bytes);
        for (int i = 0; i < flagValueArray.length; i++) {
            if ((accessValue & flagValueArray[i]) != 0) {
                result += flagNameArray[i] + ",";
            }
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }


    @Override
    public void read(InputStream is) {
        byte[] bytes = new byte[2];
        Attribute attribute = new Attribute(classInfo);
        try {
            is.read(bytes);
            methodCount = ByteUtils.bytesToInteger(bytes);
            for (int i = 0; i < methodCount; i++) {
                MethodInfo methodInfo = new MethodInfo();
                bytes = new byte[2];
                is.read(bytes);
                methodInfo.accessFlag = bytes;

                bytes = new byte[2];
                is.read(bytes);
                methodInfo.nameIndex = bytes;

                bytes = new byte[2];
                is.read(bytes);
                methodInfo.descriptorIndex = bytes;

                bytes = new byte[2];
                is.read(bytes);
                methodInfo.attributeCount = bytes;


                Integer attrCount = ByteUtils.bytesToInteger(methodInfo.attributeCount);
                if (attrCount > 0) {
                    methodInfo.attributes = new AttributeInfo[attrCount];
                    for (int j = 0; j < attrCount; j++) {

                        bytes = new byte[2];
                        is.read(bytes);
                        Integer index = ByteUtils.bytesToInteger(bytes);
                        ConstantBase constantBase = classInfo.getConstantList().get(index - 1);
                        ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;

                        if ("Code".equals(constantsUtf8Info.getValue())) {
                            CodeAttribute codeAttribute = attribute.readCode(is);
                            codeAttribute.attributeNameIndex = bytes;
                            methodInfo.attributes[j] = codeAttribute;
                        }
                    }

                }

                classInfo.getMethodInfoList().add(methodInfo);
            }

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void print() {

        System.out.println("method_count: " + methodCount);

        for (MethodInfo methodInfo : classInfo.getMethodInfoList()) {
            System.out.println("method_info{");
            System.out.println("  access_flags: " + calculateAccessFlag(methodInfo.accessFlag));

            ConstantBase constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(methodInfo.nameIndex) - 1);
            ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;

            System.out.println("  name_index: " + constantsUtf8Info.getValue());

            constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(methodInfo.descriptorIndex) - 1);
            constantsUtf8Info = (ConstantsUtf8Info) constantBase;

            System.out.println("  descriptor_index: " + constantsUtf8Info.getValue());
            System.out.println("  attributes_count: " + ByteUtils.bytesToInteger(methodInfo.attributeCount));
            if (methodInfo.attributes != null) {
                System.out.println("  attributes: ");

                for (AttributeInfo attribute : methodInfo.attributes) {
                    if (attribute instanceof CodeAttribute) {
                        ((CodeAttribute) attribute).print();
                    }
                }

            }
            System.out.println("}");

        }

    }


}
