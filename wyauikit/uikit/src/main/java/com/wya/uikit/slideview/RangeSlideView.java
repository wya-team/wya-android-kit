package com.wya.uikit.slideview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wya.uikit.R;

import java.math.BigDecimal;
public class RangeSlideView extends View implements IRangeSlideView {
    
    // progress
    private Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mProgressBackgroundRectF = new RectF();
    private int mProgressLeft, mProgressTop, mProgressRight, mProgressBottom;
    private int mProgressWidth, mProgressHeight;
    private float mProgressCorners;
    private int mPorgressBackgroundColor;
    private int mProgressForegroundColor;
    
    // region
    private Drawable mDrawableRegionMin;
    private Drawable mDrawableRegionMax;
    private int mRegionBitmapSize;
    private int mRegionTextColor;
    private int mRegionTextSize;
    private int mRegionPadding;
    private Paint mRegionPaint;
    
    // mode
    private final int SLIDDER_MODE_RANGDE = 0;
    private final int SLIDDER_MODE_SINGLE = 1;
    private int mSlidderMode = SLIDDER_MODE_RANGDE;
    
    // seekbar
    private Slide mLeftSlider, mRightSlider;
    private Slide mCurSlider;
    
    // range
    private int mProgressMin, mProgressMax;
    private float reservePercent;
    private boolean mHasMin, mHasMax;
    
    private Context mContext;
    
    private float mCurDownX;
    private OnSlideViewChangedListener callback;
    
    public RangeSlideView(Context context) {
        this(context, null);
    }
    
