package com.weiyian.android.xpopup.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.weiyian.android.xpopup.R;
import com.weiyian.android.xpopup.XPopup;
import com.weiyian.android.xpopup.core.CenterPopupView;
import com.weiyian.android.xpopup.interfaces.OnCancelListener;
import com.weiyian.android.xpopup.interfaces.OnConfirmListener;

/**
 * Description: 确定和取消的对话框
 * Create by dance, at 2018/12/16
 *
 * @author :
 */
public class ConfirmPopupView extends CenterPopupView implements View.OnClickListener {
    
    public ConfirmPopupView(@NonNull Context context) {
        super(context);
    }
    
    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_center_impl_confirm;
    }
    
    TextView tvTitle, tvContent, tvCancel, tvConfirm;
    
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
        
        applyPrimaryColor();
        
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
    }
    
    protected void applyPrimaryColor() {
        tvCancel.setTextColor(XPopup.getPrimaryColor());
        tvConfirm.setTextColor(XPopup.getPrimaryColor());
    }
    
    OnCancelListener cancelListener;
    OnConfirmListener confirmListener;
    
    public void setListener(OnConfirmListener confirmListener, OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        this.confirmListener = confirmListener;
    }
    
    String title;
    String content;
    
    public void setTitleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    @Override
    public void onClick(View v) {
        if (v == tvCancel) {
            if (cancelListener != null) {
                cancelListener.onCancel();
            }
            dismiss();
        } else if (v == tvConfirm) {
            if (confirmListener != null) {
                confirmListener.onConfirm();
            }
            if (popupInfo.autoDismiss) {
                dismiss();
            }
        }
    }
}
