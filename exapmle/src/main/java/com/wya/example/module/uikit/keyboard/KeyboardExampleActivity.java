package com.wya.example.module.uikit.keyboard;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.keyboard.WYKCustomNumberKeyboard;
import com.wya.uikit.toast.WYAToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/11/22 17:35
 * 作者： Mao Chunjiang
 * 文件名称：KeyboardExampleActivity
 * 类说明：软件盘example
 */

public class KeyboardExampleActivity extends BaseActivity {

    @BindView(R.id.btn_show_keyboard)
    Button btnShowKeyboard;
    @BindView(R.id.btn_hide_keyboard)
    Button btnHideKeyboard;
    @BindView(R.id.btn_custom_keyboard)
    Button btnCustomKeyboard;

    private WYKCustomNumberKeyboard wykCustomNumberKeyboard;

    @Override
    protected void initView() {
        setToolBarTitle("Keyboard");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_keyboard_example;
    }

    private InputMethodManager imm;
    @OnClick({R.id.btn_show_keyboard, R.id.btn_hide_keyboard, R.id.btn_custom_keyboard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_show_keyboard:
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                break;
            case R.id.btn_hide_keyboard:
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                break;
            case R.id.btn_custom_keyboard:
                wykCustomNumberKeyboard = new WYKCustomNumberKeyboard(this, new WYKCustomNumberKeyboard.ChooseInterface() {
                    @Override
                    public void selectPosition(int position) {
                        if (position == 0) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "0");
                        } else if (position == 1) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "1");
                        } else if (position == 2) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "2");
                        } else if (position == 3) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "3");
                        } else if (position == 4) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "4");
                        } else if (position == 5) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "5");
                        } else if (position == 6) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "6");
                        } else if (position == 7) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "7");
                        } else if (position == 8) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "8");
                        } else if (position == 9) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "9");
                        } else if (position == 10) {
                            WYAToast.showShort(KeyboardExampleActivity.this, ".");
                        } else if (position == 11) {
                            wykCustomNumberKeyboard.dismiss();
                            WYAToast.showShort(KeyboardExampleActivity.this, "hide");
                        } else if (position == 12) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "del");
                        } else if (position == 13) {
                            WYAToast.showShort(KeyboardExampleActivity.this, "sure");
                        }
                    }
                });
                wykCustomNumberKeyboard.show();
                break;
        }
    }
}