    public RangeSlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public RangeSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        parseAttrs(context, attrs);
        init();
    }
    
    private void init() {
        initProgressPaint();
        initRegionPaint();
        initSlider();
        initProgress();
        initRange(mProgressMin, mProgressMax);
        setProgress(mProgressMin, mProgressMax);
    }
    
    private void initRegionPaint() {
        mRegionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRegionPaint.setColor(mRegionTextColor);
        mRegionPaint.setTextSize(mRegionTextSize);
    }
    
    private void parseAttrs(Context context, AttributeSet attrs) {
        if (null == context || attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RangeSlideView);
        if (null != typedArray) {
            // progress
            mSlidderMode = typedArray.getInteger(R.styleable.RangeSlideView_rsd_slider_mode, SLIDDER_MODE_SINGLE);
            mProgressHeight = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlideView_rsd_progress_height, Utils.dp2px(context, 2))).intValue();
            if (typedArray.hasValue(R.styleable.RangeSlideView_rsd_progress_min)) {
                mProgressMin = typedArray.getInteger(R.styleable.RangeSlideView_rsd_progress_min, -1);
                mHasMin = true;
            }
            if (typedArray.hasValue(R.styleable.RangeSlideView_rsd_progress_max)) {
                mProgressMax = typedArray.getInteger(R.styleable.RangeSlideView_rsd_progress_max, -1);
                mHasMax = true;
            }
            
            mPorgressBackgroundColor = typedArray.getColor(R.styleable.RangeSlideView_rsd_progress_background_color, Color.parseColor("#DDDDDD"));
            mProgressForegroundColor = typedArray.getColor(R.styleable.RangeSlideView_rsd_progress_foreground_color, Color.parseColor("#1F90E6"));
            
            // region
            if (typedArray.hasValue(R.styleable.RangeSlideView_rsd_region_padding)) {
                mRegionPadding = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlideView_rsd_region_padding, Utils.dp2px(mContext, 10))).intValue();
            }
            mDrawableRegionMin = typedArray.getDrawable(R.styleable.RangeSlideView_rsd_region_drawable_min);
            mDrawableRegionMax = typedArray.getDrawable(R.styleable.RangeSlideView_rsd_region_drawable_max);
            mRegionBitmapSize = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlideView_rsd_region_bitmap_size, Utils.dp2px(mContext, 20))).intValue();
            mRegionTextColor = typedArray.getColor(R.styleable.RangeSlideView_rsd_region_text_color, Color.BLACK);
            mRegionTextSize = Double.valueOf(typedArray.getDimension(R.styleable.RangeSlideView_rsd_region_text_size, Utils.sp2px(mContext, 14))).intValue();
            
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
        mLeftSlider.setCurPercent(Math.abs(leftValue - mProgressMin) / range);
        if (mRightSlider != null) {
            mRightSlider.setCurPercent(Math.abs(rightValue - mProgressMin) / range);
        }
        
        if (callback != null) {
            callback.onSliderChanged(this, leftValue, rightValue, false);
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
        if (null == mRightSlider) {
            return;
        }
        if (mLeftSlider.getCurPercent() + reservePercent <= 1 && mLeftSlider.getCurPercent() + reservePercent > mRightSlider.getCurPercent()) {
            mRightSlider.setCurPercent(mLeftSlider.getCurPercent() + reservePercent);
        } else if (mRightSlider.getCurPercent() - reservePercent >= 0 && mRightSlider.getCurPercent() - reservePercent < mLeftSlider.getCurPercent()) {
            mLeftSlider.setCurPercent(mRightSlider.getCurPercent() - reservePercent);
        }
    }
    
    private void dealSingleModeRange() {
        if (mLeftSlider.getCurPercent() < 1 - reservePercent && 1 - reservePercent >= 0) {
            mLeftSlider.setCurPercent(1 - reservePercent);
        }
    }
    
    private void initSlider() {
        mLeftSlider = new Slide(mContext, this);
        if (mSlidderMode == SLIDDER_MODE_RANGDE) {
            mRightSlider = new Slide(mContext, this);
        }
    }
    
    private void initProgress() {
        if (null == mLeftSlider) {
            return;
        }
        // progress top
        float regionSize = null != mDrawableRegionMax ? mRegionBitmapSize : mRegionTextSize;
        int maxTop;
        if (mRightSlider == null) {
            maxTop = Double.valueOf(Math.max(mLeftSlider.getSliderSize() / 2, regionSize / 2)).intValue();
            mProgressTop = Double.valueOf(maxTop - getProgressHeight() / 2).intValue();
        } else {
            int maxSliderSize = Math.max(mLeftSlider.getSliderSize(), mRightSlider.getSliderSize());
            maxTop = Double.valueOf(Math.max(maxSliderSize / 2, regionSize / 2)).intValue();
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
        mProgressPaint.setColor(Color.parseColor("#1F90E6"));
        mProgressPaint.setStyle(Paint.Style.FILL);
    }
    
    @SuppressLint("SwitchIntDef")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        
        int sliderSize = mLeftSlider.getSliderSize();
        int regionSize = null != mDrawableRegionMax ? mRegionBitmapSize : mRegionTextSize;
        int heightNeeded = Math.max(sliderSize, regionSize);
        
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
        String minString = mProgressMax >= 1 || mProgressMin == 0 ? String.valueOf(mProgressMin) : String.valueOf(Float.valueOf(mProgressMin));
        int regionMinTextSize = mHasMin ? Double.valueOf(mRegionPaint.measureText(minString)).intValue() : 0;
        int regionMinSize = mHasMin ? Double.valueOf(null != mDrawableRegionMin ? mRegionBitmapSize : regionMinTextSize).intValue() : 0;
        mProgressLeft = getPaddingLeft() + regionMinSize + mRegionPadding + mLeftSlider.getSliderSize() / 2;
        
        // progress right
        String maxString = mProgressMin >= 1 || mProgressMin == 0 ? String.valueOf(mProgressMax) : String.valueOf(Float.valueOf(mProgressMax));
        int regionMaxTextSize = mHasMax ? Double.valueOf(mRegionPaint.measureText(maxString)).intValue() : 0;
        int regionMaxSize = mHasMax ? Double.valueOf(null != mDrawableRegionMax ? mRegionBitmapSize : regionMaxTextSize).intValue() : 0;
        int rightSliderSize = mLeftSlider.getSliderSize();
        mProgressRight = w - getPaddingRight() - regionMaxSize - mRegionPadding - rightSliderSize / 2;
        
        // progress rectF
        mProgressBackgroundRectF.set(getProgressLeft(), getProgressTop(), getProgressRight(), getProgressBottom());
        
        // progress width
        mProgressWidth = getProgressRight() - getProgressLeft();
        
        // seekbar
        int cx = getProgressLeft();
        int cy = Double.valueOf(getProgressBottom() - getProgressHeight() / 2).intValue();
        mLeftSlider.onSizeChanged(cx, cy, Double.valueOf(mProgressWidth).intValue());
        if (null != mRightSlider) {
            mRightSlider.onSizeChanged(cx, cy, Double.valueOf(mProgressWidth).intValue());
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
        String min = String.valueOf(mLeftSlider.getCurPercent() * (mProgressMax - mProgressMin));
        BigDecimal bigDecimal = new BigDecimal((double) Float.parseFloat(min));
        bigDecimal = bigDecimal.setScale(1, 4);
        float minFloat = bigDecimal.floatValue();
        if (minFloat >= 1 || minFloat == 0) {
            int minInt = Double.valueOf(String.valueOf(minFloat)).intValue();
            min = String.valueOf(minInt);
        } else {
            min = String.valueOf(minFloat);
        }
        
        float x = getProgressLeft() - mRegionPadding - mLeftSlider.getSliderSize() / 2 - mRegionPaint.measureText(min);
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
        String max = String.valueOf(null == mRightSlider ? mProgressMax : (mProgressMax - mProgressMin) * mRightSlider.getCurPercent());
        BigDecimal bigDecimal = new BigDecimal((double) Float.parseFloat(max));
        bigDecimal = bigDecimal.setScale(2, 4);
        float maxFloat = bigDecimal.floatValue();
        if (maxFloat >= 1 || maxFloat == 0) {
            int minInt = Double.valueOf(String.valueOf(maxFloat)).intValue();
            max = String.valueOf(minInt);
        } else {
            max = String.valueOf(maxFloat);
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
        float sliderSize = calSliderSize(mLeftSlider.getCurPercent());
        rectF.left = left;
        rectF.top = getProgressTop();
        rectF.right = left + sliderSize;
        rectF.bottom = getProgressBottom();
        return rectF;
    }
    
    private RectF getRangeForegroundRectF() {
        if (null == mRightSlider) {
            return null;
        }
        RectF rectF = new RectF();
        float left = calProgressLeft();
        float leftSliderSize = calSliderSize(mLeftSlider.getCurPercent());
        float rightSliderSize = calSliderSize(mRightSlider.getCurPercent());
        rectF.left = left + leftSliderSize;
        rectF.top = getProgressTop();
        rectF.right = left + rightSliderSize;
        rectF.bottom = getProgressBottom();
        return rectF;
    }
    
    private float calProgressLeft() {
        return mLeftSlider.getSliderLeft() + mLeftSlider.getSliderSize() / 2;
    }
    
    private float calSliderSize(float curPercent) {
        return mProgressWidth * curPercent;
    }
    
    private void drawLeftSB(Canvas canvas) {
        mLeftSlider.draw(canvas);
    }
    
    private void drawRightSB(Canvas canvas) {
        if (mRightSlider != null) {
            mRightSlider.draw(canvas);
        }
    }
    
    protected float getEventX(MotionEvent event) {
        return event.getX();
    }
    
    protected float getEventY(MotionEvent event) {
        return event.getY();
    }
    
    private Slide getCurrTouchSB(MotionEvent event) {
        Slide currTouchSB = null;
        if (isTouching(mLeftSlider, event)) {
            currTouchSB = mLeftSlider;
        } else if (isTouching(mRightSlider, event)) {
            currTouchSB = mRightSlider;
        }
        return currTouchSB;
    }
    
    private boolean isTouching(Slide seekBar, MotionEvent event) {
        if (null == seekBar || null == event) {
            return false;
        }
        return seekBar.isSliding(getEventX(event), getEventY(event));
    }
    
    private Slide exchangeCurTouch(float x, float touchDownX) {
        Slide seekBar;
        if (x - touchDownX > 0) {
            seekBar = mRightSlider;
        } else {
            seekBar = mLeftSlider;
        }
        return seekBar;
    }
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mCurDownX = getEventX(event);
                boolean result = false;
                switch (mSlidderMode) {
                    case SLIDDER_MODE_RANGDE: {
                        mCurSlider = getCurrTouchSB(event);
                        result = true;
                        break;
                    }
                    case SLIDDER_MODE_SINGLE:
                    default: {
                        if (isTouching(mLeftSlider, event)) {
                            mCurSlider = mLeftSlider;
                            result = true;
                        }
                        break;
                    }
                }
                if (callback != null) {
                    callback.onStartTrackingTouch(this, mCurSlider == mLeftSlider);
                }
                return result;
            }
            case MotionEvent.ACTION_MOVE:
                float percent;
                float x = getEventX(event);
                if (mSlidderMode == SLIDDER_MODE_RANGDE) {
                    // 重合
                    if (mLeftSlider.getCurPercent() >= mRightSlider.getCurPercent()) {
                        if (callback != null) {
                            callback.onStopTrackingTouch(this, mCurSlider == mLeftSlider);
                        }
                        mCurSlider = exchangeCurTouch(x, mCurDownX);
                        if (callback != null) {
                            callback.onStartTrackingTouch(this, mCurSlider == mLeftSlider);
                        }
                    }
                    // right
                    if (mCurSlider == mRightSlider) {
                        percent = x > getProgressRight() ? 1 : (x - getProgressLeft()) * 1f / (mProgressWidth);
                        mRightSlider.slide(percent);
                    }
                }
                mCurDownX = x;
                // left
                if (mCurSlider == mLeftSlider) {
                    percent = x < getProgressLeft() ? 0 : (x - getProgressLeft()) * 1f / (mProgressWidth);
                    mLeftSlider.slide(percent);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (callback != null) {
                    callback.onStopTrackingTouch(this, mCurSlider == mLeftSlider);
                }
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
    public void setSliderMode(int mode) {
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
