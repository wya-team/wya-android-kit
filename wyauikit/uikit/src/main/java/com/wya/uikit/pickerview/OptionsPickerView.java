package com.wya.uikit.pickerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wya.uikit.pickerview.wheelview.listener.OnItemSelectedListener;
import com.wya.uikit.pickerview.wheelview.view.WheelView;

import java.util.List;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10
 *  @description     : 三级联动布局
 */
public class OptionsPickerView<T> extends LinearLayout {
	private Context mContext;
	private WheelView mWheelView1;
	private WheelView mWheelView2;
	private WheelView mWheelView3;
	private List<T> mData1;
	private List<List<T>> mData2;
	private List<List<List<T>>> mData3;
	private int index1;
	private int index2;
	private int index3;
	private OnItemSelectedListener mSelectedListener1;
	private OnItemSelectedListener mSelectedListener2;
	private OnItemSelectedListener mSelectedListener3;


	public OptionsPickerView(@NonNull Context context) {
		this(context, null);
	}

	public OptionsPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OptionsPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int
			defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		setOrientation(HORIZONTAL);

		initView();
	}


	private void initView() {

		mWheelView1 = new WheelView(mContext);
		mWheelView2 = new WheelView(mContext);
		mWheelView3 = new WheelView(mContext);
		LayoutParams layoutParams1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup
						.LayoutParams.MATCH_PARENT);
		layoutParams1.weight = 1;
		LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup
						.LayoutParams.MATCH_PARENT);
		layoutParams2.weight = 1;
		LayoutParams layoutParams3 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup
						.LayoutParams.MATCH_PARENT);
		layoutParams3.weight = 1;
		addView(mWheelView1, layoutParams1);
		addView(mWheelView2, layoutParams2);
		addView(mWheelView3, layoutParams3);

	}

	public OptionsPickerView setData(List<T> option1Items, List<List<T>> option2Items,
									 List<List<List<T>>> option3Items) {
		mData1 = option1Items;
		mData2 = option2Items;
		mData3 = option3Items;

		addLinked();
		return this;
	}

	public OptionsPickerView setData(List<T> option1Items, List<List<T>> option2Items) {
		this.setData(option1Items, option2Items, null);
		return this;
	}

	public OptionsPickerView setData(List<T> option1Items) {
		this.setData(option1Items, null, null);

		return this;
	}

	private void addLinked() {

		mWheelView1.setAdapter(new PickerViewAdapter<>(mData1));
		if (mData2 != null) {
			mWheelView2.setAdapter(new PickerViewAdapter<>(mData2.get(0)));
			mWheelView2.setCurrentItem(0);
		}
		if (mData3 != null) {
			mWheelView3.setAdapter(new PickerViewAdapter<>(mData3.get(0).get(0)));
			mWheelView3.setCurrentItem(0);
		}
		mWheelView1.setCurrentItem(0);


		mSelectedListener1 = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				index1 = index;
				if (mData2 != null) {
					List<T> list = mData2.get(index);
					mWheelView2.setAdapter(new PickerViewAdapter<>(list));
					int indexs = list.size() > index2 ? index2 : list.size()-1;
					mWheelView2.setCurrentItem(indexs);
					mSelectedListener2.onItemSelected(indexs);
				}
			}
		};
		mSelectedListener2 = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				try {
					index2 = index;
					if (mData3 != null) {
						List<T> list = mData3.get(index1).get(index);
						mWheelView3.setAdapter(new PickerViewAdapter<>(list));
						int indexs = list.size() > index3 ? index3 : list.size()-1;
						mWheelView3.setCurrentItem(indexs);
						mSelectedListener3.onItemSelected(indexs);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		mSelectedListener3 = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				index3 = index;
			}
		};


		mWheelView1.setOnItemSelectedListener(mSelectedListener1);
		mWheelView2.setOnItemSelectedListener(mSelectedListener2);
		mWheelView3.setOnItemSelectedListener(mSelectedListener3);

		if (mData2 == null) {
			mWheelView2.setVisibility(GONE);
		}
		if (mData3 == null) {
			mWheelView3.setVisibility(GONE);
		}
	}


	public OptionsPickerView setCycle(boolean isCycle) {
		mWheelView1.setCyclic(isCycle);
		mWheelView2.setCyclic(isCycle);
		mWheelView3.setCyclic(isCycle);
		return this;
	}

	public int getIndex1() {
		return this.index1;
	}

	public int getIndex2() {
		return this.index2;
	}

	public int getIndex3() {
		return index3;
	}


	public OptionsPickerView setNPData(List<T> data1) {
		this.setNPData(data1, null);
		return this;
	}

	public OptionsPickerView setNPData(List<T> data1, List<T> data2) {
		this.setNPData(data1, data2, null);
		return this;
	}

	public OptionsPickerView setNPData(List<T> data1, List<T> data2, List<T> data3) {

		mWheelView1.setAdapter(new PickerViewAdapter<>(data1));
		if (data2 != null) {
			mWheelView2.setAdapter(new PickerViewAdapter<>(data2));
		} else {
			mWheelView2.setVisibility(GONE);
		}
		if (data3 != null) {
			mWheelView3.setAdapter(new PickerViewAdapter<>(data3));
		} else {
			mWheelView3.setVisibility(GONE);
		}

		mSelectedListener1 = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				index1 = index;
			}
		};
		mSelectedListener2 = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				index2 = index;
			}
		};
		mSelectedListener3 = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				index3 = index;
			}
		};

		mWheelView1.setOnItemSelectedListener(mSelectedListener1);
		mWheelView2.setOnItemSelectedListener(mSelectedListener2);
		mWheelView3.setOnItemSelectedListener(mSelectedListener3);

		return this;
	}

	public OptionsPickerView setDividerColor(int color) {
		mWheelView1.setDividerColor(color);
		mWheelView2.setDividerColor(color);
		mWheelView3.setDividerColor(color);
		return this;
	}

	public OptionsPickerView setTextSize(float textSize) {
		mWheelView1.setTextSize(textSize);
		mWheelView2.setTextSize(textSize);
		mWheelView3.setTextSize(textSize);
		return this;
	}

	public OptionsPickerView setCenterTextColor(int color) {
		mWheelView1.setTextColorCenter(color);
		mWheelView2.setTextColorCenter(color);
		mWheelView3.setTextColorCenter(color);
		return this;
	}

	public OptionsPickerView setOuterTextColor(int color) {
		mWheelView1.setTextColorOut(color);
		mWheelView2.setTextColorOut(color);
		mWheelView3.setTextColorOut(color);
		return this;
	}
}
