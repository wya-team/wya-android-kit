package com.wya.uikit.pickerview;

import com.wya.uikit.pickerview.WheelView.adapter.WheelAdapter;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/07
 * desc   :
 * version: 1.0
 */
public class TimePickerAdapter implements WheelAdapter<Integer> {

	private int start,end,space;

	public TimePickerAdapter(int start, int end,int space) {
		this.start = start;
		this.end = end;
		this.space = space;
	}

	@Override
	public int getItemsCount() {
		return (end-start+1)/space;
	}

	@Override
	public Integer getItem(int index) {
		return start+index*space;
	}

	@Override
	public int indexOf(Integer item) {
		return (item-start)/space;
	}

}
