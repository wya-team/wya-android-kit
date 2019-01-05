package com.wya.example.module.uikit.customitems.list;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

public class ListExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_example;
    }

    @Override
    protected void initView() {
        setTitle("列表(list)");
    }
}
