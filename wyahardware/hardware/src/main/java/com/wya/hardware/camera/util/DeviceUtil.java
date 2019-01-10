package com.wya.hardware.camera.util;

import android.os.Build;

/**
 * @date: 2018/12/5 14:12
 * @author: Chunjiang Mao
 * @classname: DeviceUtil
 * @describe:
 */

public class DeviceUtil {
    
    private static String[] huaweiRongyao = {
            //荣耀6
            "hwH60",
            //荣耀6 plus
            "hwPE",
            //3c
            "hwH30",
            //3c畅玩版
            "hwHol",
            //3x
            "hwG750",
            //x1
            "hw7D",
            //x1
            "hwChe2",
    };
    
    public static String getDeviceInfo() {
        String handSetInfo =
                "手机型号：" + Build.DEVICE +
                        "\n系统版本：" + Build.VERSION.RELEASE +
                        "\nSDK版本：" + Build.VERSION.SDK_INT;
        return handSetInfo;
    }
    
    public static String getDeviceModel() {
        return Build.DEVICE;
    }
    
    public static boolean isHuaWeiRongyao() {
        int length = huaweiRongyao.length;
        for (int i = 0; i < length; i++) {
            if (huaweiRongyao[i].equals(getDeviceModel())) {
                return true;
            }
        }
        return false;
    }
}
