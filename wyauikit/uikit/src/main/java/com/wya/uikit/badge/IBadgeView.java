package com.wya.uikit.badge;

import android.graphics.drawable.Drawable;
import android.view.View;

public interface IBadgeView {
    
    void setBadgeNum(int badgeNum);
    
    void setBadgeText(String badgeText);
    
    void setOmitNum(int omitNum);
    
    void setOmitText(String omitText);
    
    void setTextColor(int color);
    
    void setTextSize(float size);
    
    void setOmitMode(boolean isOmit);
    
    void setBackgroundColor(int color);
    
    void setBackgroundDrawable(Drawable drawable);
    
    void setPadding(float padding);
    
    void setGravity(Builder.Gravity gravity);
    
    void setOffset(float offsetX, float offsetY);
    
    void bindToTarget(View view);
    
    void update(boolean isShow);
    
}
