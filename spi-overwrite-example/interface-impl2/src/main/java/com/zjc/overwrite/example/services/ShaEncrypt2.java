package com.zjc.overwrite.example.services;

import com.zjc.overwrite.services.encrypt;

public class ShaEncrypt2 implements encrypt {

    @Override
    public String deCode(String str) {
        String s = "Sha2加密:" + str;
        System.out.println(s);
        return s;
    }

    @Override
    public String enCode(String str) {
        String s = "Sha2解密:" + str;
        System.out.println(s);
        return s;
    }
}
