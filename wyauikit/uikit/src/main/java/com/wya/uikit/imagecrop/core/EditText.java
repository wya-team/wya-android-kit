package com.wya.uikit.imagecrop.core;

import android.graphics.Color;
import android.text.TextUtils;


public class EditText {

    private String text;

    private int color = Color.WHITE;

    public EditText(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(text);
    }

    public int length() {
        return isEmpty() ? 0 : text.length();
    }

    @Override
    public String toString() {
        return "EditText{" +
                "text='" + text + '\'' +
                ", color=" + color +
                '}';
    }
}
