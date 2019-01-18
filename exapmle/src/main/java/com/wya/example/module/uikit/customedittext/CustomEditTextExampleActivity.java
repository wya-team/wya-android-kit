package com.wya.example.module.uikit.customedittext;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @date: 2019/1/8 9:53
 * @author: Chunjiang Mao
 * @classname: CustomEditTextExampleActivity
 * @describe: CustomEditTextExampleActivity
 */

public class CustomEditTextExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wyacustom_edit_text_example;
    }

    @Override
    protected void initView() {
        setTitle("多行输入(customeditext)");
        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(CustomEditTextExampleActivity.this, ReadmeActivity.class).putExtra(EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            showShort("链接地址复制成功");
            StringUtil.copyString(CustomEditTextExampleActivity.this, url);
        });
    }
}
