package com.wya.uikit.badge;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @author :
 */
public interface IBadgeView {
    
    /**
     * 设置数字类型徽标
     *
     * @param badgeNum :
     */
    void setBadgeNum(int badgeNum);
    
    /**
     * 设置文本类型徽标
     *
     * @param badgeText :
     */
    void setBadgeText(String badgeText);
    
    /**
     * 设置数字类型时超过某个数值省略
     *
     * @param omitNum :
     */
    void setOmitNum(int omitNum);
    
    /**
     * 设置省略模式时的文本
     *
     * @param omitText :
     */
    void setOmitText(String omitText);
    
    /**
     * 设置文本颜色
     *
     * @param color :
     */
    void setTextColor(int color);
    
    /**
     * 设置文本尺寸
     *
     * @param size :
     */
    void setTextSize(float size);
    
    /**
     * 设置数字类型时是否省略模式
     *
     * @param isOmit :
     */
    void setOmitMode(boolean isOmit);
    
    /**
     * 设置背景颜色
     *
     * @param color :
     */
    void setBackgroundColor(int color);
    
    /**
     * 设置背景Drawable
     *
     * @param drawable :
     */
    void setBackgroundDrawable(Drawable drawable);
    
    /**
     * 设置padding
     *
     * @param padding :
     */
    void setPadding(float padding);
    
    /**
     * 设置Gravity
     *
     * @param gravity :
     */
    void setGravity(Builder.Gravity gravity);
    
    /**
     * 设置偏移量
     *
     * @param offsetX :
     * @param offsetY :
     */
    void setOffset(float offsetX, float offsetY);
    
    /**
     * 绑定目标View
     *
     * @param view :
     */
    void bindToTarget(View view);
    
    /**
     * 更新是否显示
     *
     * @param isShow :
     */
    void updateIsShow(boolean isShow);
    
    /**
     * 设置是否依附于某个View
     *
     * @param isAttach :
     */
    void setAttach(boolean isAttach);
    
    /**
     * 设置图片类型Drawable
     *
     * @param drawable :
     */
    void setBadgeDrawable(Drawable drawable);
    
    /**
     * 设置图片类型Drawable尺寸
     *
     * @param size :
     */
    void setBadgeDrawableSize(int size);
    
    /**
     * 绘制徽标
     */
    void invalidateBadge();
    
}
