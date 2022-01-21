package com.zjc.overwrite.parseclass;

import com.zjc.overwrite.utils.ByteUtils;

/**
 * 用于描述类或者接口层次的访问信息
 */
public class AccessFlag {

    private int currentIndex = 0;
    private int[] flagValueArray = new int[8];
    private String[] flagNameArray = new String[8];


    public AccessFlag(){
        add(1,"ACC_PUBLIC");
        add(16,"ACC_FINAL");
        add(32,"ACC_SUPER");
        add(512,"ACC_INTERFACE");
        add(1024,"ACC_ABSTRACT");
        add(4096,"ACC_SYNTHETIC");
        add(8192,"ACC_ANNOTATION");
        add(16384,"ACC_ENUM");
    }


    private void add(int value,String name){
        flagValueArray[currentIndex] = value;
        flagNameArray[currentIndex] = name;
        currentIndex ++ ;
    }

    public String calculateAccessFlag(byte[] bytes){

        String result = "";
        Integer accessValue = ByteUtils.bytesToInteger(bytes);
        for (int i = 0;i<flagValueArray.length;i++) {
            if((accessValue & flagValueArray[i]) != 0){
               result += flagNameArray[i] + ",";
            }
        }
        if(result.endsWith(",")){
            result = result.substring(0,result.length()-1);
        }
        return result;
    }




}
