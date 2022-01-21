package com.zjc.overwrite.parseclass.Attribute;

import com.zjc.overwrite.utils.ByteUtils;

public class AttributeInfo {

    public byte[] attributeNameIndex = new byte[2];//指向常量池中ConstantUtf8Info类型的常量
    public byte[] attributeLength = new byte[4];
    public byte[] info;

    public AttributeInfo() {
    }


    @Override
    public String toString() {
        return ByteUtils.bytesToHexString(attributeNameIndex) + ByteUtils.bytesToHexString(attributeLength) + ByteUtils.bytesToHexString(info);
    }
}
