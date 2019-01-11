package com.wya.example.module.uikit.stepper;

import android.content.Intent;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.stepper.WYAStepper;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;

/**
 * @date: 2018/12/3 18:00
 * @author: Chunjiang Mao
 * @classname: StepperExampleActivity
 * @describe: Stepper案例
 */

public class StepperExampleActivity extends BaseActivity {
    
    @BindView(R.id.stepper)
    WYAStepper stepper;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_stepper_example;
    }
    
    @Override
    protected void initView() {
        setTitle("步进器(stepper)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(StepperExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setSecondRightIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(StepperExampleActivity.this, url);
        });
    }
}
