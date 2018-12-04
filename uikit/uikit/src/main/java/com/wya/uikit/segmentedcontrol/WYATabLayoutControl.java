package com.wya.uikit.segmentedcontrol;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/21
 * desc   : TabLayout控制器
 * version: 1.0
 */
public class WYATabLayoutControl {

	/**
	 * mTestView控制下划线宽度与文字一样
	 *
	 * @param layout Tablayout
	 */
	public static void lineWidth(final TabLayout layout) {
		try {
			LinearLayout mTabStrip = (LinearLayout) layout.getChildAt(0);
			for (int i = 0; i < mTabStrip.getChildCount(); i++) {
				View tabView = mTabStrip.getChildAt(i);

				Field mTextViewField = null;
				if (hasField(tabView.getClass(),"mTextView")) {
					mTextViewField = tabView.getClass().getDeclaredField("mTextView");
				} else {
					mTextViewField = tabView.getClass().getDeclaredField("textView");
				}
				mTextViewField.setAccessible(true);
				TextView mTextView = (TextView) mTextViewField.get(tabView);

				tabView.setPadding(0, 0, 0, 0);

				int width = mTextView.getWidth();
				if (width == 0) {
					mTextView.measure(0, 0);
					width = mTextView.getMeasuredWidth();
				}

				int tab = tabView.getWidth();
				if (tab == 0) {
					tabView.measure(0, 0);
					tab = tabView.getMeasuredWidth();
				}

				//这里设置width没起作用
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView
						.getLayoutParams();
				params.width = width;
				params.leftMargin = (tab - width - 10) / 2;
				params.rightMargin = (tab - width - 10) / 2;
				tabView.setLayoutParams(params);
				tabView.invalidate();
			}

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}


	private static boolean hasField(Class cls, String field) {
		Field[] fields = cls.getFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals(field)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * mTestView控制下划线宽度与文字一样
	 *
	 * @param layout Tablayout
	 */
	public static void clear(final TabLayout layout) {
		try {
			LinearLayout mTabStrip = (LinearLayout) layout.getChildAt(0);
			for (int i = 0; i < mTabStrip.getChildCount(); i++) {
				View tabView = mTabStrip.getChildAt(i);

				Field mTextViewField = null;
				if (hasField(tabView.getClass(),"mTextView")) {
					mTextViewField = tabView.getClass().getDeclaredField("mTextView");
				} else {
					mTextViewField = tabView.getClass().getDeclaredField("textView");
				}
				mTextViewField.setAccessible(true);
				TextView mTextView = (TextView) mTextViewField.get(tabView);

				tabView.setPadding(0, 0, 0, 0);

				int width = mTextView.getWidth();
				if (width == 0) {
					mTextView.measure(0, 0);
					width = mTextView.getMeasuredWidth();
				}

				int tab = tabView.getWidth();
				if (tab == 0) {
					tabView.measure(0, 0);
					tab = tabView.getMeasuredWidth();
				}

				//这里设置width没起作用
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView
						.getLayoutParams();
				params.width = width;
				params.leftMargin = 0;
				params.rightMargin = 0;
				tabView.setLayoutParams(params);
				tabView.invalidate();
			}

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
}
