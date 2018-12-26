package com.wya.uikit.slideview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.wya.uikit.R;

import java.math.BigDecimal;

public class Slide {
    
    // round rectF
    private int mSliderLeft, mSliderTop, mSliderRight, mSliderBottom;
    private float mCurPercent;
    
    private int mSliderSize;
    private int mSliderDrawable = R.drawable.rsb_default_slide;
    private Bitmap mSliderBitmap;
    
    private Context mContext;
    private RangeSlideView mRangeSlidder;
    private int mProgressWidth;
    
    public Slide(Context context, RangeSlideView rangeSlidder) {
        mContext = context;
        mRangeSlidder = rangeSlidder;
        mSliderSize = Utils.dp2px(context, 20);
    }
    
    void onSizeChanged(int centerX, int centerY, int progressWidth) {
        mSliderLeft = centerX - mSliderSize / 2;
        mSliderTop = centerY - mSliderSize / 2;
        mSliderRight = centerX + mSliderSize / 2;
        mSliderBottom = centerY + mSliderSize / 2;
        mProgressWidth = progressWidth;
        mSliderBitmap = getBitmap();
    }
    
    private Bitmap getBitmap() {
        return Utils.drawableToBitmap(mSliderSize, mContext.getResources().getDrawable(mSliderDrawable));
    }
    
    public boolean isSliding(float x, float y) {
        int offset = (int) (mProgressWidth * getCurPercent());
        return x > getSliderLeft() + offset && x < getSliderRight() + offset && y > mSliderTop && y < mSliderBottom;
    }
    
    void slide(float percent) {
        percent = Math.max(0, percent);
        percent = Math.min(percent, 1);
        setCurPercent(percent);
    }
    
    void draw(Canvas canvas) {
        if (null == mSliderBitmap || null == canvas) {
            return;
        }
        int offset = (int) (mProgressWidth * getCurPercent());
        canvas.save();
        canvas.translate(offset, 0);
        canvas.translate(getSliderLeft(), 0);
        drawSlider(canvas);
        canvas.restore();
    }
    
    private void drawSlider(Canvas canvas) {
        if (null == canvas) {
            return;
        }
        canvas.drawBitmap(mSliderBitmap, 0, mRangeSlidder.getProgressTop() + (mRangeSlidder.getProgressHeight() - mSliderSize) / 2, null);
    }
    
    public int getSliderSize() {
        return mSliderSize;
    }
    
    public int getSliderLeft() {
        return mSliderLeft;
    }
    
    public int getSliderRight() {
        return mSliderRight;
    }
    
    public float getCurPercent() {
        try {
            BigDecimal bigDecimal = new BigDecimal((double) mCurPercent);
            bigDecimal = bigDecimal.setScale(2, 4);
            mCurPercent = bigDecimal.floatValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCurPercent;
    }
    
    public void setCurPercent(float percent) {
        this.mCurPercent = percent;
    }
}