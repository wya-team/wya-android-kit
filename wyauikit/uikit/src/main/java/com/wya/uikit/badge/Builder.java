package com.wya.uikit.badge;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.text.TextUtils;
public class Builder {
    
    private int badgeNum;
    private String text;
    private float textSize;
    private int textColor;
    private int backgroundColor;
    private boolean isOmitMode;
    private Drawable backgroundDrawable;
    private float padding;
    private Gravity gravity;
    private boolean isShow;
    private Context context;
    
    @IntDef
    public @interface BadgeGravity {
        
        int GRAVITY_START_TOP = android.view.Gravity.START | android.view.Gravity.TOP;
        int GRAVITY_END_TOP = android.view.Gravity.END | android.view.Gravity.TOP;
        int GRAVITY_START_BOTTOM = android.view.Gravity.START | android.view.Gravity.BOTTOM;
        int GRAVITY_END_BOTTOM = android.view.Gravity.END | android.view.Gravity.BOTTOM;
        
        int GRAVITY_CENTER = android.view.Gravity.CENTER;
        
        int GRAVITY_CENTER_TOP = android.view.Gravity.CENTER | android.view.Gravity.TOP;
        int GRAVITY_CENTER_BOTTOM = android.view.Gravity.CENTER | android.view.Gravity.BOTTOM;
        int GRAVITY_CENTER_START = android.view.Gravity.CENTER | android.view.Gravity.START;
        int GRAVITY_CENTER_END = android.view.Gravity.CENTER | android.view.Gravity.END;
        
    }
    
    public static class Gravity {
        
        private int gravity = BadgeGravity.GRAVITY_END_TOP;
        float xOffset = 0;
        float yOffset = 0;
        
        public Gravity(@BadgeGravity int gravity, int xOffset, int yOffset) {
            this.gravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
        
        public int getGravity() {
            return gravity;
        }
        
        public void setGravity(int gravity) {
            this.gravity = gravity;
        }
        
        public void setxOffset(float xOffset) {
            this.xOffset = xOffset;
        }
        
        public void setyOffset(float yOffset) {
            this.yOffset = yOffset;
        }
        
        public float getXOffset() {
            return xOffset;
        }
        
        public float getYOffset() {
            return yOffset;
        }
    }
    
    public Builder(Context context) {
        this.context = context;
        this.gravity = new Gravity(Builder.BadgeGravity.GRAVITY_END_TOP, 0, 0);
        this.padding = DisplayUtil.dp2px(context, 5);
        this.textSize = DisplayUtil.sp2px(context, 11);
        this.textColor = Color.WHITE;
        this.backgroundColor = Color.RED;
        this.isShow = true;
    }
    
    public Builder setBackgroundDrawable(Drawable drawable) {
        this.backgroundDrawable = drawable;
        return this;
    }
    
    public Builder setBadgeNum(int badgeNum) {
        this.badgeNum = badgeNum;
        return this;
    }
    
    public Builder setText(String text) {
        this.text = text;
        return this;
    }
    
    public Builder setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }
    
    public Builder setTextColor(int color) {
        this.textColor = color;
        return this;
    }
    
    public Builder setGravity(Gravity gravity) {
        this.gravity = gravity;
        return this;
    }
    
    public Builder setOffset(int xOffset, int yOffset) {
        gravity.xOffset = xOffset;
        gravity.yOffset = yOffset;
        return this;
    }
    
    public Builder setOmitMode(boolean isOmitMode) {
        this.isOmitMode = isOmitMode;
        return this;
    }
    
    public Builder setPadding(int padding) {
        this.padding = padding;
        return this;
    }
    
    public Builder isShow(boolean isShow) {
        this.isShow = isShow;
        return this;
    }
    
    public IBadgeView create() {
        IBadgeView badgeView = new WYABadgeView(context);
        badgeView.setBadgeNum(badgeNum);
        
        if (!TextUtils.isEmpty(text)) {
            badgeView.setBadgeText(text);
        }
        
        badgeView.setTextSize(textSize);
        badgeView.setTextColor(textColor);
        badgeView.setBackgroundColor(backgroundColor);
        
        badgeView.setPadding(padding);
        if (null != backgroundDrawable) {
            badgeView.setBackgroundDrawable(backgroundDrawable);
        }
        badgeView.setPadding(padding);
        if (null != gravity) {
            badgeView.setGravity(gravity);
        }
        badgeView.setOmitMode(isOmitMode);
        badgeView.update(isShow);
        
        return badgeView;
    }
    
}
