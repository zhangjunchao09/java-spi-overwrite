package com.zjc.overwrite.example;

import com.zjc.overwrite.services.encrypt;
import com.zjc.overwrite.servicesload.ServicesLoadFromClassFile;
import com.zjc.overwrite.servicesload.ServicesLoadFromResources;

public class Main {
    public static void main(String[] args) {
        ServicesLoadFromClassFile servicesLoadFromClassFile = new ServicesLoadFromClassFile(encrypt.class, null);
        Class<?> clMd5 = servicesLoadFromClassFile.getClass("com.zjc.overwrite.example.services.Md5Encrypt2");
        try {
            encrypt p = encrypt.class.cast(clMd5.newInstance());
            p.deCode("deCode");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        ServicesLoadFromResources servicesLoadFromResources = new ServicesLoadFromResources(encrypt.class);
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
