package com.wya.hardware.camera.util;

import android.util.Log;

import static com.wya.hardware.BuildConfig.DEBUG;

 /**
  * 创建日期：2018/12/5 14:03
  * 作者： Mao Chunjiang
  * 文件名称：LogUtil
  * 类说明：
  */

public class LogUtil {

    private static final String DEFAULT_TAG = "MCJ";

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public static void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    public static void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }
}
