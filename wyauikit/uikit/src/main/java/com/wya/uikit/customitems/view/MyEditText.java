package com.wya.uikit.customitems.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;


/**
 * 创建日期：2018/12/27 13:46
 * 作者： Mao Chunjiang
 * 文件名称： MyEditText
 * 类说明：
 */

public class MyEditText extends EditText {


    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isEnabled() && super.onTouchEvent(event);
    }
}
