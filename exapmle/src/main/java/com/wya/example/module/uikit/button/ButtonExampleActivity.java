package com.wya.example.module.uikit.button;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.button.WYAButton;
import com.wya.utils.utils.ColorUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ButtonExampleActivity extends BaseActivity {

    @BindView(R.id.wya_button)
    WYAButton wyaButton;
    @BindView(R.id.radio_button_main)
    RadioButton radioButtonMain;
    @BindView(R.id.radio_button_second)
    RadioButton radioButtonSecond;
    @BindView(R.id.radio_button_small)
    RadioButton radioButtonSmall;
    @BindView(R.id.radio_disabled_true)
    RadioButton radioDisabledTrue;
    @BindView(R.id.radio_disabled_false)
    RadioButton radioDisabledFalse;
    @BindView(R.id.radio_frame_true)
    RadioButton radioFrameTrue;
    @BindView(R.id.radio_frame_false)
    RadioButton radioFrameFalse;
    @BindView(R.id.radio_loading_true)
    RadioButton radioLoadingTrue;
    @BindView(R.id.radio_loading_false)
    RadioButton radioLoadingFalse;

    private boolean showText = true;
    private boolean loading = false;

    @Override
    protected void initView() {
        if (wyaButton != null) {
            wyaButton.setFillet(true);
            wyaButton.setRadius(10);//设置圆角大小
            wyaButton.setTextColor(ColorUtil.hex2Int("#ffffff"));//字体颜色
            wyaButton.setTextColorPress(ColorUtil.hex2Int("#ffffff"));//点击后的字体颜色
            wyaButton.setBackColor(ColorUtil.hex2Int("#1890FF"));//背景颜色
            wyaButton.setBackColorPress(ColorUtil.hex2Int("#0083d5"));//点击后的背景颜色
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_button_example;
    }

    @OnClick({R.id.radio_loading_true, R.id.radio_loading_false, R.id.radio_frame_true, R.id.radio_frame_false, R.id.wya_button, R.id.radio_button_main, R.id.radio_button_second, R.id.radio_button_small, R.id.radio_disabled_true, R.id.radio_disabled_false})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wya_button:
                break;
            case R.id.radio_button_main:
                showText = true;
                wyaButton.setBackGroundDrawable(null);
                wyaButton.setBackGroundDrawablePress(null);
                wyaButton.setSize(ButtonExampleActivity.this, 1, 0, 0);
                wyaButton.setTextColor(ColorUtil.hex2Int("#ffffff"));//字体颜色
                wyaButton.setTextColorPress(ColorUtil.hex2Int("#ffffff"));//点击后的字体颜色
                wyaButton.setBackColor(ColorUtil.hex2Int("#1890FF"));//背景颜色
                wyaButton.setBackColorPress(ColorUtil.hex2Int("#0083d5"));//点击后的背景颜色
                wyaButton.setText("主按钮");
                wyaButton.setTextSize(15);
                break;
            case R.id.radio_button_second:
                showText = true;
                wyaButton.setBackGroundDrawable(null);
                wyaButton.setBackGroundDrawablePress(null);
                wyaButton.setSize(ButtonExampleActivity.this, 1, 0, 0);
                wyaButton.setTextColor(ColorUtil.hex2Int("#000000"));//字体颜色
                wyaButton.setTextColorPress(ColorUtil.hex2Int("#000000"));//点击后的字体颜色
                wyaButton.setBackColor(ColorUtil.hex2Int("#ffffff"));//背景颜色
                wyaButton.setBackColorPress(ColorUtil.hex2Int("#eeeeee"));//点击后的背景颜色
                wyaButton.setText("次按钮");
                wyaButton.setTextSize(15);
                break;
            case R.id.radio_button_small:
                if(loading){
                    wyaButton.setText("");
                } else {
                    wyaButton.setText("次按钮");
                }
                showText = false;
                wyaButton.setBackGroundDrawable(null);
                wyaButton.setBackGroundDrawablePress(null);
                wyaButton.setSize(ButtonExampleActivity.this, 2, 0, 0);
                wyaButton.setTextSize(10);
                break;
            case R.id.radio_disabled_true:
                if (radioButtonSecond.isChecked()) {
                    wyaButton.setTextColor(ColorUtil.hex2Int("#000000"));//字体颜色
                    wyaButton.setTextColorPress(ColorUtil.hex2Int("#000000"));//点击后的字体颜色
                    wyaButton.setBackColor(ColorUtil.hex2Int("#ffffff"));//背景颜色
                    wyaButton.setBackColorPress(ColorUtil.hex2Int("#eeeeee"));//点击后的背景颜色
                    wyaButton.setText("次按钮");
                } else if (radioButtonMain.isChecked()) {
                    wyaButton.setTextColor(ColorUtil.hex2Int("#ffffff"));//字体颜色
                    wyaButton.setTextColorPress(ColorUtil.hex2Int("#ffffff"));//点击后的字体颜色
                    wyaButton.setBackColor(ColorUtil.hex2Int("#1890FF"));//背景颜色
                    wyaButton.setBackColorPress(ColorUtil.hex2Int("#0083d5"));//点击后的背景颜色
                    wyaButton.setText("主按钮");
                }
                wyaButton.setEnabled(true);
                break;
            case R.id.radio_disabled_false:
                wyaButton.setEnabled(false);
                wyaButton.setTextColor(ColorUtil.hex2Int("#b2b2b2"));//字体颜色
                wyaButton.setBackColor(ColorUtil.hex2Int("#dddddd"));//背景颜色
                break;
            case R.id.radio_frame_true:
                wyaButton.setBackGroundDrawable(ButtonExampleActivity.this.getResources().getDrawable(R.drawable.wya_button_frame_normal_bg));
                wyaButton.setBackGroundDrawablePress(ButtonExampleActivity.this.getResources().getDrawable(R.drawable.wya_button_frame_press_bg));
                wyaButton.setTextColor(ColorUtil.hex2Int("#1890FF"));//字体颜色
                wyaButton.setTextColorPress(Color.parseColor("#0083d5"));//点击后的字体颜色
                break;
            case R.id.radio_frame_false:
                wyaButton.setBackGroundDrawable(null);
                wyaButton.setBackGroundDrawablePress(null);
                if (radioButtonSecond.isChecked()) {
                    wyaButton.setTextColor(ColorUtil.hex2Int("#000000"));//字体颜色
                    wyaButton.setTextColorPress(ColorUtil.hex2Int("#000000"));//点击后的字体颜色
                    wyaButton.setBackColor(ColorUtil.hex2Int("#ffffff"));//背景颜色
                    wyaButton.setBackColorPress(ColorUtil.hex2Int("#eeeeee"));//点击后的背景颜色
                    wyaButton.setText("次按钮");
                } else if (radioButtonMain.isChecked()) {
                    wyaButton.setTextColor(ColorUtil.hex2Int("#ffffff"));//字体颜色
                    wyaButton.setTextColorPress(ColorUtil.hex2Int("#ffffff"));//点击后的字体颜色
                    wyaButton.setBackColor(ColorUtil.hex2Int("#1890FF"));//背景颜色
                    wyaButton.setBackColorPress(ColorUtil.hex2Int("#0083d5"));//点击后的背景颜色
                    wyaButton.setText("主按钮");
                }
                break;
            case R.id.radio_loading_true:
                loading = true;
                wyaButton.setLoading(ButtonExampleActivity.this, ButtonExampleActivity.this.getResources().getDrawable(R.mipmap.loading_01),  showText );
                break;
            case R.id.radio_loading_false:
                loading = false;
                wyaButton.setLoading(ButtonExampleActivity.this, null,  showText);
                break;
        }
    }
}
