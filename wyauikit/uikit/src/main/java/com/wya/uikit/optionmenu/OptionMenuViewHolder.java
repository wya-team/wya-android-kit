package com.wya.uikit.optionmenu;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/26
 * desc   :
 * version: 1.0
 */
public class OptionMenuViewHolder extends RecyclerView.ViewHolder {
	private SparseArray<View> mView;

	public OptionMenuViewHolder(View itemView) {
		super(itemView);
		mView = new SparseArray<>();
	}

	public <T extends View> T getView(@IdRes int viewId) {
		View view = mView.get(viewId);
		if (view == null) {
			view = itemView.findViewById(viewId);
			mView.put(viewId, view);
		}
		return (T) view;
	}



	public OptionMenuViewHolder setText(@IdRes int id, String value) {
		TextView view = getView(id);
		view.setText(value);
		return this;
	}

	public OptionMenuViewHolder setText(@IdRes int id, @StringRes  int value) {
		TextView view = getView(id);
		view.setText(value);
		return this;
	}

}
