package com.wya.uikit.tabbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10 
 *  @description     : 
 */
public class WYATabBar extends BottomNavigationView {
	private BottomNavigationMenuView mMenuView;
	private BottomNavigationItemView[] mButtons;
	private boolean animationRecord;
	private float mScaleUpFactor;
	private float mScaleDownFactor;
	private float mLargeLabelSize;
	private float mSmallLabelSize;

	public WYATabBar(Context context) {
		super(context);
	}

	public WYATabBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WYATabBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * 取消偏移动画
	 */
	@SuppressLint("RestrictedApi")
	public void disableShiftMode() {

		BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
		try {
			//28之后代码不一样需要改变
			Class<? extends BottomNavigationMenuView> menuViewClass = menuView.getClass();

			if (hasField(menuViewClass,"mShiftingMode")) {
				Field shiftingMode = menuViewClass.getDeclaredField("mShiftingMode");
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

				Method setLabelVisibilityMode = menuViewClass.getDeclaredMethod
						("setLabelVisibilityMode", int.class);
				setLabelVisibilityMode.invoke(menuView, 1);
				for (int i = 0; i < menuView.getChildCount(); i++) {
					BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt
							(i);
					Class<? extends BottomNavigationItemView> itemClass = item.getClass();
					Method setShifting = itemClass.getDeclaredMethod("setShifting", boolean.class);
					setShifting.invoke(item, false);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean hasField(Class cls, String field) {

		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(field)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取消item文字图片点击放大效果
	 * @param enable false 取消
	 */
	@SuppressLint("RestrictedApi")
	public void enableAnimation(boolean enable) {

		// 1. get mMenuView
		BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
		// 2. get mButtons
		BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
		// 3. change field mShiftingMode value in mButtons
		for (BottomNavigationItemView button : mButtons) {
			TextView mSmallLabel, mLargeLabel;
			if (hasField(button.getClass(),"mLargeLabel")) {
				mLargeLabel = getField(button.getClass(), button, "mLargeLabel");
				mSmallLabel = getField(button.getClass(), button, "mSmallLabel");
			} else {
				mLargeLabel = getField(button.getClass(), button, "largeLabel");
				mSmallLabel = getField(button.getClass(), button, "smallLabel");
			}


			// if disable animation, need animationRecord the source value
			if (!enable) {
				if (!animationRecord) {
					animationRecord = true;
					if (hasField(button.getClass(),"mShiftAmount")){
						int mShiftAmount = getField(button.getClass(), button, "mShiftAmount");
						mScaleUpFactor = getField(button.getClass(), button, "mScaleUpFactor");
						mScaleDownFactor = getField(button.getClass(), button, "mScaleDownFactor");
					}else {
						float mShiftAmount = getField(button.getClass(), button, "shiftAmount");
						mScaleUpFactor = getField(button.getClass(), button, "scaleUpFactor");
						mScaleDownFactor = getField(button.getClass(), button, "scaleDownFactor");
					}


					mLargeLabelSize = mLargeLabel.getTextSize();
					mSmallLabelSize = mSmallLabel.getTextSize();

				}
				// disable
				if (hasField(button.getClass(),"mShiftAmount")){
					setField(button.getClass(), button, "mShiftAmount", 0);
					setField(button.getClass(), button, "mScaleUpFactor", 1);
					setField(button.getClass(), button, "mScaleDownFactor", 1);
				}else {
					setField(button.getClass(), button, "shiftAmount", 0);
					setField(button.getClass(), button, "scaleUpFactor", 1);
					setField(button.getClass(), button, "scaleDownFactor", 1);
				}


				// let the mLargeLabel font size equal to mSmallLabel
				mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);


			}
		}
		mMenuView.updateMenuView();
	}

	/**
	 * get private mMenuView
	 *
	 * @return
	 */
	private BottomNavigationMenuView getBottomNavigationMenuView() {
		if (null == mMenuView) {
            if (hasField(BottomNavigationView.class, "mMenuView")) {
                mMenuView = getField(BottomNavigationView.class, this, "mMenuView");
            } else {
                mMenuView = getField(BottomNavigationView.class, this, "menuView");
            }
        }
		return mMenuView;
	}

	/**
	 * get private mButtons in mMenuView
	 *
	 * @return
	 */
	public BottomNavigationItemView[] getBottomNavigationItemViews() {
		if (null != mButtons) {
            return mButtons;
        }
		/*
		 * 1 private final BottomNavigationMenuView mMenuView;
		 * 2 private BottomNavigationItemView[] mButtons;
		 */
		BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
		if (hasField(mMenuView.getClass(),"mButtons")) {
			mButtons = getField(mMenuView.getClass(), mMenuView, "mButtons");
		} else {
			mButtons = getField(mMenuView.getClass(), mMenuView, "buttons");
		}
		return mButtons;
	}

	/**
	 * get private filed in this specific object
	 *
	 * @param targetClass
	 * @param instance    the filed owner
	 * @param fieldName
	 * @param <T>
	 * @return field if success, null otherwise.
	 */
	private <T> T getField(Class targetClass, Object instance, String fieldName) {
		try {
			Field field = targetClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(instance);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * change the field value
	 *
	 * @param targetClass
	 * @param instance    the filed owner
	 * @param fieldName
	 * @param value
	 */
	private void setField(Class targetClass, Object instance, String fieldName, Object value) {
		try {
			Field field = targetClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(instance, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
