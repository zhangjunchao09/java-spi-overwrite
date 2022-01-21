package com.zjc.overwrite.parseclass.Field;

import com.zjc.overwrite.parseclass.Attribute.AttributeInfo;
import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantsUtf8Info;
import com.zjc.overwrite.parseclass.Handle.Handle;
import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class Field implements Handle {

    Integer fieldCount = 0;

    private int currentIndex = 0;
    private int[] flagValueArray = new int[9];
    private String[] flagNameArray = new String[9];

    private ClassInfo classInfo;

    public Field(ClassInfo classInfo) {

        this.classInfo = classInfo;

        add(1, "ACC_PUBLIC");
        add(2, "ACC_PRIVATE");
        add(4, "ACC_PROTECTED");
        add(8, "ACC_STATIC");
        add(16, "ACC_FINAL");
        add(64, "ACC_VOLATILE");
        add(128, "ACC_ITRANSIENT");
        add(4096, "ACC_SYNTHETIC");
        add(16384, "ACC_ENUM");
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
        try {
            is.read(bytes);
            fieldCount = ByteUtils.bytesToInteger(bytes);
            for (int i = 0; i < fieldCount; i++) {
                FieldInfo fieldInfo = new FieldInfo();
                bytes = new byte[2];
                is.read(bytes);
                fieldInfo.accessFlag = bytes;

                bytes = new byte[2];
                is.read(bytes);
                fieldInfo.nameIndex = bytes;

                bytes = new byte[2];
                is.read(bytes);
                fieldInfo.descriptorIndex = bytes;

                bytes = new byte[2];
                is.read(bytes);
                fieldInfo.attributeCount = bytes;


                Integer attrCount = ByteUtils.bytesToInteger(fieldInfo.attributeCount);
                if (attrCount > 0) {
                    fieldInfo.attributes = new AttributeInfo[attrCount];
                    for (int j = 0; j < attrCount; j++) {
                        AttributeInfo attributeInfo = new AttributeInfo();
                        is.read(attributeInfo.attributeNameIndex);
                        is.read(attributeInfo.attributeLength);

                        int attrLength = ByteUtils.bytesToInteger(attributeInfo.attributeLength);
                        if (attrLength > 0) {
                            attributeInfo.info = new byte[attrLength];
                            is.read(attributeInfo.info);
                        }
                        fieldInfo.attributes[j] = attributeInfo;
                    }

                }


                classInfo.getFieldInfoList().add(fieldInfo);
            }


            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void print() {

        System.out.println("fields_count: " + fieldCount);


        for (FieldInfo fieldInfo : classInfo.getFieldInfoList()) {
            System.out.println("fields_info{");
            System.out.println("  access_flags: " + calculateAccessFlag(fieldInfo.accessFlag));

            ConstantBase constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(fieldInfo.nameIndex) - 1);
            ConstantsUtf8Info constantsUtf8Info = (ConstantsUtf8Info) constantBase;

            System.out.println("  name_index: " + constantsUtf8Info.getValue());

            constantBase = classInfo.getConstantList().get(ByteUtils.bytesToInteger(fieldInfo.descriptorIndex) - 1);
            constantsUtf8Info = (ConstantsUtf8Info) constantBase;

            System.out.println("  descriptor_index: " + constantsUtf8Info.getValue());
            System.out.println("  attributes_count: " + ByteUtils.bytesToInteger(fieldInfo.attributeCount));
            if (fieldInfo.attributes != null) {
                System.out.println("  attributes: ");

                for (AttributeInfo attribute : fieldInfo.attributes) {
                    System.out.println("    attribute_info:{");

                    int index = ByteUtils.bytesToInteger(attribute.attributeNameIndex);
                    ConstantBase constantBase2 = classInfo.getConstantList().get(index - 1);
                    ConstantsUtf8Info constantsUtf8Info2 = (ConstantsUtf8Info) constantBase2;
                    System.out.println("      attributeNameIndex: " + constantsUtf8Info2.getValue());
                    System.out.println("      attributeLength: " + ByteUtils.bytesToHexString(attribute.attributeLength));
                    System.out.println("      info: " + new String(attribute.info));
                    System.out.println("    }");

                }

            }
            System.out.println("}");

        }

    }


}
