package com.zjc.overwrite.parseclass.Handle;

import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InterfaceHandle implements Handle {

    ClassInfo classInfo;

    public InterfaceHandle(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public void read(InputStream is) {
        byte[] bytes = new byte[2];
        try {
            is.read(bytes);
            Integer interfaceCount = ByteUtils.bytesToInteger(bytes);

            for (int i = 0; i < interfaceCount; i++) {
                is.read(bytes);
                classInfo.addInterfaces(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        List<String> interfaces = classInfo.getInterfaces();
        System.out.println("interface_count: " + interfaces.size());
        System.out.print("interfaces[]: ");
        interfaces.stream().forEach(i -> System.out.print(i));


    }
}
