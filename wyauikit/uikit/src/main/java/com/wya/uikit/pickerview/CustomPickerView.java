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
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 常用三级联动
 */
public class CustomPickerView<T> extends Dialog implements View.OnClickListener {
    private final String TAG = getClass().getName();
    private Context mContext;
    private OptionsPickerView optionPickerView;
    private TextView pickerTitle, cancel, sure;
    private OnChooseItemListener mOnChooseItemListener;
    private LinearLayout pickerTitleContent;

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

        optionPickerView = view.findViewById(R.id.option_picker_view);
        cancel = findViewById(R.id.picker_cancel);
        pickerTitle = findViewById(R.id.picker_title);
        sure = findViewById(R.id.picker_sure);
        pickerTitleContent = findViewById(R.id.picker_title_content);

        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);

    }

    public void setData(List<T> option1Items, List<List<T>> option2Items,
                        List<List<List<T>>> option3Items) {
        optionPickerView.setData(option1Items, option2Items, option3Items);
    }

    public void setData(List<T> option1Items, List<List<T>> option2Items) {
        optionPickerView.setData(option1Items, option2Items, null);
    }

    public void setData(List<T> option1Items) {
        optionPickerView.setData(option1Items, null, null);
    }

    public void setNPData(List<T> option1Items, List<T> option2Items, List<T> option3Items) {
        optionPickerView.setNPData(option1Items, option2Items, option3Items);
    }

    public void setNPData(List<T> option1Items, List<T> option2Items) {
        optionPickerView.setNPData(option1Items, option2Items, null);
    }

    public void setNPData(List<T> option1Items) {
        optionPickerView.setNPData(option1Items, null, null);
    }

    public CustomPickerView setNPIndex(int index1) {
        optionPickerView.setNPIndex(index1);
        return this;
    }

    public CustomPickerView setNPIndex(int index1, int index2) {
        optionPickerView.setNPIndex(index1, index2);
        return this;
    }

    public CustomPickerView setNPIndex(int index1, int index2, int index3) {
        optionPickerView.setNPIndex(index1, index2, index3);
        return this;
    }

    public CustomPickerView setCycle(boolean isCycle) {
        optionPickerView.setCycle(isCycle);
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

    public CustomPickerView setTitleContentColor(int color) {
        pickerTitleContent.setBackgroundColor(color);
        return this;
    }

    public CustomPickerView setDividerColor(int color) {
        optionPickerView.setDividerColor(color);
        return this;
    }

    public CustomPickerView setTextSize(float textSize) {
        optionPickerView.setTextSize(textSize);
        return this;
    }

    public CustomPickerView setOutTextColor(int color) {
        optionPickerView.setOuterTextColor(color);
        return this;
    }

    public CustomPickerView setCenterTextColor(int color) {
        optionPickerView.setCenterTextColor(color);
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.picker_cancel) {
            dismiss();
        }
        if (v.getId() == R.id.picker_sure) {
            if (mOnChooseItemListener != null) {
                mOnChooseItemListener.itemSelected(optionPickerView.getIndex1(),
                        optionPickerView.getIndex2(), optionPickerView.getIndex3());
                dismiss();
            }
        }
    }

    public void setOnChooseItemListener(OnChooseItemListener onChooseItemListener) {
        mOnChooseItemListener = onChooseItemListener;
    }

    public interface OnChooseItemListener {
        /**
         * itemSelected
         *
         * @param position1
         * @param position2
         * @param position3
         */
        void itemSelected(int position1, int position2, int position3);
    }
}
