package com.zjc.overwrite.example.services;

import com.zjc.overwrite.services.encrypt;

public class ShaEncrypt implements encrypt {

    @Override
    public String deCode(String str) {
        String s = "Sha加密:" + str;
        System.out.println(s);
        return s;
    }

    @Override
    public String enCode(String str) {
        String s = "Sha解密:" + str;
        System.out.println(s);
        return s;
    }
}
