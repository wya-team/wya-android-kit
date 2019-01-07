package com.wya.uikit.slidder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wya.uikit.R;

import java.math.BigDecimal;

/**
 * @author :
 */
public class RangeSlidderView extends View implements IRangeSlidderView {
    
    /**
     * progress
     */
    private final int SLIDDER_MODE_RANGDE = 0;
    private final int SLIDDER_MODE_SINGLE = 1;
    private int mSlidderMode = SLIDDER_MODE_RANGDE;
    private Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mProgressBackgroundRectF = new RectF();
    private int mProgressLeft, mProgressTop, mProgressRight, mProgressBottom;
    private int mProgressWidth, mProgressHeight;
    private float mProgressCorners;
    private int mPorgressBackgroundColor;
    private int mProgressForegroundColor;
    
    /**
     * region
     */
    private final int REGION_MODE_INTEGER = 0;
    private final int REGION_MODE_FLOAT = 1;
    private int mRegionMode = REGION_MODE_INTEGER;
    private Drawable mDrawableRegionMin;
    private Drawable mDrawableRegionMax;
    private int mRegionBitmapSize;
    private int mRegionTextColor;
    private int mRegionTextSize;
    private int mRegionPadding;
    private Paint mRegionPaint;
    
    /**
     * slidder
     */
    private Slidder mLeftSlidder, mRightSlidder;
    private Slidder mCurSlidder;
    
    private Drawable mSlidderDrawable;
    
    /**
     * range
     */
    private int mProgressMin, mProgressMax;
    private float reservePercent;
    private boolean mHasMin, mHasMax;
    
    private Context mContext;
    
    private float mCurDownX;
    private OnSlidderViewChangedListener mCallback;
    
    public RangeSlidderView(Context context) {
        this(context, null);
    }
    
