package com.wya.uikit.paginationview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wya.uikit.R;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/24
 * desc   : WYAPaginationDot
 * version: 1.0
 */
public class WYAPaginationDot extends LinearLayout {
	private Context mContext;
	private int mDotBg;
	private ViewPager mViewPager;

	public WYAPaginationDot(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WYAPaginationDot(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
				.WYAPaginationDot);
		int dotNum = typedArray.getInteger(R.styleable.WYAPaginationDot_dotNumber, 1);
		mDotBg = typedArray.getResourceId(R.styleable.WYAPaginationDot_dotBackgroundResource,
				R.drawable.pagination_selector_dot_solid);

		typedArray.recycle();
		addDot(dotNum);
	}


	/**
	 * relate to ViewPager
	 *
	 * @param viewPager viewPager
	 */
	public void setUpWithViewPager(ViewPager viewPager) {
		if (viewPager.getAdapter() == null) {
			throw new IllegalArgumentException("Viewpager must set adapter!");
		}
		int count = viewPager.getAdapter().getCount();
		addDot(count);
		mViewPager = viewPager;
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int
					positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				int childCount = getChildCount();
				for (int i = 0; i < childCount; i++) {
					View child = getChildAt(i);
					if (position == i) {
						child.setSelected(true);
					} else {
						child.setSelected(false);
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	/**
	 * a method to set number
	 *
	 * @param num dot's number
	 */
	public void setPointNumber(int num) {
		if (mViewPager == null) {
			addDot(num);
		}
	}

	/**
	 * add dot
	 *
	 * @param num dot's number
	 */
	private void addDot(int num) {
		removeAllViews();
		for (int i = 0; i < num; i++) {
			ImageView dot = new ImageView(mContext);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams
					.WRAP_CONTENT);
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			dot.setLayoutParams(layoutParams);
			dot.setImageResource(mDotBg);
			addView(dot);
		}
		getChildAt(0).setSelected(true);
		invalidate();
	}

}
