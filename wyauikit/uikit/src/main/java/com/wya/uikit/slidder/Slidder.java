package com.wya.uikit.slidder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.wya.uikit.R;

/**
 * @author :
 */
public class Slidder {
    
    /**
     * round rectF
     */
    private int mSlidderLeft, mSlidderTop, mSlidderRight, mSlidderBottom;
    private float mCurPercent;
    
    private int mSlidderSize;
    private Drawable mSlidderDrawable;
    private Bitmap mSlidderBitmap;
    
    private WYASlidderView mRangeSlidder;
    private int mProgressWidth;
    
    public Slidder(Context context, WYASlidderView rangeSlidder) {
        mRangeSlidder = rangeSlidder;
        mSlidderSize = Utils.dp2px(context, 20);
        mSlidderDrawable = context.getResources().getDrawable(R.drawable.slidder_default_drawable);
    }
    
    void onSizeChanged(int centerX, int centerY, int progressWidth) {
        mSlidderLeft = centerX - mSlidderSize / 2;
        mSlidderTop = centerY - mSlidderSize / 2;
        mSlidderRight = centerX + mSlidderSize / 2;
        mSlidderBottom = centerY + mSlidderSize / 2;
        mProgressWidth = progressWidth;
        mSlidderBitmap = getBitmap();
    }
    
    private Bitmap getBitmap() {
        return Utils.drawableToBitmap(mSlidderSize, mSlidderDrawable);
    }
    
    public boolean isSliding(float x, float y) {
        int offset = (int) (mProgressWidth * getCurPercent());
        return x > getSlidderLeft() + offset && x < getSlidderRight() + offset && y > mSlidderTop && y < mSlidderBottom;
    }
    
    void slide(float percent) {
        percent = Math.max(0, percent);
        percent = Math.min(percent, 1);
        setCurPercent(percent);
    }
    
    void draw(Canvas canvas) {
        if (null == mSlidderBitmap || null == canvas) {
            return;
        }
        int offset = (int) (mProgressWidth * getCurPercent());
        canvas.save();
        canvas.translate(offset, 0);
        canvas.translate(getSlidderLeft(), 0);
        drawSlidder(canvas);
        canvas.restore();
    }
    
    private void drawSlidder(Canvas canvas) {
        if (null == canvas) {
            return;
        }
        canvas.drawBitmap(mSlidderBitmap, 0, mRangeSlidder.getProgressTop() + (mRangeSlidder.getProgressHeight() - mSlidderSize) / 2, null);
    }
    
    public int getSlidderSize() {
        return mSlidderSize;
    }
    
    public int getSlidderLeft() {
        return mSlidderLeft;
    }
    
    public int getSlidderRight() {
        return mSlidderRight;
    }
    
    public float getCurPercent() {
        return mCurPercent;
    }
    
    public void setCurPercent(float percent) {
        this.mCurPercent = percent;
    }
    
    public void setSlidderDrawable(Drawable drawable) {
        this.mSlidderDrawable = drawable;
    }
}