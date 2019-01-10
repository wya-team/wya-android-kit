package com.wya.uikit.imagecrop.core;


public interface EditViewPortrait {
    
    /**
     * getWidth
     * @return
     */
    int getWidth();
    
    /**
     * getHeight
     * @return
     */
    int getHeight();
    
    /**
     * getScaleX
     * @return
     */
    float getScaleX();
    
    /**
     * getScaleY
     * @return
     */
    float getScaleY();
    
    /**
     * getRotation
     * @return
     */
    float getRotation();
    
    /**
     * getPivotX
     * @return
     */
    float getPivotX();
    
    /**
     * getPivotY
     * @return
     */
    float getPivotY();
    
    /**
     * getX
     * @return
     */
    float getX();
    
    /**
     * getY
     * @return
     */
    float getY();
    
    /**
     * setX
     * @param x
     */
    void setX(float x);
    
    /**
     * setY
     * @param y
     */
    void setY(float y);
    
    /**
     * setRotation
     * @param rotate
     */
    void setRotation(float rotate);
    
    /**
     * setScaleX
     * @param scaleX
     */
    void setScaleX(float scaleX);
    
    /**
     * setScaleY
     * @param scaleY
     */
    void setScaleY(float scaleY);
    
    /**
     * getScale
     * @return
     */
    float getScale();
    
    /**
     * setScale
     * @param scale
     */
    void setScale(float scale);
    
    /**
     * addScale
     * @param scale
     */
    void addScale(float scale);
}
