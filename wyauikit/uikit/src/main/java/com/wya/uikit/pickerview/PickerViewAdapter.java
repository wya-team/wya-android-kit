package com.wya.uikit.pickerview;

import android.support.annotation.NonNull;

import com.wya.uikit.pickerview.wheelview.adapter.WheelAdapter;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/06
 * desc   :
 * version: 1.0
 */
public class PickerViewAdapter<T> implements WheelAdapter {
	private List<T>mData;

	public PickerViewAdapter(@NonNull  List<T> data) {
		mData = data;
	}

	@Override
	public int getItemsCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int index) {
		return mData.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return mData.indexOf(o);
	}

}
