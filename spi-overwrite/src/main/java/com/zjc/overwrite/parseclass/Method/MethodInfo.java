package com.zjc.overwrite.parseclass.Method;

import com.zjc.overwrite.parseclass.Attribute.AttributeInfo;

public class MethodInfo {
    public byte[] accessFlag = new byte[2];
    public byte[] nameIndex = new byte[2];
    public byte[] descriptorIndex = new byte[2];
    public byte[] attributeCount = new byte[2];
    public AttributeInfo[] attributes;

    MethodInfo() {
    }

}
