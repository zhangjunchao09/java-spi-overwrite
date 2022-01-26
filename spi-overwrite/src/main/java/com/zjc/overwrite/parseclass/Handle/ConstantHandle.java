package com.zjc.overwrite.parseclass.Handle;

import com.zjc.overwrite.parseclass.ClassInfo;
import com.zjc.overwrite.parseclass.Constant.ConstantBase;
import com.zjc.overwrite.parseclass.Constant.ConstantsFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConstantHandle implements Handle {

    ClassInfo classInfo;

    public ConstantHandle(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public void read(InputStream is) {
        byte[] bytes = new byte[2];
        try {
            is.read(bytes);

            int constantNum = (bytes[0] << 8) + bytes[1] - 1;

            for (int i = 0; i < constantNum; i++) {
                int tag = is.read();

                //每次读取一个字节
                ConstantBase constantBase = ConstantsFactory.getConstantBaseInstance(tag, classInfo);
                constantBase.read(is);
                classInfo.getConstantList().add(constantBase);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {

    }

}