    public RangeSlidderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public RangeSlidderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        parseAttrs(context, attrs, defStyleAttr, R.style.style_slidder_global_option);
        init();
    }
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RangeSlidderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    private void init() {
        initProgressPaint();
        initRegionPaint();
        initSlidder();
        initProgress();
        initRange(mProgressMin, mProgressMax);
        setProgress(mProgressMin, mProgressMax);
    }
    
    private void initRegionPaint() {
        mRegionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRegionPaint.setColor(mRegionTextColor);
        mRegionPaint.setTextSize(mRegionTextSize);
    }
    
    private void parseAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (null == context || attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RangeSlidderView, defStyleAttr, defStyleRes);
        if (null != typedArray) {
            // progress
            mSlidderMode = typedArray.getInteger(R.styleable.RangeSlidderView_slidderMode, SLIDDER_MODE_SINGLE);
            mProgressHeight = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlidderView_slidderProgressHeight, Utils.dp2px(context, 2))).intValue();
            if (typedArray.hasValue(R.styleable.RangeSlidderView_slidderProgressMin)) {
                mProgressMin = typedArray.getInteger(R.styleable.RangeSlidderView_slidderProgressMin, -1);
                mHasMin = true;
            }
            if (typedArray.hasValue(R.styleable.RangeSlidderView_slidderProgressMax)) {
                mProgressMax = typedArray.getInteger(R.styleable.RangeSlidderView_slidderProgressMax, -1);
                mHasMax = true;
            }
            
            if (typedArray.hasValue(R.styleable.RangeSlidderView_slidderDrawable)) {
                mSlidderDrawable = typedArray.getDrawable(R.styleable.RangeSlidderView_slidderDrawable);
            }
            
            mPorgressBackgroundColor = typedArray.getColor(R.styleable.RangeSlidderView_slidderProgressBackgroundColor, getResources().getColor(R.color.slidder_background_default_color));
            mProgressForegroundColor = typedArray.getColor(R.styleable.RangeSlidderView_slidderProgressForegroundColor, getResources().getColor(R.color.slidder_foreground_default_color));
            
            // region
            mRegionMode = typedArray.getInteger(R.styleable.RangeSlidderView_slidderRegionMode, REGION_MODE_INTEGER);
            if (typedArray.hasValue(R.styleable.RangeSlidderView_slidderRegionPadding)) {
                mRegionPadding = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlidderView_slidderRegionPadding, Utils.dp2px(mContext, 10))).intValue();
            }
            mDrawableRegionMin = typedArray.getDrawable(R.styleable.RangeSlidderView_slidderRegionDrawableMin);
            mDrawableRegionMax = typedArray.getDrawable(R.styleable.RangeSlidderView_slidderRegionDrawableMax);
            mRegionBitmapSize = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlidderView_slidderRegionBitmapSize, Utils.dp2px(mContext, 20))).intValue();
            mRegionTextColor = typedArray.getColor(R.styleable.RangeSlidderView_slidderRegionTextColor, Color.BLACK);
            mRegionTextSize = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlidderView_slidderRegionTextSize, Utils.sp2px(mContext, 14))).intValue();
            
            typedArray.recycle();
        }
    }
    
    public void initRange(int min, int max) {
        if (max <= min) {
            return;
        }
        mProgressMax = max;
        mProgressMin = min;
        
        dealRange();
        invalidate();
    }
    
    public void setProgress(float leftValue, float rightValue) {
        leftValue = Math.min(leftValue, rightValue);
        rightValue = Math.max(leftValue, rightValue);
        if (leftValue < mProgressMin || rightValue > mProgressMax) {
            return;
        }
        float range = mProgressMax - mProgressMin;
        mLeftSlidder.setCurPercent(Math.abs(leftValue - mProgressMin) / range);
        if (mRightSlidder != null) {
            mRightSlidder.setCurPercent(Math.abs(rightValue - mProgressMin) / range);
        }
        
        if (mCallback != null) {
            mCallback.onProgressChanged(this, leftValue, rightValue, false);
        }
        invalidate();
    }
    
    private void dealRange() {
        switch (mSlidderMode) {
            case SLIDDER_MODE_RANGDE:
                dealRangeModeRange();
                break;
            case SLIDDER_MODE_SINGLE:
            default:
                dealSingleModeRange();
                break;
        }
    }
    
    private void dealRangeModeRange() {
        if (null == mRightSlidder) {
            return;
        }
        if (mLeftSlidder.getCurPercent() + reservePercent <= 1 && mLeftSlidder.getCurPercent() + reservePercent > mRightSlidder.getCurPercent()) {
            mRightSlidder.setCurPercent(mLeftSlidder.getCurPercent() + reservePercent);
        } else if (mRightSlidder.getCurPercent() - reservePercent >= 0 && mRightSlidder.getCurPercent() - reservePercent < mLeftSlidder.getCurPercent()) {
            mLeftSlidder.setCurPercent(mRightSlidder.getCurPercent() - reservePercent);
        }
    }
    
    private void dealSingleModeRange() {
        if (mLeftSlidder.getCurPercent() < 1 - reservePercent && 1 - reservePercent >= 0) {
            mLeftSlidder.setCurPercent(1 - reservePercent);
        }
    }
    
    private void initSlidder() {
        mLeftSlidder = new Slidder(mContext, this);
        if (null != mSlidderDrawable) {
            mLeftSlidder.setSlidderDrawable(mSlidderDrawable);
        }
        if (mSlidderMode == SLIDDER_MODE_RANGDE) {
            mRightSlidder = new Slidder(mContext, this);
            if (null != mSlidderDrawable) {
                mRightSlidder.setSlidderDrawable(mSlidderDrawable);
            }
        }
    }
    
    private void initProgress() {
        if (null == mLeftSlidder) {
            return;
        }
        // progress top
        float regionSize = null != mDrawableRegionMax ? mRegionBitmapSize : mRegionTextSize;
        int maxTop;
        if (mRightSlidder == null) {
            maxTop = Double.valueOf(Math.max(mLeftSlidder.getSlidderSize() / 2, regionSize / 2)).intValue();
            mProgressTop = Double.valueOf(maxTop - getProgressHeight() / 2).intValue();
        } else {
            int maxSlidderSize = Math.max(mLeftSlidder.getSlidderSize(), mRightSlidder.getSlidderSize());
            maxTop = Double.valueOf(Math.max(maxSlidderSize / 2, regionSize / 2)).intValue();
            mProgressTop = Double.valueOf(maxTop - getProgressHeight() / 2).intValue();
        }
        
        // progress bottom
        mProgressBottom = Double.valueOf(getProgressTop() + getProgressHeight()).intValue();
        
        // progress corner
        if (mProgressCorners < 0) {
            mProgressCorners = (int) ((getProgressBottom() - getProgressTop()) * 0.5f);
        }
    }
    
    private void initProgressPaint() {
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(getResources().getColor(R.color.slidder_foreground_default_color));
        mProgressPaint.setStyle(Paint.Style.FILL);
    }
    
    @SuppressLint("SwitchIntDef")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        
        int slidderSize = mLeftSlidder.getSlidderSize();
        int regionSize = null != mDrawableRegionMax ? mRegionBitmapSize : mRegionTextSize;
        int heightNeeded = Math.max(slidderSize, regionSize);
        
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                heightSize = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.AT_MOST:
                heightSize = MeasureSpec.makeMeasureSpec(heightNeeded, MeasureSpec.AT_MOST);
                break;
            default:
                heightSize = MeasureSpec.makeMeasureSpec(heightNeeded, MeasureSpec.EXACTLY);
                break;
        }
        super.onMeasure(widthMeasureSpec, heightSize);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // progress left
        String minString = mRegionMode == REGION_MODE_INTEGER ? String.valueOf(mProgressMin) : String.valueOf(Float.valueOf(mProgressMin));
        int regionMinTextSize = mHasMin ? Double.valueOf(mRegionPaint.measureText(minString)).intValue() : 0;
        int regionMinSize = mHasMin ? Double.valueOf(null != mDrawableRegionMin ? mRegionBitmapSize : regionMinTextSize).intValue() : 0;
        mProgressLeft = getPaddingLeft() + regionMinSize + mRegionPadding + mLeftSlidder.getSlidderSize() / 2;
        
        // progress right
        String maxString = mRegionMode == REGION_MODE_INTEGER ? String.valueOf(mProgressMax) : String.valueOf(Float.valueOf(mProgressMax));
        int regionMaxTextSize = mHasMax ? Double.valueOf(mRegionPaint.measureText(maxString)).intValue() : 0;
        int regionMaxSize = mHasMax ? Double.valueOf(null != mDrawableRegionMax ? mRegionBitmapSize : regionMaxTextSize).intValue() : 0;
        int rightSlidderSize = mLeftSlidder.getSlidderSize();
        mProgressRight = w - getPaddingRight() - regionMaxSize - mRegionPadding - rightSlidderSize / 2;
        
        // progress rectF
        mProgressBackgroundRectF.set(getProgressLeft(), getProgressTop(), getProgressRight(), getProgressBottom());
        
        // progress width
        mProgressWidth = getProgressRight() - getProgressLeft();
        
        // seekbar
        int cx = getProgressLeft();
        int cy = Double.valueOf(getProgressBottom() - getProgressHeight() / 2).intValue();
        mLeftSlidder.onSizeChanged(cx, cy, Double.valueOf(mProgressWidth).intValue());
        if (null != mRightSlidder) {
            mRightSlidder.onSizeChanged(cx, cy, Double.valueOf(mProgressWidth).intValue());
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
        drawLeftSB(canvas);
        drawRightSB(canvas);
        drawRegion(canvas);
    }
    
    /**
     * @param canvas :
     */
    private void drawRegion(Canvas canvas) {
        if (null == canvas) {
            return;
        }
        drawMinRegion(canvas);
        drawMaxRegion(canvas);
    }
    
    private void drawMinRegion(Canvas canvas) {
        if (mRegionBitmapSize <= 0) {
            return;
        }
        if (null != mDrawableRegionMin) {
            drawMinRegionBitmap(canvas);
        } else if (mProgressMax != mProgressMin && mHasMin) {
            drawMinRegionText(canvas);
        }
    }
    
    private void drawMinRegionText(Canvas canvas) {
        String min = String.valueOf(mLeftSlidder.getCurPercent() * (mProgressMax - mProgressMin));
        BigDecimal bigDecimal = new BigDecimal((double) Float.parseFloat(min));
        bigDecimal = bigDecimal.setScale(1, 4);
        float minFloat = bigDecimal.floatValue();
        switch (mRegionMode) {
            case REGION_MODE_FLOAT:
                min = String.valueOf(minFloat);
                break;
            case REGION_MODE_INTEGER:
                int minInt = Double.valueOf(String.valueOf(minFloat)).intValue();
                min = String.valueOf(minInt);
                break;
            default:
                break;
        }
        
        float x = getProgressLeft() - mRegionPadding - mLeftSlidder.getSlidderSize() / 2 - mRegionPaint.measureText(min);
        Paint.FontMetricsInt fontMetricsInt = mRegionPaint.getFontMetricsInt();
        float y = getProgressTop() + (getProgressHeight()) / 2 - (fontMetricsInt.bottom + fontMetricsInt.top) / 2;
        canvas.drawText(min, x, y, mRegionPaint);
    }
    
    private void drawMinRegionBitmap(Canvas canvas) {
        Bitmap bitmapLeft = getBitmap(Double.valueOf(mRegionBitmapSize).intValue(), mDrawableRegionMin);
        canvas.drawBitmap(bitmapLeft, getPaddingLeft(), getProgressTop() + (getProgressHeight() - mRegionBitmapSize) / 2, null);
    }
    
    private void drawMaxRegion(Canvas canvas) {
        if (mRegionBitmapSize <= 0) {
            return;
        }
        if (null != mDrawableRegionMax) {
            drawMaxRegionBitmap(canvas);
        } else if (mProgressMax != mProgressMin && mHasMax) {
            drawMaxRegionText(canvas);
        }
    }
    
    private void drawMaxRegionText(Canvas canvas) {
        String max = String.valueOf(null == mRightSlidder ? mProgressMax : (mProgressMax - mProgressMin) * mRightSlidder.getCurPercent());
        BigDecimal bigDecimal = new BigDecimal((double) Float.parseFloat(max));
        bigDecimal = bigDecimal.setScale(2, 4);
        float maxFloat = bigDecimal.floatValue();
        switch (mRegionMode) {
            case REGION_MODE_FLOAT:
                max = String.valueOf(maxFloat);
                break;
            case REGION_MODE_INTEGER:
                int minInt = Double.valueOf(String.valueOf(maxFloat)).intValue();
                max = String.valueOf(minInt);
                break;
            default:
                break;
        }
        
        float x = getWidth() - getPaddingRight() - mRegionPaint.measureText(max);
        Paint.FontMetricsInt fontMetricsInt = mRegionPaint.getFontMetricsInt();
        float y = getProgressTop() + (getProgressHeight()) / 2 - (fontMetricsInt.bottom + fontMetricsInt.top) / 2;
        canvas.drawText(max, x, y, mRegionPaint);
    }
    
    private void drawMaxRegionBitmap(Canvas canvas) {
        Bitmap bitmapRight = getBitmap(Double.valueOf(mRegionBitmapSize).intValue(), mDrawableRegionMax);
        canvas.drawBitmap(bitmapRight, getWidth() - getPaddingRight() - mRegionBitmapSize, getProgressTop() + (getProgressHeight() - mRegionBitmapSize) / 2, null);
    }
    
    private Bitmap getBitmap(int size, Drawable drawable) {
        if (null == drawable || size <= 0) {
            return null;
        }
        return Utils.drawableToBitmap(size, drawable);
    }
    
    /**
     * draw background & forground
     *
     * @param canvas :
     */
    private void drawProgress(Canvas canvas) {
        if (null == mProgressPaint || null == canvas) {
            return;
        }
        drawProgressBackground(canvas);
        drawProgressForeground(canvas);
    }
    
    /**
     * background : RoundRect
     *
     * @param canvas :
     */
    private void drawProgressBackground(Canvas canvas) {
        mProgressPaint.setColor(mPorgressBackgroundColor);
        canvas.drawRoundRect(mProgressBackgroundRectF, mProgressCorners, mProgressCorners, mProgressPaint);
    }
    
    /**
     * foreground : RoundRect
     *
     * @param canvas :
     */
    private void drawProgressForeground(Canvas canvas) {
        mProgressPaint.setColor(mProgressForegroundColor);
        RectF rectF;
        switch (mSlidderMode) {
            case SLIDDER_MODE_RANGDE:
                rectF = getRangeForegroundRectF();
                break;
            case SLIDDER_MODE_SINGLE:
            default:
                rectF = getSingleForegroundRectF();
                break;
        }
        
        if (null != rectF) {
            canvas.drawRoundRect(rectF, mProgressCorners, mProgressCorners, mProgressPaint);
        }
    }
    
    private RectF getSingleForegroundRectF() {
        RectF rectF = new RectF();
        float left = calProgressLeft();
        float slidderSize = calSlidderSize(mLeftSlidder.getCurPercent());
        rectF.left = left;
        rectF.top = getProgressTop();
        rectF.right = left + slidderSize;
        rectF.bottom = getProgressBottom();
        return rectF;
    }
    
    private RectF getRangeForegroundRectF() {
        if (null == mRightSlidder) {
            return null;
        }
        RectF rectF = new RectF();
        float left = calProgressLeft();
        float leftSlidderSize = calSlidderSize(mLeftSlidder.getCurPercent());
        float rightSlidderSize = calSlidderSize(mRightSlidder.getCurPercent());
        rectF.left = left + leftSlidderSize;
        rectF.top = getProgressTop();
        rectF.right = left + rightSlidderSize;
        rectF.bottom = getProgressBottom();
        return rectF;
    }
    
    private float calProgressLeft() {
        return mLeftSlidder.getSlidderLeft() + mLeftSlidder.getSlidderSize() / 2;
    }
    
    private float calSlidderSize(float curPercent) {
        return mProgressWidth * curPercent;
    }
    
    private void drawLeftSB(Canvas canvas) {
        mLeftSlidder.draw(canvas);
    }
    
    private void drawRightSB(Canvas canvas) {
        if (mRightSlidder != null) {
            mRightSlidder.draw(canvas);
        }
    }
    
    protected float getEventX(MotionEvent event) {
        return event.getX();
    }
    
    protected float getEventY(MotionEvent event) {
        return event.getY();
    }
    
    private Slidder getCurrTouchSB(MotionEvent event) {
        Slidder currTouchSB = null;
        if (isTouching(mLeftSlidder, event)) {
            currTouchSB = mLeftSlidder;
        } else if (isTouching(mRightSlidder, event)) {
            currTouchSB = mRightSlidder;
        }
        return currTouchSB;
    }
    
    private boolean isTouching(Slidder seekBar, MotionEvent event) {
        if (null == seekBar || null == event) {
            return false;
        }
        return seekBar.isSliding(getEventX(event), getEventY(event));
    }
    
    private boolean onActionDown(MotionEvent event) {
        mCurDownX = getEventX(event);
        boolean result = false;
        switch (mSlidderMode) {
            case SLIDDER_MODE_RANGDE: {
                mCurSlidder = getCurrTouchSB(event);
                result = true;
                break;
            }
            case SLIDDER_MODE_SINGLE:
            default: {
                if (isTouching(mLeftSlidder, event)) {
                    mCurSlidder = mLeftSlidder;
                    result = true;
                }
                break;
            }
        }
        if (mCallback != null) {
            mCallback.onStartTrackingTouch(this, mCurSlidder == mLeftSlidder);
        }
        return result;
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isReset;
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                return onActionDown(event);
            }
            case MotionEvent.ACTION_MOVE:
                float percent;
                float x = getEventX(event);
                boolean touchable = null != getRangeForegroundRectF() && getRangeForegroundRectF().right - getRangeForegroundRectF().left <= mLeftSlidder.getSlidderSize();
                isReset = true;
                float curLeftPercent;
                float curRightPercent;
                
                float slidderPercent = mLeftSlidder.getSlidderSize() * 1f / mProgressWidth;
                if (mSlidderMode == SLIDDER_MODE_RANGDE) {
                    // right
                    if (null != mRightSlidder && mCurSlidder == mRightSlidder) {
                        if (mCurDownX < x) {
                            isReset = false;
                        }
                        if (isReset && touchable) {
                            break;
                        }
                        percent = x > getProgressRight() ? 1 : (x - getProgressLeft()) * 1f / (mProgressWidth);
                        curRightPercent = percent;
                        if (null != mRightSlidder && curRightPercent - mLeftSlidder.getCurPercent() <= slidderPercent) {
                            curRightPercent = Math.max(mLeftSlidder.getCurPercent() + slidderPercent, 0);
                        }
                        mRightSlidder.slide(curRightPercent);
                    }
                }
                // left
                if (mCurSlidder == mLeftSlidder) {
                    if (mCurDownX > x) {
                        isReset = false;
                    }
                    if (isReset && touchable) {
                        break;
                    }
                    percent = x < getProgressLeft() ? 0 : (x - getProgressLeft()) * 1f / (mProgressWidth);
                    curLeftPercent = percent;
                    if (null != mRightSlidder && mRightSlidder.getCurPercent() - curLeftPercent <= slidderPercent) {
                        curLeftPercent = Math.max(mRightSlidder.getCurPercent() - slidderPercent, 0);
                    }
                    mLeftSlidder.slide(curLeftPercent);
                }
                mCurDownX = x;
                
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mCallback != null) {
                    mCallback.onStopTrackingTouch(this, mCurSlidder == mLeftSlidder);
                }
                break;
            
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
    
    public int getProgressLeft() {
        return mProgressLeft;
    }
    
    public int getProgressTop() {
        return mProgressTop;
    }
    
    public int getProgressRight() {
        return mProgressRight;
    }
    
    public int getProgressBottom() {
        return mProgressBottom;
    }
    
    public float getProgressHeight() {
        return mProgressHeight;
    }
    
    @Override
    public void setProgressHeight(int height) {
        this.mProgressHeight = height;
    }
    
    @Override
    public void setProgressMin(int min) {
        this.mProgressMin = min;
    }
    
    @Override
    public void setProgressMax(int max) {
        this.mProgressMax = max;
    }
    
    @Override
    public void setProgressBackgroundColor(int color) {
        this.mPorgressBackgroundColor = color;
    }
    
    @Override
    public void setProgressForegroundColor(int color) {
        this.mProgressForegroundColor = color;
    }
    
    @Override
    public void setSlidderMode(int mode) {
        this.mSlidderMode = mode;
    }
    
    @Override
    public void setRegionBitmapSize(int size) {
        this.mRegionBitmapSize = size;
    }
    
    @Override
    public void setRegionPadding(int padding) {
        this.mRegionPadding = padding;
    }
    
    @Override
    public void setRegionTextColor(int textColor) {
        this.mRegionTextColor = textColor;
    }
    
    @Override
    public void setRegionTextSize(int textSize) {
        this.mRegionTextSize = textSize;
    }
    
    @Override
    public void setRegionDrawableMin(Drawable drawableMin) {
        this.mDrawableRegionMin = drawableMin;
    }
    
    @Override
    public void setRegionDrawableMax(Drawable drawableMax) {
        this.mDrawableRegionMax = drawableMax;
    }
    
}
