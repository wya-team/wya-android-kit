package com.wya.utils.utils;

import android.content.Context;

/**
 * 创建日期：2018/11/29 9:23
 * 作者： Mao Chunjiang
 * 文件名称： DensityUtil
 * 类说明：像素转换
 */

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     *
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
