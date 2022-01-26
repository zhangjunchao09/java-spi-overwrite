package com.zjc.overwrite.parseclass.Handle;

import com.zjc.overwrite.parseclass.Attribute.Attribute;
import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantInvokeDynamicInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantMethodHandleInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantMethodTypeInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsDoubleInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsFieldrefInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsFloatInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsIntegerInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsInterfaceMethodrefInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsLongInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsMethodrefInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsNameAndTypeInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsStringInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantsUtf8Info;
import com.zjc.overwrite.parseclass.Field.Field;
import com.zjc.overwrite.parseclass.Method.Method;
import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ClassInfoHandle implements Handle {

    private List<Handle> handles = new LinkedList<>();

    ClassInfo classInfo;


    public ClassInfoHandle() {
        classInfo = new ClassInfo();
        //step7 读取接口
        handles.add(new InterfaceHandle(classInfo));
        //step8 读取字段表集合
        handles.add(new Field(classInfo));
        //step9 读取方法表
        handles.add(new Method(classInfo));
        //step10 读取属性表
        handles.add(new Attribute(classInfo));
    }

    @Override
    public void read(InputStream is) {
        //step1 读取魔数
        readMagic(is);
        //step2 读取版本号
        readVersion(is);

        //step3 读取常量池
        ConstantHandle constantHandle = new ConstantHandle(classInfo);
        constantHandle.read(is);
        constantHandle.print();

        //step4 读取访问标识
        readAccessFlag(is);

        //step5 读取本类名
        readThisClass(is);

        //step6 读取父类
        readSuperClass(is);

        handles.forEach(h -> {
            h.read(is);
            h.print();
        });
    }

    @Override
    public void print() {

    }

    private void readMagic(InputStream is) {
        try {
            is.read(classInfo.getMagicBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readVersion(InputStream is) {
        try {
            is.read(classInfo.getMinorVersionBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            is.read(classInfo.getMajorVersionBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readAccessFlag(InputStream is) {
        byte[] bytes = new byte[2];
        try {
            is.read(bytes);

            System.out.println("access_flags: " + classInfo.getAccessFlag().calculateAccessFlag(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readThisClass(InputStream is) {
        byte[] bytes = new byte[2];
        try {
            is.read(bytes);
            classInfo.setThisClass(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSuperClass(InputStream is) {
        byte[] bytes = new byte[2];
        try {
            is.read(bytes);

            classInfo.setSuperClass(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInterfaces(InputStream is) {
        byte[] bytes = new byte[2];
        try {
            is.read(bytes);
            Integer interfaceCount = ByteUtils.bytesToInteger(bytes);
            System.out.println("interface_count: " + interfaceCount);
            System.out.print("interfaces[]: ");

            for (int i = 0; i < interfaceCount; i++) {
                is.read(bytes);
                classInfo.addInterfaces(bytes);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
}
