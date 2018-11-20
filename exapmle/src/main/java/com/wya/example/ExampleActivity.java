package com.wya.example;

import android.content.Intent;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.UiKitExampleActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class ExampleActivity extends BaseActivity {


    @BindView(R.id.tv_ui_kit)
    TextView tvUiKit;

    @Override
    protected void initView() {
        initClick();
    }

    private void initClick() {
        RxView.clicks(tvUiKit)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(Observable -> {
                    Intent intent = new Intent(ExampleActivity.this, UiKitExampleActivity.class);
                    ExampleActivity.this.startActivity(intent);
                });

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

}
