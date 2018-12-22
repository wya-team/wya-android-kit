package com.wya.uikit.pickerview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/08
 * desc   :
 * version: 1.0
 */
public class CustomTimePicker extends Dialog implements View.OnClickListener {
    private Context mContext;
    private LinearLayout time_picker_title_content;
    private TextView time_picker_cancel, time_picker_title, time_picker_sure;
    private TimePickerView time_picker_view;
    private OnTimePickerSelectedListener mListener;
    private String TAG = "CustomTimePicker";

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

        time_picker_view = view.findViewById(R.id.time_picker_view);
        time_picker_cancel = findViewById(R.id.time_picker_cancel);
        time_picker_title = findViewById(R.id.time_picker_title);
        time_picker_sure = findViewById(R.id.time_picker_sure);
        time_picker_title_content = findViewById(R.id.time_picker_title_content);

        time_picker_cancel.setOnClickListener(this);
        time_picker_sure.setOnClickListener(this);
    }

    public CustomTimePicker setTitle(@NonNull String title) {
        time_picker_title.setText(title);
        return this;
    }

    public CustomTimePicker setCancelTextColor(int color) {
        time_picker_cancel.setTextColor(color);
        return this;
    }

    public CustomTimePicker setSureTextColor(int color) {
        time_picker_sure.setTextColor(color);
        return this;
    }

    public CustomTimePicker setTitleTextColor(int color) {
        time_picker_title.setTextColor(color);
        return this;
    }

    public CustomTimePicker setTitleContentColor(int color) {
        time_picker_title_content.setBackgroundColor(color);
        return this;
    }

    public CustomTimePicker setDividerColor(int color) {
        time_picker_view.setDividerColor(color);
        return this;
    }


    public CustomTimePicker setTextSize(float textSize) {
        time_picker_view.setTextSize(textSize);
        return this;
    }

    public CustomTimePicker setOutTextColor(int color) {
        time_picker_view.setOuterTextColor(color);
        return this;
    }

    public CustomTimePicker setCenterTextColor(int color) {
        time_picker_view.setCenterTextColor(color);
        return this;
    }


    public CustomTimePicker setHourSpace(int space) {
        time_picker_view.setHourSpace(space);
        return this;
    }

    public CustomTimePicker setMinuteSpace(int space) {
        time_picker_view.setMinuteSpace(space);
        return this;
    }

    public CustomTimePicker setSecondSpace(int space) {
        time_picker_view.setSecondSpace(space);
        return this;
    }

    public CustomTimePicker setType(boolean[] type) {
        time_picker_view.setType(type);
        return this;
    }

    public CustomTimePicker setShowType(boolean type) {
        time_picker_view.setShowType(type);
        return this;
    }

    public void setRangeTime(Calendar start, Calendar end) {
        time_picker_view.setRangeTime(start, end);
    }

    public void setSelectDate(Calendar selectDate) {
        time_picker_view.setSelectDate(selectDate);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.time_picker_sure) {
            Calendar calendar = Calendar.getInstance();
            Log.i(TAG, "onClick: " + time_picker_view.getMinute() + " " + time_picker_view.getSecond());
            calendar.set(time_picker_view.getYear(), time_picker_view.getMonth() - 1, time_picker_view
                            .getDay(), time_picker_view.getHour(), time_picker_view.getMinute(),
                    time_picker_view.getSecond());

            mListener.selected(calendar.getTime());
            dismiss();
        }
        if (v.getId() == R.id.time_picker_cancel) {
            dismiss();
        }
    }

    public interface OnTimePickerSelectedListener {
        void selected(Date date);
    }
}
