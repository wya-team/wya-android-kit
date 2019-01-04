package com.wya.uikit.toolbar;


import android.graphics.Color;

/**
 * @date: 2018/11/19 13:33
 * @author: Chunjiang Mao
 * @classname:  WYAColor
 * @describe: 自定义的标题栏颜色
 */

public class WYAToolBarHelper {
    //显示标题栏
    private boolean showToolBar = true;
    //标题栏颜色
    private int toolbarBgColor = Color.parseColor("#000000");

    //标题参数
    //默认显示标题
    private boolean isShowTitle = true;
    //标题文字
    private String titleStr = "标题";
    //标题大小
    private int titleTextSize = 16;
    //标题颜色
    private int titleTextColor = Color.parseColor("#FFFFFF");

    //标题左边参数
    //默认不显示左边文字
    private boolean isShowTvLeft = false;
    //左边文字
    private String tvLeftStr = "左边";
    //左边文字大小
    private int tvLeftTextSize = 14;
    //标题颜色
    private int tvLeftTextColor = Color.parseColor("#FFFFFF");
    //是否显示左图标
    private boolean isShowImgLeft = true;
    //做图标资源
    private int imgLeftRes;


    //标题右边参数
    //默认不显示右边文字
    private boolean isShowTvRight = false;
    //默认不显示右边文字
    private boolean isShowTvRightAnther = false;
    //右边文字
    private String tvRightStr = "右边";
    //右边文字
    private String tvRightAntherStr = "右边第二个";
    //右边文字大小
    private int tvRightTextSize = 14;
    //右边文字大小
    private int tvRightAntherTextSize = 14;
    //标题颜色
    private int tvRightTextColor = Color.parseColor("#FFFFFF");
    //标题颜色
    private int tvRightAntherTextColor = Color.parseColor("#FFFFFF");
    //是否显示右图标
    private boolean isShowImgRight = false;
    //是否显示第二个图标
    private boolean isShowImgRightAnther = false;
    //做图标资源
    private int imgRightRes;
    //第二个图标资源
    private int imgRightResAnther;

    private boolean isLight;//是否是明亮的标题栏

    public boolean isLight() {
        return isLight;
    }

    public void setLight(boolean light) {
        isLight = light;
    }

    public boolean isShowImgRightAnther() {
        return isShowImgRightAnther;
    }

    public void setShowImgRightAnther(boolean showImgRightAnther) {
        isShowImgRightAnther = showImgRightAnther;
    }

    public int getImgRightResAnther() {
        return imgRightResAnther;
    }

    public void setImgRightResAnther(int imgRightResAnther) {
        this.imgRightResAnther = imgRightResAnther;
    }

    public boolean isShowTvLeft() {
        return isShowTvLeft;
    }

    public void setShowTvLeft(boolean showTvLeft) {
        isShowTvLeft = showTvLeft;
    }

    public boolean isShowTvRight() {
        return isShowTvRight;
    }

    public void setShowTvRight(boolean showTvRight) {
        isShowTvRight = showTvRight;
    }

    public boolean isShowToolBar() {
        return showToolBar;
    }

    public void setShowToolBar(boolean showToolBar) {
        this.showToolBar = showToolBar;
    }

    public String getTvLeftStr() {
        return tvLeftStr;
    }

    public void setTvLeftStr(String tvLeftStr) {
        this.tvLeftStr = tvLeftStr;
    }

    public int getTvLeftTextSize() {
        return tvLeftTextSize;
    }

    public void setTvLeftTextSize(int tvLeftTextSize) {
        this.tvLeftTextSize = tvLeftTextSize;
    }

    public int getTvLeftTextColor() {
        return tvLeftTextColor;
    }

    public void setTvLeftTextColor(String tvLeftTextColorValue) {
        this.tvLeftTextColor = Color.parseColor(tvLeftTextColorValue);
    }

    public boolean isShowImgLeft() {
        return isShowImgLeft;
    }

    public void setShowImgLeft(boolean showImgLeft) {
        isShowImgLeft = showImgLeft;
    }

    public int getImgLeftRes() {
        return imgLeftRes;
    }

    public void setImgLeftRes(int imgLeftRes) {
        this.imgLeftRes = imgLeftRes;
    }


    public String getTvRightStr() {
        return tvRightStr;
    }

    public void setTvRightStr(String tvRightStr) {
        this.tvRightStr = tvRightStr;
    }

    public int getTvRightTextSize() {
        return tvRightTextSize;
    }

    public void setTvRightTextSize(int tvRightTextSize) {
        this.tvRightTextSize = tvRightTextSize;
    }

    public int getTvRightTextColor() {
        return tvRightTextColor;
    }

    public void setTvRightTextColor(String tvRightTextColorValue) {
        this.tvRightTextColor = Color.parseColor(tvRightTextColorValue);
        ;
    }

    public boolean isShowImgRight() {
        return isShowImgRight;
    }

    public void setShowImgRight(boolean showImgRight) {
        isShowImgRight = showImgRight;
    }

    public int getImgRightRes() {
        return imgRightRes;
    }

    public void setImgRightRes(int imgRightRes) {
        this.imgRightRes = imgRightRes;
    }

    public boolean isShowTitle() {
        return isShowTitle;
    }

    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(String titleTextColorValue) {
        this.titleTextColor = Color.parseColor(titleTextColorValue);
    }

    public int getToolbarBgColor() {
        return toolbarBgColor;
    }

    public void setToolbarBgColor(String toolbarBgColorValue) {
        this.toolbarBgColor = Color.parseColor(toolbarBgColorValue);
    }

    public void setToolbarBgColor(int toolbarBgColor) {
        this.toolbarBgColor = toolbarBgColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public void setTvLeftTextColor(int tvLeftTextColor) {
        this.tvLeftTextColor = tvLeftTextColor;
    }

    public boolean isShowTvRightAnther() {
        return isShowTvRightAnther;
    }

    public void setShowTvRightAnther(boolean showTvRightAnther) {
        isShowTvRightAnther = showTvRightAnther;
    }


    public int getTvRightAntherTextSize() {
        return tvRightAntherTextSize;
    }

    public void setTvRightAntherTextSize(int tvRightAntherTextSize) {
        this.tvRightAntherTextSize = tvRightAntherTextSize;
    }

    public void setTvRightTextColor(int tvRightTextColor) {
        this.tvRightTextColor = tvRightTextColor;
    }

    public int getTvRightAntherTextColor() {
        return tvRightAntherTextColor;
    }

    public void setTvRightAntherTextColor(String tvRightAntherTextColor) {
        this.tvRightAntherTextColor = Color.parseColor(tvRightAntherTextColor);
    }

    public String getTvRightAntherStr() {
        return tvRightAntherStr;
    }

    public void setTvRightAntherStr(String tvRightAntherStr) {
        this.tvRightAntherStr = tvRightAntherStr;
    }
}
