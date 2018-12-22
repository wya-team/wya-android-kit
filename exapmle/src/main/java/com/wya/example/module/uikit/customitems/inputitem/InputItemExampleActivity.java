package com.wya.example.module.uikit.customitems.inputitem;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.toolbar.AndroidBugWorkaround;

/**
 * 创建日期：2018/11/30 18:03
 * 作者： Mao Chunjiang
 * 文件名称：InputItemExampleActivity
 * 类说明：自定义的常见布局
 */

public class InputItemExampleActivity extends BaseActivity {
    @Override
    protected void initView() {
        AndroidBugWorkaround.assistActivity(findViewById(android.R.id.content));
        setToolBarTitle("文本输入(customitems)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(InputItemExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_input_item_example;

    }
}
