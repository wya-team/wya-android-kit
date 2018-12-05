package com.wya.example;

import android.content.Intent;
import android.view.View;

import com.wya.example.base.BaseActivity;
import com.wya.example.module.hardware.HardwareExampleActivity;
import com.wya.example.module.uikit.UiKitExampleActivity;

import butterknife.OnClick;

public class ExampleActivity extends BaseActivity {

    @Override
    protected void initView() {
        setToolBarTitle("微一案");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.wya_button_hardware, R.id.wya_button_ui_kit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wya_button_hardware:
                ExampleActivity.this.startActivity(new Intent(ExampleActivity.this, HardwareExampleActivity.class));
                break;
            case R.id.wya_button_ui_kit:
                ExampleActivity.this.startActivity(new Intent(ExampleActivity.this, UiKitExampleActivity.class));
                break;
        }
    }
}
