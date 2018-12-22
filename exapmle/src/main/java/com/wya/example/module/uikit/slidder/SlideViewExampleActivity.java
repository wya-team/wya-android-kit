package com.wya.example.module.uikit.slidder;

import android.app.Activity;
import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;

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
        setToolBarTitle("滑动输入条(slideview)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(SlideViewExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
        getSwipeBackLayout().setEnableGesture(false);
    }
    
}