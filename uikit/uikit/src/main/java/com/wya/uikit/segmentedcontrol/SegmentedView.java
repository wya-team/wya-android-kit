package com.wya.uikit.segmentedcontrol;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/21
 * desc   :
 * version: 1.0
 */
public class SegmentedView extends LinearLayout {

	public SegmentedView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SegmentedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
}
