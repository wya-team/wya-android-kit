package com.wya.uikit.tabbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/20
 * desc   :	底部导航栏
 * version: 1.0
 */
public class TabBar extends BottomNavigationView {


	public TabBar(Context context) {
		super(context);
	}

	public TabBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TabBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * 取消动画
	 */
	@SuppressLint("RestrictedApi")
	public void disableShiftMode() {

		BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
		try {
			//28之后代码不一样需要改变
			if (Build.VERSION.SDK_INT < 28) {
				Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
				shiftingMode.setAccessible(true);
				shiftingMode.setBoolean(menuView, false);
				shiftingMode.setAccessible(false);
				for (int i = 0; i < menuView.getChildCount(); i++) {
					BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt
							(i);
					Class<? extends BottomNavigationItemView> itemClass = item.getClass();
					Method setShiftingMode = itemClass.getDeclaredMethod("setShiftingMode",
							boolean.class);
					setShiftingMode.invoke(item, false);
					item.setChecked(item.getItemData().isChecked());
				}
			} else {
				Class<? extends BottomNavigationMenuView> menuViewClass = menuView.getClass();
				Method setLabelVisibilityMode = menuViewClass.getDeclaredMethod
						("setLabelVisibilityMode", int.class);
				setLabelVisibilityMode.invoke(menuView, 1);
				for (int i = 0; i < menuView.getChildCount(); i++) {
					BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
					Class<? extends BottomNavigationItemView> itemClass = item.getClass();
					Method setShifting = itemClass.getDeclaredMethod("setShifting", boolean.class);
					setShifting.invoke(item, false);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
