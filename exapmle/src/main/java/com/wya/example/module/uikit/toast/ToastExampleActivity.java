package com.wya.example.module.uikit.toast;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date: 2018/11/22 14:26
 * @author: Chunjiang Mao
 * @classname: ToastExampleActivity
 * @describe: 自定义Toast
 */

public class ToastExampleActivity extends BaseActivity {
    
    @BindView(R.id.radio_center)
    RadioButton radioCenter;
    @BindView(R.id.radio_bottom)
    RadioButton radioBottom;
    private int gravity = Gravity.CENTER;
    
    @Override
    protected void initView() {
        setTitle("轻提示(toast)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(ToastExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(ToastExampleActivity.this, url);
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_toast_example;
    }
    
    @OnClick({R.id.radio_center, R.id.radio_bottom, R.id.tv_more_custom_text, R.id.tv_normal, R.id.tv_more_text, R.id.tv_long, R.id.tv_custom_success, R.id.tv_custom_fail, R.id.tv_custom_warn, R.id.tv_normal_custom})
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
                getWyaToast().showToastWithImg("成功提示", R.drawable.icon_succesful, gravity);
                break;
            case R.id.tv_custom_fail:
                getWyaToast().showToastWithImg("失败提示", R.drawable.icon_fail, gravity);
                break;
            case R.id.tv_custom_warn:
                getWyaToast().showToastWithImg("警告提示", R.drawable.icon_waring, gravity);
                break;
            case R.id.tv_normal_custom:
                getWyaToast().showToastWithImg("自定义普通文字提示", 0, gravity);
                break;
            case R.id.tv_more_custom_text:
                getWyaToast().showToastWithImg("我有很多字要提示,需要换行显示，哈哈哈哈哈哈哈。。。", 0, gravity);
                break;
            case R.id.radio_center:
                gravity = Gravity.CENTER;
                radioCenter.setChecked(true);
                radioBottom.setChecked(false);
                break;
            case R.id.radio_bottom:
                gravity = Gravity.BOTTOM;
                radioBottom.setChecked(true);
                radioCenter.setChecked(false);
                break;
            default:
                break;
        }
    }
    
}
