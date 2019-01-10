package com.wya.uikit.imagecrop.core;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public interface EditViewPortrait {
    
    /**
     * getWidth
     *
     * @return
     */
    int getWidth();
    
    /**
     * getHeight
     *
     * @return
     */
    int getHeight();
    
    /**
     * getScaleX
     *
     * @return
     */
    float getScaleX();
    
    /**
     * setScaleX
     *
     * @param scaleX
     */
    void setScaleX(float scaleX);
    
    /**
     * getScaleY
     *
     * @return
     */
    float getScaleY();
    
    /**
     * setScaleY
     *
     * @param scaleY
     */
    void setScaleY(float scaleY);
    
    /**
     * getRotation
     *
     * @return
     */
    float getRotation();
    
    /**
     * setRotation
     *
     * @param rotate
     */
    void setRotation(float rotate);
    
    /**
     * getPivotX
     *
     * @return
     */
    float getPivotX();
    
    /**
     * getPivotY
     *
     * @return
     */
    float getPivotY();
    
    /**
     * getX
     *
     * @return
     */
    float getX();
    
    /**
     * setX
     *
     * @param x
     */
    void setX(float x);
    
    /**
     * getY
     *
     * @return
     */
    float getY();
    
    /**
     * setY
     *
     * @param y
     */
    void setY(float y);
    
    /**
     * getScale
     *
     * @return
     */
    float getScale();
    
    /**
     * setScale
     *
     * @param scale
     */
    void setScale(float scale);
    
    /**
     * addScale
     *
     * @param scale
     */
    void addScale(float scale);
}
