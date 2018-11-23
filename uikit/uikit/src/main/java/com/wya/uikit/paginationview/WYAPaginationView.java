package com.wya.uikit.paginationview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/23
 * desc   :
 * version: 1.0
 */
public class WYAPaginationView extends FrameLayout {

	public WYAPaginationView(@NonNull Context context) {
		super(context);
		init();
	}

	public WYAPaginationView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WYAPaginationView(@NonNull Context context, @Nullable AttributeSet attrs, int
			defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

	}
}
