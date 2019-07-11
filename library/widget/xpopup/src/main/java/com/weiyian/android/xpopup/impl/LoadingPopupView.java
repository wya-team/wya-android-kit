package com.weiyian.android.xpopup.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.weiyian.android.xpopup.R;
import com.weiyian.android.xpopup.core.CenterPopupView;

/**
 * Description: 加载对话框
 * Create by dance, at 2018/12/16
 *
 * @author :
 */
public class LoadingPopupView extends CenterPopupView {
    
    public LoadingPopupView(@NonNull Context context) {
        super(context);
    }
    
    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_center_impl_loading;
    }
    
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        TextView tvTitle = findViewById(R.id.tv_title);
        if (title != null) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(title);
        }
    }
    
    private String title;
    
    public LoadingPopupView setTitle(String title) {
        this.title = title;
        return this;
    }
}
