package com.wya.uikit.customeditext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wya.uikit.R;

/**
 * @date: 2018/12/14 10:26
 * @author: Chunjiang Mao
 * @classname:  WYACustomEdittxet
 * @describe: 自定义文输入框
 */

public class WYACustomEditText extends RelativeLayout {

    //背景图片
    private GradientDrawable gradientDrawable = null;
    //统计文字颜色
    private ColorStateList countTextColor = null;
    //编辑框提示文字颜色
    private ColorStateList hintTextColor = null;
    //编辑框提示文字颜色
    private ColorStateList hintEditColor = null;
    //编辑框文字颜色
    private ColorStateList editTextColor = null;
    //提示文字内容
    private String hintTextStr = null;
    //编辑框文字内容
    private String editTextStr = null;
    //编辑框文本提示内容
    private String hintEditStr = null;

    private int maxNum;


    public WYACustomEditText(Context context) {
        this(context, null);
    }

    public WYACustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.wya_custom_edittext_layout, this);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WYACustomEditText);
        if (a != null) {
            gradientDrawable = (GradientDrawable) a.getDrawable(R.styleable.WYACustomEditText_wyaBackGroundImage);
            if (gradientDrawable != null) {
                setMyBackgroundDrawable(gradientDrawable);
            }

            countTextColor = a.getColorStateList(R.styleable.WYACustomEditText_wyaCountTextColor);
            if (countTextColor != null) {
                setCountTextColor(countTextColor);
            }

            hintEditColor = a.getColorStateList(R.styleable.WYACustomEditText_wyaHintEditColor);
            if (hintEditColor != null) {
                setHintEditColor(hintEditColor);
            }

            hintTextColor = a.getColorStateList(R.styleable.WYACustomEditText_wyaHintTextColor);
            if (hintTextColor != null) {
                setHintTextColor(hintTextColor);
            }

            editTextColor = a.getColorStateList(R.styleable.WYACustomEditText_wyaEditTextColor);
            if (editTextColor != null) {
                setEditTextColor(editTextColor);
            }

            hintTextStr = a.getString(R.styleable.WYACustomEditText_wyaHintTextStr);
            if (hintTextStr != null && !"".equals(hintTextStr)) {
                setHintTextStr(hintTextStr);
            }

            editTextStr = a.getString(R.styleable.WYACustomEditText_wyaEditTextStr);
            if (editTextStr != null) {
                setEditTextStr(editTextStr);
            }

            hintEditStr = a.getString(R.styleable.WYACustomEditText_wyaHintEditStr);
            if (hintEditStr != null) {
                setHintEditStr(hintEditStr);
            }

            maxNum = a.getInt(R.styleable.WYACustomEditText_wyaMaxNum, 100);
            setTextNumCount();
            a.recycle();
        }
    }

    /**
     * 设置输入数字统计
     */
    private void setTextNumCount() {
        tvCountWyaCustomEditText.setText(etWyaCustomEditText.getText().toString().length() + "/" + maxNum);
        etWyaCustomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable editable = etWyaCustomEditText.getText();
                int len = editable.length();
                if (len > maxNum) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, maxNum);
                    etWyaCustomEditText.setText(newStr);
                    editable = etWyaCustomEditText.getText();
                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);
                }
                editTextStr = editable.toString();
                tvCountWyaCustomEditText.setText(etWyaCustomEditText.getText().toString().length() + "/" + maxNum);
            }
        });
    }

    /**
     * 设置编辑框背景图片
     *
     * @param gradientDrawable
     */
    private void setMyBackgroundDrawable(GradientDrawable gradientDrawable) {
        this.gradientDrawable = gradientDrawable;
        wyaCustomEditTextParent.setBackgroundDrawable(gradientDrawable);
    }

    /**
     * 设置编辑框文本提示内容
     *
     * @param hintEditStr
     */
    private void setHintEditStr(String hintEditStr) {
        this.hintEditStr = hintEditStr;
        etWyaCustomEditText.setHint(hintEditStr);
    }

    /**
     * 设置编辑框文本
     *
     * @param editTextStr
     */
    private void setEditTextStr(String editTextStr) {
        this.editTextStr = editTextStr;
        etWyaCustomEditText.setText(editTextStr);
    }

    /**
     * 设置提示文本
     *
     * @param hintTextStr
     */
    private void setHintTextStr(String hintTextStr) {
        this.hintTextStr = hintTextStr;
        tvHintWyaCustomEditText.setVisibility(View.VISIBLE);
        tvHintWyaCustomEditText.setText(hintTextStr);
    }

    /**
     * 设置编辑文本字体颜色
     *
     * @param editTextColor
     */
    private void setEditTextColor(ColorStateList editTextColor) {
        this.editTextColor = editTextColor;
        etWyaCustomEditText.setTextColor(editTextColor);
    }

    /**
     * 设置提示字体颜色
     *
     * @param hintTextColor
     */
    private void setHintTextColor(ColorStateList hintTextColor) {
        this.hintTextColor = hintTextColor;
        tvHintWyaCustomEditText.setTextColor(hintTextColor);
    }

    /**
     * 设置编辑文本提示字体颜色
     *
     * @param hintEditColor
     */
    private void setHintEditColor(ColorStateList hintEditColor) {
        this.hintEditColor = hintEditColor;
        etWyaCustomEditText.setHintTextColor(hintEditColor);
    }

    /**
     * 设置统计字体颜色
     *
     * @param countTextColor
     */
    private void setCountTextColor(ColorStateList countTextColor) {
        this.countTextColor = countTextColor;
        tvCountWyaCustomEditText.setTextColor(countTextColor);
    }

    /**
     * 获取输入文本
     *
     * @return
     */
    private String getEditTextStr() {
        return editTextStr;
    }


    private LinearLayout wyaCustomEditTextParent;
    private TextView tvHintWyaCustomEditText;
    private EditText etWyaCustomEditText;
    private TextView tvCountWyaCustomEditText;

    private void initView() {
        wyaCustomEditTextParent = findViewById(R.id.wya_custom_edit_text_parent);
        tvHintWyaCustomEditText = findViewById(R.id.tv_hint_wya_custom_edit_text);
        etWyaCustomEditText = findViewById(R.id.et_wya_custom_edit_text);
        tvCountWyaCustomEditText = findViewById(R.id.tv_count_wya_custom_edit_text);
    }
}
