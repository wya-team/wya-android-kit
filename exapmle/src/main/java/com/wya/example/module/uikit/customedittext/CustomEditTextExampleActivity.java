package com.wya.example.module.uikit.customedittext;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;

public class CustomEditTextExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_wyacustom_edit_text_example;
    }


    @Override
    protected void initView() {
        setToolBarTitle("多行输入(customeditext)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(CustomEditTextExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
    }
}
