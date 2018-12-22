package com.wya.uikit.choicemenu;

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
public abstract class ChoiceMenuAdapter<T> extends RecyclerView.Adapter<ChoiceMenuViewHolder> {
	private List<T> mData;
	private int layoutId;
	private String TAG = "ChoiceMenuAdapter";

	public ChoiceMenuAdapter(List<T> data, @LayoutRes int layoutId) {
		mData = data;
		this.layoutId = layoutId;
	}

	@NonNull
	@Override
	public ChoiceMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
		return new ChoiceMenuViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ChoiceMenuViewHolder holder, int position) {
		convert(holder, mData.get(position));
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}


	protected abstract void convert(ChoiceMenuViewHolder viewHolder, T item);
}
