package com.weiyian.android.circleprogress;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author :
 */
public class BaseLoading implements ILoadingView {
    
    private Activity mActivity;
    private View vLoading;
    
    public BaseLoading inflate(Activity activity) {
        mActivity = activity;
        initView();
        return this;
    }
    
    private void initView() {
        if (mActivity == null) {
            return;
        }
        if (vLoading == null) {
            FrameLayout root = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
            if (root != null) {
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (root.getChildCount() > 0) {
                    View contentView = root.getChildAt(0);
                    if (contentView != null) {
                        layoutParams = contentView.getLayoutParams();
                    }
                }
                vLoading = LayoutInflater.from(mActivity).inflate(R.layout.layout_base_loading, null);
                vLoading.setOnTouchListener((v, event) -> true);
                root.addView(vLoading, layoutParams);
            }
            
        }
    }
    
    @Override
    public void showLoading() {
        if (vLoading == null) {
            return;
        }
        vLoading.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void dismissLoading() {
        if (isLoading()) {
            vLoading.setVisibility(View.GONE);
        }
    }
    
    @Override
    public boolean isLoading() {
        return vLoading != null && (vLoading.getVisibility() == View.VISIBLE);
    }
    
    @Override
    public View getView() {
        return vLoading;
    }
    
}
