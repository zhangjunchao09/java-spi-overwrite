package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.utils.ByteUtils;

import java.io.IOException;
import java.io.InputStream;

public class ConstantInvokeDynamicInfo extends ConstantBase{
    public byte[] indexBootstrapMethodAttr = new byte[2];//值必须是当前Class文件中引导方法表的bootstrap_methods[]数组的有效索引
    public byte[] indexNameAndType = new byte[2];//值必须是对当前常量池的有效索引，常量池在该索引处的项必须是ConstantsNameAndType类型。

    public ConstantInvokeDynamicInfo(){
        description="CONSTANT_Invoke-Dynamic_info";
    }

    @Override
    public void read(InputStream is) {
        try {
            is.read(indexBootstrapMethodAttr);
            is.read(indexNameAndType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void print() {
        System.out.println(description+"{");
        System.out.print("  indexBootstrapMethodAttr: ");
        for(int i = 0;i<indexBootstrapMethodAttr.length;i++){
            System.out.print(ByteUtils.bytesToHexString(indexBootstrapMethodAttr[i]));
        }
        System.out.println();
        System.out.print("  indexNameAndType: ");
        for(int i = 0;i<indexNameAndType.length;i++){
            System.out.print(ByteUtils.bytesToHexString(indexNameAndType[i]));
        }
        System.out.println();
        System.out.println("}");
    }
}