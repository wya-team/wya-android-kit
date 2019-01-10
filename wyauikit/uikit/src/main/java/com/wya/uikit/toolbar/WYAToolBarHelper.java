package com.wya.uikit.toolbar;

import android.graphics.Color;

/**
 * @date: 2018/11/19 13:33
 * @author: Chunjiang Mao
 * @classname: WYAColor
 * @describe: 自定义的标题栏颜色
 */

public class WYAToolBarHelper {
    
    /**
     * 显示标题栏
     */
    private boolean show = true;
    /**
     * 标题栏颜色
     */
    private int backgroundColor = Color.parseColor("#ffffff");
    /**
     * 是否是明亮的标题栏
     */
    private boolean isLight;
    /**
     * 标题文字
     */
    private String title = "";
    /**
     * 标题大小
     */
    private int titleSize = 16;
    /**
     * 标题颜色
     */
    private int titleColor = Color.parseColor("#000000");
    
    /**
     * 左边文字
     */
    private String leftText = "";
    /**
     * 左边文字大小
     */
    private int leftTextSize = 14;
    /**
     * 左边文字颜色
     */
    private int leftTextColor = Color.parseColor("#000000");
    /**
     * 默认不显示左边文字
     */
    private boolean showLeftText = false;
    
    /**
     * 是否显示左图标
     */
    private boolean showLeftIcon = true;
    /**
     * 做图标资源
     */
    private int leftIcon;
    
    /**
     * 默认不显示右边第一个文字
     */
    private boolean showFirstRightText = false;
    /**
     * 右边第一个文字內容
     */
    private String firstRightText = "";
    /**
     * 右边第一个文字顏色
     */
    private int firstRightTextColor = Color.parseColor("#000000");
    /**
     * 右边第一个文字大小
     */
    private int firstRightTextSize = 14;
    
    /**
     * 默认不显示右边第二个文字
     */
    private boolean showSecondRightText = false;
    /**
     * 右边第二个文字內容
     */
    private String secondRightText = "";
    /**
     * 右边第二个文字顏色
     */
    private int secondRightTextColor = Color.parseColor("#000000");
    /**
     * 右边第二个文字大小
     */
    private int secondRightTextSize = 14;
    
    /**
     * 是否显示右边第一个图标
     */
    private boolean showFirstRightIcon = false;
    /**
     * 右边第一个图标资源
     */
    private int firstRightIcon;
    
    /**
     * 是否显示第二个图标
     */
    private boolean showSecondRightIcon = false;
    /**
     * 第二个图标资源
     */
    private int secondRightIcon;
    
    public boolean isShowSecondRightIcon() {
        return showSecondRightIcon;
    }
    
    public void setShowSecondRightIcon(boolean showSecondRightIcon) {
        this.showSecondRightIcon = showSecondRightIcon;
    }
    
    public int getSecondRightIcon() {
        return secondRightIcon;
    }
    
    public void setSecondRightIcon(int secondRightIcon) {
        this.secondRightIcon = secondRightIcon;
    }
    
    public int getFirstRightIcon() {
        return firstRightIcon;
    }
    
    public void setFirstRightIcon(int firstRightIcon) {
        this.firstRightIcon = firstRightIcon;
    }
    
    public boolean isShowFirstRightIcon() {
        return showFirstRightIcon;
    }
    
    public void setShowFirstRightIcon(boolean showFirstRightIcon) {
        this.showFirstRightIcon = showFirstRightIcon;
    }
    
    public boolean isShowSecondRightText() {
        return showSecondRightText;
    }
    
    public void setShowSecondRightText(boolean showSecondRightText) {
        this.showSecondRightText = showSecondRightText;
    }
    
    public String getSecondRightText() {
        return secondRightText;
    }
    
    public void setSecondRightText(String secondRightText) {
        this.secondRightText = secondRightText;
    }
    
    public int getSecondRightTextColor() {
        return secondRightTextColor;
    }
    
    public void setSecondRightTextColor(int secondRightTextColor) {
        this.secondRightTextColor = secondRightTextColor;
    }
    
    public int getSecondRightTextSize() {
        return secondRightTextSize;
    }
    
    public void setSecondRightTextSize(int secondRightTextSize) {
        this.secondRightTextSize = secondRightTextSize;
    }
    
    public int getFirstRightTextSize() {
        return firstRightTextSize;
    }
    
    public void setFirstRightTextSize(int firstRightTextSize) {
        this.firstRightTextSize = firstRightTextSize;
    }
    
    public int getFirstRightTextColor() {
        return firstRightTextColor;
    }
    
    public void setFirstRightTextColor(int firstRightTextColor) {
        this.firstRightTextColor = firstRightTextColor;
    }
    
    public String getFirstRightText() {
        return firstRightText;
    }
    
    public void setFirstRightText(String firstRightText) {
        this.firstRightText = firstRightText;
    }
    
    public boolean isShowFirstRightText() {
        return showFirstRightText;
    }
    
    public void setShowFirstRightText(boolean showFirstRightText) {
        this.showFirstRightText = showFirstRightText;
    }
    
    public boolean isShowLeftIcon() {
        return showLeftIcon;
    }
    
    public void setShowLeftIcon(boolean showLeftIcon) {
        this.showLeftIcon = showLeftIcon;
    }
    
    public int getLeftIcon() {
        return leftIcon;
    }
    
    public void setLeftIcon(int leftIcon) {
        this.leftIcon = leftIcon;
    }
    
    public boolean isShowLeftText() {
        return showLeftText;
    }
    
    public void setShowLeftText(boolean showLeftText) {
        this.showLeftText = showLeftText;
    }
    
    public int getLeftTextColor() {
        return leftTextColor;
    }
    
    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
    }
    
    public int getLeftTextSize() {
        return leftTextSize;
    }
    
    public void setLeftTextSize(int leftTextSize) {
        this.leftTextSize = leftTextSize;
    }
    
    public String getLeftText() {
        return leftText;
    }
    
    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }
    
    public int getTitleColor() {
        return titleColor;
    }
    
    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }
    
    public int getTitleSize() {
        return titleSize;
    }
    
    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }
    
    public boolean isShow() {
        return show;
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
    
    public int getBackgroundColor() {
        return backgroundColor;
    }
    
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean isLight() {
        return isLight;
    }
    
    public void setLight(boolean light) {
        isLight = light;
    }
    
}
