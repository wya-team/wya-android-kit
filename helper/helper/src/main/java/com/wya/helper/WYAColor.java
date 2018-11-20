package com.wya.helper;

import android.graphics.Color;

/**
 * 创建日期：2018/11/19 13:33
 * 作者： Mao Chunjiang
 * 文件名称： WYAColor
 * 类说明：自定义的基础颜色
 */

public class WYAColor {
    //主题色
    private static int theme_color = Color.parseColor("#1890FF");

    public static int getTheme_color() {
        return theme_color;
    }

    public static void setTheme_color(String theme_color_value) {
        WYAColor.theme_color = Color.parseColor(theme_color_value);
    }
}
