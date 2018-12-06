package com.wya.uikit.pickerview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.uikit.R;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/06
 * desc   :
 * version: 1.0
 */
public class CustomPickerView<T> extends Dialog implements View.OnClickListener {
	private Context mContext;
	private final String TAG = getClass().getName();
	private OptionsPickerView option_picker_view;
	private TextView pickerTitle, cancel, sure;
	private OnChooseItemListener mOnChooseItemListener;
	private LinearLayout picker_title_content;

	public CustomPickerView(@NonNull Context context, OnChooseItemListener onChooseItemListener) {
		this(context, R.style.WYACustomDialog, onChooseItemListener);
	}

	public CustomPickerView(@NonNull Context context, int themeResId, OnChooseItemListener
			onChooseItemListener) {
		super(context, themeResId);
		mContext = context;
		this.mOnChooseItemListener = onChooseItemListener;
		initView();
	}

	private void initView() {

		View view = LayoutInflater.from(mContext).inflate(R.layout.custom_picker_view, null);
		setContentView(view);
		Window window = getWindow();
		window.setWindowAnimations(R.style.picker_view_slide_anim);
		window.setGravity(Gravity.BOTTOM);
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
		window.setAttributes(layoutParams);

		setCancelable(true);
		setCanceledOnTouchOutside(true);

		option_picker_view = view.findViewById(R.id.option_picker_view);
		cancel = findViewById(R.id.picker_cancel);
		pickerTitle = findViewById(R.id.picker_title);
		sure = findViewById(R.id.picker_sure);
		picker_title_content = findViewById(R.id.picker_title_content);

		cancel.setOnClickListener(this);
		sure.setOnClickListener(this);

	}

	public CustomPickerView setData(List<T> option1Items, List<List<T>> option2Items,
									List<List<List<T>>> option3Items) {
		option_picker_view.setData(option1Items, option2Items, option3Items);

		return this;
	}

	public CustomPickerView setData(List<T> option1Items, List<List<T>> option2Items) {
		option_picker_view.setData(option1Items, option2Items, null);
		return this;
	}

	public CustomPickerView setData(List<T> option1Items) {
		option_picker_view.setData(option1Items, null, null);
		return this;
	}

	public CustomPickerView setNPData(List<T> option1Items, List<T> option2Items, List<T> option3Items) {
		option_picker_view.setNPData(option1Items, option2Items, option3Items);
		return this;
	}

	public CustomPickerView setNPData(List<T> option1Items, List<T> option2Items) {
		option_picker_view.setNPData(option1Items, option2Items, null);
		return this;
	}

	public CustomPickerView setNPData(List<T> option1Items) {
		option_picker_view.setNPData(option1Items, null, null);
		return this;
	}

	public CustomPickerView setCycle(boolean isCycle) {
		option_picker_view.setCycle(isCycle);
		return this;
	}

	public CustomPickerView setTitle(@NonNull String title) {
		pickerTitle.setText(title);
		return this;
	}

	public CustomPickerView setCancelTextColor(int color) {
		cancel.setTextColor(color);
		return this;
	}

	public CustomPickerView setSureTextColor(int color) {
		sure.setTextColor(color);
		return this;
	}

	public CustomPickerView setTitleTextColor(int color) {
		pickerTitle.setTextColor(color);
		return this;
	}

	public CustomPickerView setDividerColor(int color) {
		option_picker_view.setDividerColor(color);
		return this;
	}

	public CustomPickerView setTitleContentColor(int color) {
		picker_title_content.setBackgroundColor(color);
		return this;
	}

	public CustomPickerView setTextSize(float textSize) {
		option_picker_view.setTextSize(textSize);
		return this;
	}

	public CustomPickerView setOutTextColor(int color) {
		option_picker_view.setOuterTextColor(color);
		return this;
	}

	public CustomPickerView setCenterTextColor(int color) {
		option_picker_view.setCenterTextColor(color);
		return this;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.picker_cancel) {
			dismiss();
		}
		if (v.getId() == R.id.picker_sure) {
			if (mOnChooseItemListener != null) {
				mOnChooseItemListener.itemSelected(option_picker_view.getIndex1(),
						option_picker_view.getIndex2(), option_picker_view.getIndex3());
				dismiss();
			}
		}
	}

	public void setOnChooseItemListener(OnChooseItemListener onChooseItemListener) {
		mOnChooseItemListener = onChooseItemListener;
	}

	public interface OnChooseItemListener {
		void itemSelected(int position1, int position2, int position3);
	}
}
