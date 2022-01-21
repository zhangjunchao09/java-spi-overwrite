package com.zjc.overwrite.parseclass;

import com.zjc.overwrite.parseclass.Attribute.Attribute;
import com.zjc.overwrite.parseclass.Attribute.AttributeInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantsClassInfo;
import com.zjc.overwrite.parseclass.Field.Field;
import com.zjc.overwrite.parseclass.Field.FieldInfo;
import com.zjc.overwrite.parseclass.Method.Method;
import com.zjc.overwrite.parseclass.Method.MethodInfo;
import com.zjc.overwrite.utils.ByteUtils;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {

    private byte[] magicBytes = new byte[4];

    private byte[] minorVersionBytes = new byte[2];

    private byte[] majorVersionBytes = new byte[2];

    private List<ConstantBase> constantList = new ArrayList<>();

    private AccessFlag accessFlag = new AccessFlag();

    private String thisClass;
    private String superClass;

    private List<String> interfaces = new ArrayList<>();

    private Field field;
    List<FieldInfo> fieldInfoList = new ArrayList<>();
    private Method method;

    List<MethodInfo> methodInfoList = new ArrayList<>();

    private Attribute attribute;
    private List<AttributeInfo> attributeInfos = new ArrayList<>();

    public ClassInfo() {
    }


    /**
     * 传入类、接口的指向索引，从常量池中查出类、接口名
     *
     * @param bytes
     * @return
     */
    public String getClassName(byte[] bytes) {
        Integer index = ByteUtils.bytesToInteger(bytes) - 1;
        ConstantBase constantBase = constantList.get(index);
        ConstantsClassInfo constantsClassInfo = (ConstantsClassInfo) constantBase;
        return constantsClassInfo.getClassName();
    }

    public byte[] getMagicBytes() {
        return magicBytes;
    }

    public void setMagicBytes(byte[] magicBytes) {
        this.magicBytes = magicBytes;
    }

    public byte[] getMinorVersionBytes() {
        return minorVersionBytes;
    }

    public void setMinorVersionBytes(byte[] minorVersionBytes) {
        this.minorVersionBytes = minorVersionBytes;
    }

    public byte[] getMajorVersionBytes() {
        return majorVersionBytes;
    }

    public void setMajorVersionBytes(byte[] majorVersionBytes) {
        this.majorVersionBytes = majorVersionBytes;
    }

    public List<ConstantBase> getConstantList() {
        return constantList;
    }

    public void setConstantList(List<ConstantBase> constantList) {
        this.constantList = constantList;
    }

    public AccessFlag getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(AccessFlag accessFlag) {
        this.accessFlag = accessFlag;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getThisClass() {
        return thisClass;
    }

    public void setThisClass(byte[] bytes) {
        this.thisClass = getClassName(bytes);
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(byte[] bytes) {
        this.superClass = getClassName(bytes);
    }

    public void addInterfaces(byte[] bytes) {
        interfaces.add(getClassName(bytes));
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<MethodInfo> getMethodInfoList() {
        return methodInfoList;
    }

    public void setMethodInfoList(List<MethodInfo> methodInfoList) {
        this.methodInfoList = methodInfoList;
    }

    public List<AttributeInfo> getAttributeInfos() {
        return attributeInfos;
    }

    public void setAttributeInfos(List<AttributeInfo> attributeInfos) {
        this.attributeInfos = attributeInfos;
    }

    public List<FieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public void setFieldInfoList(List<FieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }
}
