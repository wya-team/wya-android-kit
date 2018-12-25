package com.wya.uikit.toolbar.swipeback;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public class SwipeBackListenerAdapter implements SwipeBackLayout.SwipeListenerEx {
    private final WeakReference<Activity> mActivity;
    private final SwipeBackLayout mLayout;
    
    public SwipeBackListenerAdapter(@NonNull Activity activity, SwipeBackLayout layout) {
        mActivity = new WeakReference<>(activity);
        mLayout = layout;
    }
    
    @Override
    public void onContentViewSwipedBack() {
        Activity activity = mActivity.get();
        if (null != activity && !activity.isFinishing()) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }
    
    @Override
    public void onScrollStateChange(int state, float scrollPercent) {
        switch (state) {
            case ViewDragHelper.STATE_IDLE:
                // 侧滑未退出页面，切换至透明
                if (null != mActivity.get()) {
                    Utils.convertActivityFromTranslucent(mActivity.get());
                }
                break;
        }
    }
    
    @Override
    public void onEdgeTouch(int edgeFlag) {
        // 侧滑切换至透明
        if (null != mActivity.get()) {
            Utils.convertActivityToTranslucent(mActivity.get(), new Utils.TranslucentListener() {
                @Override
                public void onTranslucent() {
                    if (null != mLayout) {
                        mLayout.setPageTranslucent(true);
                    }
                }
            });
        }
    }
    
    @Override
    public void onScrollOverThreshold() {
    
    }
    
}
