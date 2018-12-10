package com.wya.utils.utils;

import android.text.TextUtils;
import android.util.Log;

import com.wya.helper.WYAConstants;
import com.wya.wyautils.BuildConfig;

 /**
  * 创建日期：2018/12/5 14:03
  * 作者： Mao Chunjiang
  * 文件名称：LogUtil
  * 类说明：
  */

public class LogUtil {
     public static String tagPrefix = "";
     public static boolean showV = true;
     public static boolean showD = true;
     public static boolean showI = true;
     public static boolean showW = true;
     public static boolean showE = true;
     public static boolean showWTF = true;
     /**
      * 得到tag（所在类.方法（L:行））
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
         return "WYA_LOG:"+tag;
     }

     public static void v(String msg) {
         if (showV && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.v(tag, msg);
         }
     }

     public static void v(String msg, Throwable tr) {
         if (showV && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.v(tag, msg, tr);
         }
     }

     public static void d(String msg) {
         if (showD && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.d(tag, msg);
         }
     }

     public static void d(String msg, Throwable tr) {
         if (showD && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.d(tag, msg, tr);
         }
     }

     public static void i(String msg) {
         if (showI && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.i(tag, msg);
         }
     }

     public static void i(String msg, Throwable tr) {
         if (showI && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.i(tag, msg, tr);
         }
     }

     public static void w(String msg) {
         if (showW && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.w(tag, msg);
         }
     }

     public static void w(String msg, Throwable tr) {
         if (showW && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.w(tag, msg, tr);
         }
     }

     public static void e(String msg) {
         if (showE && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.e(tag, msg);
         }
     }

     public static void e(String msg, Throwable tr) {
         if (showE && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.e(tag, msg, tr);
         }
     }

     public static void wtf(String msg) {
         if (showWTF && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.wtf(tag, msg);
         }
     }

     public static void wtf(String msg, Throwable tr) {
         if (showWTF && WYAConstants.IS_SHOW_LOG) {
             String tag = generateTag();
             Log.wtf(tag, msg, tr);
         }
     }

}
