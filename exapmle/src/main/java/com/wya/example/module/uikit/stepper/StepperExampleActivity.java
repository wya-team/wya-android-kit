package com.wya.example.module.uikit.stepper;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.stepper.WYAStepper;

import butterknife.BindView;

/**
 * 创建日期：2018/12/3 18:00
 * 作者： Mao Chunjiang
 * 文件名称：StepperExampleActivity
 * 类说明：Stepper案例
 */

public class StepperExampleActivity extends BaseActivity {

    @BindView(R.id.stepper)
    WYAStepper stepper;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_stepper_example;
    }

    @Override
    protected void initView() {
        setToolBarTitle("步进器(stepper)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(StepperExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
    }
}
