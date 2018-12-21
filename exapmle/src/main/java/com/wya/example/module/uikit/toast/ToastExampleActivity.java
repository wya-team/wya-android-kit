package com.wya.example.module.uikit.toast;

import android.view.Gravity;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import butterknife.OnClick;

/**
 * 创建日期：2018/11/22 14:26
 * 作者： Mao Chunjiang
 * 文件名称：ToastExampleActivity
 * 类说明：自定义Toast
 */

public class ToastExampleActivity extends BaseActivity {

    @Override
    protected void initView() {
        setToolBarTitle("轻提示(toast)");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_toast_example;
    }

    @OnClick({R.id.tv_more_custom_text, R.id.tv_normal, R.id.tv_more_text, R.id.tv_long, R.id.tv_custom_success, R.id.tv_custom_fail, R.id.tv_custom_warn, R.id.tv_normal_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_normal:
                getWyaToast().showShort("系统提示");
                break;
            case R.id.tv_more_text:
                getWyaToast().showShort("我有很多字要提示,需要换行显示，哈哈哈哈哈哈哈。。。");
                break;
            case R.id.tv_long:
                getWyaToast().showLong("长时间提示");
                break;
            case R.id.tv_custom_success:
                getWyaToast().showToastWithImg("成功提示", R.drawable.icon_succesful, Gravity.CENTER);
                break;
            case R.id.tv_custom_fail:
                getWyaToast().showToastWithImg("失败提示", R.drawable.icon_fail, Gravity.CENTER);
                break;
            case R.id.tv_custom_warn:
                getWyaToast().showToastWithImg("警告提示", R.drawable.icon_waring, Gravity.CENTER);
                break;
            case R.id.tv_normal_custom:
                getWyaToast().showToastWithImg("自定义普通文字提示", 0, Gravity.BOTTOM);
                break;
            case R.id.tv_more_custom_text:
                getWyaToast().showToastWithImg("我有很多字要提示,需要换行显示，哈哈哈哈哈哈哈。。。", 0, Gravity.BOTTOM);
                break;
        }
    }
}
