package com.zjc.overwrite;

import com.zjc.overwrite.parseclass.Handle.ClassInfoHandle;

import java.io.File;
import java.io.FileInputStream;

public class ClassParseTest {

    public static void main(String[] args) throws Exception {
        File file = new File("E:\\classs\\Md5Encrypt2.class");

        FileInputStream fis = new FileInputStream(file);

        ClassInfoHandle classInfoHandle = new ClassInfoHandle();
        classInfoHandle.read(fis);

    }


    public String bytesToHexString(byte src) {
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (1 == hv.length()) {
            hv = "0" + hv;
        }
        return hv;
    }


}
