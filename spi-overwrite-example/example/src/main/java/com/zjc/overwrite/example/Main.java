package com.zjc.overwrite.example;

import com.zjc.overwrite.services.encrypt;
import com.zjc.overwrite.servicesLoad.ServicesLoadFromClassFile;

public class Main {
    public static void main(String[] args) {
        ServicesLoadFromClassFile servicesLoadFromResources = new ServicesLoadFromClassFile(encrypt.class);
        Class<?> clMd5 = servicesLoadFromResources.loadClass("com.zjc.overwrite.example.services.Md5Encrypt2");
        try {
            encrypt p = encrypt.class.cast(clMd5.newInstance());
            p.deCode("deCode");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Class<?> clSha = servicesLoadFromResources.loadClass("com.zjc.overwrite.example.services.Md5Encrypt");
        try {
            encrypt p = encrypt.class.cast(clSha.newInstance());
            p.deCode("deCode");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
