package com.weiyian.android.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.weiyian.android.xpopup.R;
import com.weiyian.android.xpopup.XPopup;
import com.weiyian.android.xpopup.interfaces.OnCancelListener;
import com.weiyian.android.xpopup.interfaces.OnInputConfirmListener;
import com.weiyian.android.xpopup.util.XPopupUtils;

/**
 * Description: 带输入框，确定和取消的对话框
 * Create by dance, at 2018/12/16
 *
 * @author :
 */
public class InputConfirmPopupView extends ConfirmPopupView implements View.OnClickListener {
    
    public InputConfirmPopupView(@NonNull Context context) {
        super(context);
    }
    
    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_center_impl_confirm;
    }
    
    AppCompatEditText tvInput;
    
    @Override
    protected void initPopupContent() {
        tvInput = findViewById(R.id.tv_input);
        tvInput.setVisibility(VISIBLE);
        super.initPopupContent();
    }
    
    @Override
    protected void applyPrimaryColor() {
        super.applyPrimaryColor();
        XPopupUtils.setCursorDrawableColor(tvInput, XPopup.getPrimaryColor());
        tvInput.post(new Runnable() {
            @Override
            public void run() {
                BitmapDrawable defaultDrawable = XPopupUtils.createBitmapDrawable(getResources(), tvInput.getMeasuredWidth(), Color.parseColor("#888888"));
                BitmapDrawable focusDrawable = XPopupUtils.createBitmapDrawable(getResources(), tvInput.getMeasuredWidth(), XPopup.getPrimaryColor());
                tvInput.setBackgroundDrawable(XPopupUtils.createSelector(defaultDrawable, focusDrawable));
            }
        });
        
    }
    
    OnCancelListener cancelListener;
    OnInputConfirmListener inputConfirmListener;
    
    public void setListener(OnInputConfirmListener inputConfirmListener, OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        this.inputConfirmListener = inputConfirmListener;
    }
    
    @Override
    public void onClick(View v) {
        if (v == tvCancel) {
            if (cancelListener != null) {
                cancelListener.onCancel();
            }
            dismiss();
        } else if (v == tvConfirm) {
            if (inputConfirmListener != null) {
                inputConfirmListener.onConfirm(tvInput.getText().toString().trim());
            }
            if (popupInfo.autoDismiss) {
                dismiss();
            }
        }
    }
    
}
