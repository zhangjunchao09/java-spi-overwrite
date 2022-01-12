package com.zjc.overwrite.example.services;


import com.zjc.overwrite.services.encrypt;

public class Md5Encrypt implements encrypt {

    @Override
    public String deCode(String str) {
        String s = "Md5加密:" + str;
        System.out.println(s);
        return s;
    }

    @Override
    public String enCode(String str) {
        String s = "Md5解密:" + str;
        System.out.println(s);
        return s;
    }
}
