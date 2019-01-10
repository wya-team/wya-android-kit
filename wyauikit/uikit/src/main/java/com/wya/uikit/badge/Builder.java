package com.wya.uikit.badge;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * @author :
 */
public class Builder {
    
    private int mBadgeNum;
    private String mText;
    private float mTextSize;
    private int mTextColor;
    private int mBackgroundColor;
    private boolean mIsOmitMode;
    private int mOmitNum;
    private String mOmitText;
    private Drawable mBackgroundDrawable;
    private float mPadding;
    private Gravity mGravity;
    private boolean mIsShow;
    private Context mContext;
    
    private boolean mIsAttach;
    private Drawable mBadgeDrawable;
    private int mBadgeDrawableSize;
    
    public Builder(Context context) {
        this.mContext = context;
        this.mGravity = new Gravity(BadgeGravity.GRAVITY_END_TOP, 0, 0);
        this.mPadding = DisplayUtil.dp2px(context, 5);
        this.mTextSize = DisplayUtil.sp2px(context, 11);
        this.mTextColor = Color.WHITE;
        this.mBackgroundColor = Color.RED;
        this.mIsShow = true;
        this.mIsAttach = true;
    }
    
    public Builder setBackgroundDrawable(Drawable drawable) {
        this.mBackgroundDrawable = drawable;
        return this;
    }
    
    public Builder setBadgeNum(int badgeNum) {
        this.mBadgeNum = badgeNum;
        return this;
    }
    
    public Builder setText(String text) {
        this.mText = text;
        return this;
    }
    
    public Builder setTextSize(float textSize) {
        this.mTextSize = textSize;
        return this;
    }
    
    public Builder setTextColor(int color) {
        this.mTextColor = color;
        return this;
    }
    
    public Builder setGravity(Gravity gravity) {
        this.mGravity = gravity;
        return this;
    }
    
    public Builder setOffset(int offsetX, int offsetY) {
        mGravity.offsetX = offsetX;
        mGravity.offsetY = offsetY;
        return this;
    }
    
    public Builder setOmitMode(boolean isOmitMode) {
        this.mIsOmitMode = isOmitMode;
        return this;
    }
    
    public Builder setOmitNum(int omitNum) {
        this.mOmitNum = omitNum;
        return this;
    }
    
    public Builder setOmitText(String omitText) {
        this.mOmitText = omitText;
        return this;
    }
    
    public Builder setPadding(int padding) {
        this.mPadding = padding;
        return this;
    }
    
    public Builder isShow(boolean isShow) {
        this.mIsShow = isShow;
        return this;
    }
    
    public Builder setAttach(boolean isAttach) {
        this.mIsAttach = isAttach;
        return this;
    }
    
    public Builder setBadgeDrawable(Drawable drawable) {
        this.mBadgeDrawable = drawable;
        return this;
    }
    
    public Builder setBadgeDrawableSize(int size) {
        this.mBadgeDrawableSize = size;
        return this;
    }
    
    public IBadgeView create() {
        IBadgeView badgeView = new WYABadgeView(mContext);
        if (null != mBadgeDrawable) {
            badgeView.setBadgeDrawable(mBadgeDrawable);
            badgeView.setBadgeDrawableSize(mBadgeDrawableSize);
        } else {
            badgeView.setBadgeNum(mBadgeNum);
            if (!TextUtils.isEmpty(mText)) {
                badgeView.setBadgeText(mText);
            }
            badgeView.setTextSize(mTextSize);
            badgeView.setTextColor(mTextColor);
            badgeView.setBackgroundColor(mBackgroundColor);
            if (null != mBackgroundDrawable) {
                badgeView.setBackgroundDrawable(mBackgroundDrawable);
            }
            badgeView.setOmitMode(mIsOmitMode);
            badgeView.setOmitNum(mOmitNum);
            badgeView.setOmitText(mOmitText);
        }
        badgeView.setAttach(mIsAttach);
        badgeView.setPadding(mPadding);
        if (null != mGravity) {
            badgeView.setGravity(mGravity);
        }
        badgeView.updateIsShow(mIsShow);
        badgeView.invalidateBadge();
        return badgeView;
    }
    
    public static class Gravity {
        
        float offsetX;
        float offsetY;
        private int gravity;
        
        public Gravity(@BadgeGravity int gravity, int offsetX, int offsetY) {
            this.gravity = gravity;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }
        
        public int getGravity() {
            return gravity;
        }
        
        public void setGravity(int gravity) {
            this.gravity = gravity;
        }
        
        public void setOffsetX(float offsetX) {
            this.offsetX = offsetX;
        }
        
        public void setOffsetY(float offsetY) {
            this.offsetY = offsetY;
        }
    }
    
}
