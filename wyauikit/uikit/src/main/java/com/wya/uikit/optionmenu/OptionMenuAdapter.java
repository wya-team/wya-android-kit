package com.wya.uikit.optionmenu;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/26
 * desc   :
 * version: 1.0
 */
public abstract class OptionMenuAdapter<T> extends RecyclerView.Adapter<OptionMenuViewHolder> {
	private List<T> mData;
	private int layoutId;
	private String TAG = "OptionMenuAdapter";

	public OptionMenuAdapter(List<T> data, @LayoutRes int layoutId) {
		mData = data;
		this.layoutId = layoutId;
	}

	@NonNull
	@Override
	public OptionMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
		return new OptionMenuViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull OptionMenuViewHolder holder, int position) {
		convert(holder, mData.get(position));
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}


	protected abstract void convert(OptionMenuViewHolder viewHolder, T item);
}
