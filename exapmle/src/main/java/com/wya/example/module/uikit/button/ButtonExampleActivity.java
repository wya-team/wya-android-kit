package com.wya.example.module.uikit.button;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

public class ButtonExampleActivity extends BaseActivity {

    @Override
    protected void initView() {
        setToolBarTitle("按钮(button)");
        String url= getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(ButtonExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
        setRightImageAntherOnLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(ButtonExampleActivity.this, url);
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_button_example;
    }

}
