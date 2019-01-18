package com.wya.example.module.uikit.button;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @date: 2019/1/10 14:06
 * @author: Chunjiang Mao
 * @classname: ButtonExampleActivity
 * @describe: ButtonExampleActivity
 */

public class ButtonExampleActivity extends BaseActivity {

    @Override
    protected void initView() {
        setTitle("按钮(button)");
        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(ButtonExampleActivity.this, ReadmeActivity.class).putExtra(EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            showShort("链接地址复制成功");
            StringUtil.copyString(ButtonExampleActivity.this, url);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_button_example;
    }

}
