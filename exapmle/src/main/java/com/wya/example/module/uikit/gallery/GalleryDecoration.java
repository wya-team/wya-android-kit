package com.wya.example.module.uikit.gallery;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 */
public class GalleryDecoration extends RecyclerView.ItemDecoration {
	private int spanCount;
	private int spacing;
	private boolean includeEdge;

	public GalleryDecoration(int spanCount, int spacing, boolean includeEdge) {
		this.spanCount = spanCount;
		this.spacing = spacing;
		this.includeEdge = includeEdge;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
			state) {
		int position = parent.getChildAdapterPosition(view);
		int column = position % spanCount;
		if (includeEdge) {
			outRect.left = spacing - column * spacing / spanCount;
			outRect.right = (column + 1) * spacing / spanCount;
			if (position < spanCount) {
				outRect.top = spacing;
			}
			outRect.bottom = spacing;
		} else {
			outRect.left = column * spacing / spanCount;
			outRect.right = spacing - (column + 1) * spacing / spanCount;
			if (position < spanCount) {
				outRect.top = spacing;
			}
			outRect.bottom = spacing;
		}
	}
}
