package com.wya.example.module.uikit.keyboard;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.keyboard.WYACustomNumberKeyboard;
import com.wya.uikit.keyboard.WYACustomNumberKeyboardView;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @date: 2018/11/22 17:35
 * @author: Chunjiang Mao
 * @classname: KeyboardExampleActivity
 * @describe: 软件盘example
 */

public class KeyboardExampleActivity extends BaseActivity {

    @BindView(R.id.keyboard_view)
    WYACustomNumberKeyboardView keyboardView;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_number_random)
    EditText etNumberRandom;
    @BindView(R.id.parent)
    RelativeLayout parent;

    private WYACustomNumberKeyboard keyboard = null;

    @Override
    protected void initView() {
        setTitle("软键盘(keyboard)");
        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(KeyboardExampleActivity.this, ReadmeActivity.class).putExtra(EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(KeyboardExampleActivity.this, url);
        });
        setKeyBoard();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_keyboard_example;
    }

    private void setKeyBoard() {
        //获取到keyboard对象
        keyboard = new WYACustomNumberKeyboard(KeyboardExampleActivity.this);
        etNumberRandom.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //eiditext绑定keyboard，true表示随机数字
                keyboard.attachTo(etNumberRandom, true);
            }
            etNumberRandom.setSelection(etNumberRandom.getText().toString().length());
            //获取焦点 光标出现
            etNumberRandom.requestFocus();
            return true;
        });

        etNumber.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //eiditext绑定keyboard，false表示普通数字键盘
                keyboard.attachTo(etNumber, false);
                etNumber.setSelection(etNumber.getText().toString().length());
                etNumber.requestFocus();//获取焦点 光标出现
            }
            return true;
        });

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (count(s.toString(), ".") > 1) {
                    etNumber.setText(s.toString().substring(0, s.toString().length() - 1));
                    etNumber.setSelection(s.toString().length() - 1);
                }
            }
        });
        etNumberRandom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (count(s.toString(), ".") > 1) {
                    etNumberRandom.setText(s.toString().substring(0, s.toString().length() - 1));
                    etNumberRandom.setSelection(s.toString().length() - 1);
                }
            }
        });

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.hideKeyBoard();
            }
        });
        /*
        确定按钮
         */
        keyboard.setOnOkClick(() -> Toast.makeText(KeyboardExampleActivity.this, "确定按钮", Toast.LENGTH_SHORT).show());
        //隐藏键盘按钮
        keyboard.setOnCancelClick(() -> Toast.makeText(KeyboardExampleActivity.this, "隐藏键盘按钮", Toast.LENGTH_SHORT).show());
    }

    public int count(String text, String sub) {
        int count = 0, start = 0;
        while ((start = text.indexOf(sub, start)) >= 0) {
            start += sub.length();
            count++;
        }
        return count;
    }

}