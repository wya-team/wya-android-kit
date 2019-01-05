package com.wya.example.module.uikit.customedittext;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

public class CustomEditTextExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wyacustom_edit_text_example;
    }


    @Override
    protected void initView() {
        setTitle("多行输入(customeditext)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(CustomEditTextExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(CustomEditTextExampleActivity.this, url);
        });
    }
}
