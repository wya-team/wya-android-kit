package com.wya.uikit.slideview;

import android.graphics.drawable.Drawable;
public interface IRangeSlideView {
    
    void setSliderMode(int mode);
    
    void setProgressHeight(int height);
    
    void setProgressMin(int min);
    
    void setProgressMax(int max);
    
    void setProgressBackgroundColor(int color);
    
    void setProgressForegroundColor(int color);
    
    void setRegionPadding(int padding);
    
    void setRegionDrawableMin(Drawable drawableMin);
    
    void setRegionDrawableMax(Drawable drawableMax);
    
    void setRegionBitmapSize(int size);
    
    void setRegionTextColor(int textColor);
    
    void setRegionTextSize(int textSize);
    
}
