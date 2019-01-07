package com.wya.uikit.slidder;

import android.graphics.drawable.Drawable;

public interface IRangeSlidderView {
    
    /**
     * 设置模式
     *
     * @param mode : single / range
     */
    void setSlidderMode(int mode);
    
    /**
     * 设置进度条高度
     *
     * @param height :
     */
    void setProgressHeight(int height);
    
    /**
     * 设置进度条最小值
     *
     * @param min :
     */
    void setProgressMin(int min);
    
    /**
     * 设置进度条最大值
     *
     * @param max :
     */
    void setProgressMax(int max);
    
    /**
     * 设置进度条背景颜色
     *
     * @param color :
     */
    void setProgressBackgroundColor(int color);
    
    /**
     * 设置进度条前景颜色
     *
     * @param color :
     */
    void setProgressForegroundColor(int color);
    
    /**
     * 设置区间padding
     *
     * @param padding :
     */
    void setRegionPadding(int padding);
    
    /**
     * 设置最小值drawable
     *
     * @param drawableMin :
     */
    void setRegionDrawableMin(Drawable drawableMin);
    
    /**
     * 设置最大值drawable
     *
     * @param drawableMax :
     */
    void setRegionDrawableMax(Drawable drawableMax);
    
    /**
     * 设置区间图片大小
     *
     * @param size :
     */
    void setRegionBitmapSize(int size);
    
    /**
     * 设置区间文本颜色
     *
     * @param textColor :
     */
    void setRegionTextColor(int textColor);
    
    /**
     * 设置区间文本大小
     *
     * @param textSize :
     */
    void setRegionTextSize(int textSize);
    
}
