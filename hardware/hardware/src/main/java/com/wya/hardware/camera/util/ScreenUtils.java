package com.wya.hardware.camera.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

 /**
  * 创建日期：2018/12/5 14:01
  * 作者： Mao Chunjiang
  * 文件名称：ScreenUtils
  * 类说明：获取屏幕宽高
  */

public class ScreenUtils {
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
}
