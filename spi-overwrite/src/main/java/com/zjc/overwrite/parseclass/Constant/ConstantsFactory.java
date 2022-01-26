package com.zjc.overwrite.parseclass.Constant;

import com.zjc.overwrite.parseclass.ClassInfo;

public class ConstantsFactory {

    public static ConstantBase getConstantBaseInstance(int tag, ClassInfo classInfo) {

        ConstantBase constantBase;

        switch (tag) {
            case 1:
                constantBase = new ConstantsUtf8Info();
                break;
            case 3:
                constantBase = new ConstantsIntegerInfo();
                break;
            case 4:
                constantBase = new ConstantsFloatInfo();
                break;
            case 5:
                constantBase = new ConstantsLongInfo();
                break;
            case 6:
                constantBase = new ConstantsDoubleInfo();
                break;
            case 7:
                constantBase = new ConstantsClassInfo(classInfo);
                break;
            case 8:
                constantBase = new ConstantsStringInfo(classInfo);
                break;
            case 9:
                constantBase = new ConstantsFieldrefInfo(classInfo);
                break;
            case 10:
                constantBase = new ConstantsMethodrefInfo();
                break;
            case 11:
                constantBase = new ConstantsInterfaceMethodrefInfo();
                break;
            case 12:
                constantBase = new ConstantsNameAndTypeInfo(classInfo);
                break;
            case 15:
                constantBase = new ConstantMethodHandleInfo();
                break;
            case 16:
                constantBase = new ConstantMethodTypeInfo();
                break;
            case 18:
                constantBase = new ConstantInvokeDynamicInfo();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tag);
        }
        return constantBase;
    }
}
