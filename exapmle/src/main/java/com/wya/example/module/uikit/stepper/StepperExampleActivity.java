package com.wya.example.module.uikit.stepper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
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
        setToolBarTitle("Stepper");
    }
}
