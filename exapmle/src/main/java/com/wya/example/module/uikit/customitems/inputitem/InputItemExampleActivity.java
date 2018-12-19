package com.wya.example.module.uikit.customitems.inputitem;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

/**
 * 创建日期：2018/11/30 18:03
 * 作者： Mao Chunjiang
 * 文件名称：InputItemExampleActivity
 * 类说明：自定义的常见布局
 */

public class InputItemExampleActivity extends BaseActivity {
    @Override
    protected void initView() {
        setToolBarTitle("文本输入(customitems)");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_input_item_example;

    }
}
