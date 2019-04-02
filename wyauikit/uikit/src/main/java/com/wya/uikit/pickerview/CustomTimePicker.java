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

import java.util.Calendar;
import java.util.Date;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 常用时间选择器
 */
public class CustomTimePicker extends Dialog implements View.OnClickListener {
    private static final String TAG = "CustomTimePicker";
    private Context mContext;
    private LinearLayout timePickerTitleContent;
    private TextView timePickerCancel, timePickerTitle, timePickerSure;
    private TimePickerView timePickerView;
    private OnTimePickerSelectedListener mListener;
    private View mDivider;

    public CustomTimePicker(@NonNull Context context, OnTimePickerSelectedListener
            onTimePickerSelectedListener) {
        this(context, R.style.WYACustomDialog, onTimePickerSelectedListener);
    }

    public CustomTimePicker(@NonNull Context context, int themeResId,
                            OnTimePickerSelectedListener onTimePickerSelectedListener) {
        super(context, themeResId);
        mContext = context;
        this.mListener = onTimePickerSelectedListener;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_time_picker_view, null);
        setContentView(view);
        Window window = getWindow();
        window.setWindowAnimations(R.style.picker_view_slide_anim);
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(layoutParams);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        timePickerView = view.findViewById(R.id.time_picker_view);
        timePickerCancel = findViewById(R.id.time_picker_cancel);
        timePickerTitle = findViewById(R.id.time_picker_title);
        timePickerSure = findViewById(R.id.time_picker_sure);
        timePickerTitleContent = findViewById(R.id.time_picker_title_content);
        mDivider = findViewById(R.id.custom_time_picker_divider);

        timePickerCancel.setOnClickListener(this);
        timePickerSure.setOnClickListener(this);
    }

    public CustomTimePicker setTitle(@NonNull String title) {
        timePickerTitle.setText(title);
        return this;
    }
    public CustomTimePicker setTitleDividerColor(int color) {
        mDivider.setBackgroundColor(color);
        return this;
    }

    public CustomTimePicker setCancelTextColor(int color) {
        timePickerCancel.setTextColor(color);
        return this;
    }

    public CustomTimePicker setSureTextColor(int color) {
        timePickerSure.setTextColor(color);
        return this;
    }

    public CustomTimePicker setTitleTextColor(int color) {
        timePickerTitle.setTextColor(color);
        return this;
    }

    public CustomTimePicker setTitleContentColor(int color) {
        timePickerTitleContent.setBackgroundColor(color);
        return this;
    }

    public CustomTimePicker setDividerColor(int color) {
        timePickerView.setDividerColor(color);
        return this;
    }

    public CustomTimePicker setDividerWidth(float width) {
        timePickerView.setDividerStroke(width);
        return this;
    }

    public CustomTimePicker setTextSize(float textSize) {
        timePickerView.setTextSize(textSize);
        return this;
    }

    public CustomTimePicker setOutTextColor(int color) {
        timePickerView.setOuterTextColor(color);
        return this;
    }

    public CustomTimePicker setCenterTextColor(int color) {
        timePickerView.setCenterTextColor(color);
        return this;
    }

    public CustomTimePicker setHourSpace(int space) {
        timePickerView.setHourSpace(space);
        return this;
    }

    public CustomTimePicker setMinuteSpace(int space) {
        timePickerView.setMinuteSpace(space);
        return this;
    }

    public CustomTimePicker setSecondSpace(int space) {
        timePickerView.setSecondSpace(space);
        return this;
    }

    public CustomTimePicker setType(boolean[] type) {
        timePickerView.setType(type);
        return this;
    }

    public CustomTimePicker setShowType(boolean type) {
        timePickerView.setShowType(type);
        return this;
    }

    public CustomTimePicker setItemsVisible(int items){
        timePickerView.setItemsVisible(items);
        return this;
    }

    /**
     * 设置item 行间距 默认值 2F
     * @param lineSpace
     * @return
     */
    public CustomTimePicker setLineSpace(float lineSpace) {
        timePickerView.setLineSpace(lineSpace);
        return this;
    }

    public void setRangeTime(Calendar start, Calendar end) {
        timePickerView.setRangeTime(start, end);
    }

    public void setSelectDate(Calendar selectDate) {
        timePickerView.setSelectDate(selectDate);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.time_picker_sure) {
            Calendar calendar = Calendar.getInstance();

            calendar.set(timePickerView.getYear(), timePickerView.getMonth() - 1, timePickerView.getDay() == 0 ? 1 :
                    timePickerView.getDay(), timePickerView.getHour(), timePickerView.getMinute(),
                    timePickerView.getSecond());

            mListener.selected(calendar.getTime());
            dismiss();

        }
        if (v.getId() == R.id.time_picker_cancel) {
            dismiss();
        }
    }

    public interface OnTimePickerSelectedListener {
        /**
         * selected
         *
         * @param date
         */
        void selected(Date date);
    }
}
