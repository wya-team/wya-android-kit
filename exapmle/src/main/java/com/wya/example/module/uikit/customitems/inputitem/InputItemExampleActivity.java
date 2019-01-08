package com.wya.example.module.uikit.customitems.inputitem;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.toolbar.AndroidBugWorkaround;
import com.wya.utils.utils.StringUtil;

/**
 * @date: 2018/11/30 18:03
 * @author: Chunjiang Mao
 * @classname: InputItemExampleActivity
 * @describe: 自定义的常见布局
 */

public class InputItemExampleActivity extends BaseActivity {
    @Override
    protected void initView() {
        AndroidBugWorkaround.assistActivity(findViewById(android.R.id.content));
        setTitle("文本输入(customitems)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(InputItemExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(InputItemExampleActivity.this, url);
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_item_example;
        
    }
}
