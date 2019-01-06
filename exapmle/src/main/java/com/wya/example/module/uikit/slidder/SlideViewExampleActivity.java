package com.wya.example.module.uikit.slidder;

import android.app.Activity;
import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

public class SlideViewExampleActivity extends BaseActivity {

    public static void start(Activity activity) {
        if (null == activity) {
            return;
        }
        Intent intent = new Intent(activity, SlideViewExampleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_activity_slide_view;
    }

    @Override
    protected void initView() {
        getSwipeBackLayout().setEnableGesture(false);
        setTitle("滑动输入条(slideview)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(SlideViewExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(SlideViewExampleActivity.this, url);
        });
    }
}