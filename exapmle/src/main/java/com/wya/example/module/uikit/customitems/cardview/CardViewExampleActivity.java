package com.wya.example.module.uikit.customitems.cardview;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;

public class CardViewExampleActivity extends BaseActivity {



    @Override
    protected void initView() {
        setToolBarTitle("卡片(customitems)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(CardViewExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_wyacard_view_example;
    }

}
