package com.wya.uikit.optionmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10
 *  @description     : recyclerview 分割线
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {
	private Paint mPaint;
	private int height;

	public ItemDecoration(Context context, @ColorRes int color, int height) {
		super();
		mPaint = new Paint();
		mPaint.setColor(context.getResources().getColor(color));
		this.height = height;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		int childCount = parent.getChildCount();
		for (int i = 1; i < childCount; i++) {
			View child = parent.getChildAt(i);
			int top = child.getTop() - height;
			int bottom = child.getTop();
			int left = parent.getPaddingLeft();
			int right = parent.getWidth() - parent.getPaddingRight();
			c.drawRect(left, top, right, bottom, mPaint);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
			state) {
		outRect.set(0, 0, 0, height);
	}
}
