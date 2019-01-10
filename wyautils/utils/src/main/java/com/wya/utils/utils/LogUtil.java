package com.wya.utils.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * @date: 2018/12/5 14:03
 * @author: Chunjiang Mao
 * @classname: LogUtil
 * @describe: 输出日志
 */

public class LogUtil {
    public static String tagPrefix = "";
    public static boolean showV = true;
    public static boolean showD = true;
    public static boolean showI = true;
    public static boolean showW = true;
    public static boolean showE = true;
    public static boolean showWTF = true;
    public static boolean isShowLog = true;
    
    /**
     * 得到tag（所在类.方法（L:行））
     *
     * @return
     */
    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = stackTraceElement.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s(L:%d)";
        tag = String.format(tag, new Object[]{callerClazzName, stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())});
        //给tag设置前缀
        tag = TextUtils.isEmpty(tagPrefix) ? tag : tagPrefix + ":" + tag;
        return "WYA_LOG:" + tag;
    }
    
    public static void v(String msg) {
        if (showV && isShowLog) {
            String tag = generateTag();
            Log.v(tag, msg);
        }
    }
    
    public static void v(String msg, Throwable tr) {
        if (showV && isShowLog) {
            String tag = generateTag();
            Log.v(tag, msg, tr);
        }
    }
    
    public static void d(String msg) {
        if (showD && isShowLog) {
            String tag = generateTag();
            Log.d(tag, msg);
        }
    }
    
    public static void d(String msg, Throwable tr) {
        if (showD && isShowLog) {
            String tag = generateTag();
            Log.d(tag, msg, tr);
        }
    }
    
    public static void i(String msg) {
        if (showI && isShowLog) {
            String tag = generateTag();
            Log.i(tag, msg);
        }
    }
    
    public static void i(String msg, Throwable tr) {
        if (showI && isShowLog) {
            String tag = generateTag();
            Log.i(tag, msg, tr);
        }
    }
    
    public static void w(String msg) {
        if (showW && isShowLog) {
            String tag = generateTag();
            Log.w(tag, msg);
        }
    }
    
    public static void w(String msg, Throwable tr) {
        if (showW && isShowLog) {
            String tag = generateTag();
            Log.w(tag, msg, tr);
        }
    }
    
    public static void e(String msg) {
        if (showE && isShowLog) {
            String tag = generateTag();
            Log.e(tag, msg);
        }
    }
    
    public static void e(String msg, Throwable tr) {
        if (showE && isShowLog) {
            String tag = generateTag();
            Log.e(tag, msg, tr);
        }
    }
    
    public static void wtf(String msg) {
        if (showWTF && isShowLog) {
            String tag = generateTag();
            Log.wtf(tag, msg);
        }
    }
    
    public static void wtf(String msg, Throwable tr) {
        if (showWTF && isShowLog) {
            String tag = generateTag();
            Log.wtf(tag, msg, tr);
        }
    }
    
}
