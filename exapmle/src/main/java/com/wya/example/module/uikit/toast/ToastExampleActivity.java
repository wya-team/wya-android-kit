package com.wya.example.module.uikit.toast;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.toast.WYAToast;

import butterknife.BindView;
import butterknife.OnClick;
 /**
  * 创建日期：2018/11/22 14:26
  * 作者： Mao Chunjiang
  * 文件名称：ToastExampleActivity
  * 类说明：自定义Toast
  */

public class ToastExampleActivity extends BaseActivity {

    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_long)
    TextView tvLong;
    @BindView(R.id.tv_custom)
    TextView tvCustom;

    @Override
    protected void initView() {
        setToolBarTitle("Toast");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_toast_example;
    }

    @OnClick({R.id.tv_normal, R.id.tv_long, R.id.tv_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_normal:
                WYAToast.showShort(ToastExampleActivity.this, "普通Toast");
                break;
            case R.id.tv_long:
                WYAToast.showLong(ToastExampleActivity.this, "长时间Toast");
                break;
            case R.id.tv_custom:
                WYAToast.showToastWithImg(ToastExampleActivity.this, "自定义Toast", R.mipmap.ic_launcher, Gravity.CENTER);
                break;
        }
    }
}
