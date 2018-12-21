package com.wya.example.module.uikit.slidder;

import android.app.Activity;
import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

public class SlideViewExampleActivity extends BaseActivity {
    
    public static void start(Activity activity) {
        if (null == activity) {
            return;
        }
        Intent intent = new Intent(activity, SlideViewExampleActivity.class);
        activity.startActivity(intent);
    }
    
    @Override
    protected int getLayoutID() {
        return R.layout.layout_activity_slide_view;
    }
    
    @Override
    protected void initView() {
        setToolBarTitle("slidder");
    }
    
}