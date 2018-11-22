package com.wya.utils.utils;

import android.content.Context;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/22
 * desc   :
 * version: 1.0
 */
public class ScreenUtils {
	/**
	 * dp转px
	 *
	 * @param dp
	 * @return
	 */
	public static int dip2px(Context context,int dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dp * density + 0.5);
	}

	/**
	 * px转换dip
	 */
	public static int px2dp(Context context,int px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * px转换sp
	 */
	public static int px2sp(Context context,int pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * sp转换px
	 */
	public static int sp2px(Context context,int spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);

	}
}