package com.wya.example.module.utils;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;

import com.wya.example.module.base.WYAApplication;

import java.lang.reflect.Field;

public class WYAScreenUtil {
    /**
     * Get statusbar height.
     *
     * @return ：
     */
    @SuppressLint("PrivateApi")
    public static int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = WYAApplication.getApplication().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
    
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = WYAApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = WYAApplication.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = WYAApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    /**
     * Get screen width, in pixels.
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = WYAApplication.getApplication().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
    
    /**
     * Get screen height, in pixels.
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = WYAApplication.getApplication().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
    
    public static float getScreendensity() {
        DisplayMetrics dm = WYAApplication.getApplication().getResources().getDisplayMetrics();
        return dm.density;
    }
    
}
