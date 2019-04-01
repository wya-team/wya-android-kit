package com.wya.uikit.notice;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.Toast;

import com.wya.uikit.R;

/**
 * @author :
 */
public class WYAMarqueeTextView extends AppCompatTextView {
    
    public static final int MARQUEE_MODE_REPEAT = 0;
    public static final int MARQUEE_MODE_ONCE = 1;
    private static final int MARQUEE_DEFAULT_INTERVAL = 5000;
    private static final int MARQUEE_DEFAULT_DURATION = 5000;
    private Scroller mScroller;
    
    private int mCurStartX = 0;
    private int mMarqueeInterval;
    private int mDuration;
    private int mMarqueeMode;
    private boolean mClosable;
    private boolean mSkipable;
    
    private boolean mIsPaused = true;
    private boolean mIsAutoStart = true;
    private Context mContext;
    
    public WYAMarqueeTextView(Context context) {
        this(context, null);
    }
    
    public WYAMarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public WYAMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        parseAttrs(context, attrs, R.style.style_marquee_global_option, R.style.style_marquee_global_option);
        init();
    }
    
    private void parseAttrs(Context context, AttributeSet attrs, int defStyleAtts, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WYAMarqueeTextView, defStyleAtts, defStyleRes);
        if (null != typedArray) {
            mIsAutoStart = typedArray.getBoolean(R.styleable.WYAMarqueeTextView_marqueeAutoStart, false);
            mMarqueeInterval = typedArray.getInt(R.styleable.WYAMarqueeTextView_marqueeInterval, MARQUEE_DEFAULT_INTERVAL);
            mDuration = typedArray.getInt(R.styleable.WYAMarqueeTextView_marqueeDuration, MARQUEE_DEFAULT_DURATION);
            mMarqueeMode = typedArray.getInt(R.styleable.WYAMarqueeTextView_marqueeMode, MARQUEE_MODE_REPEAT);
            mClosable = typedArray.getBoolean(R.styleable.WYAMarqueeTextView_marqueeClosable, false);
            mSkipable = typedArray.getBoolean(R.styleable.WYAMarqueeTextView_marqueeSkipable, false);
            typedArray.recycle();
        }
    }
    
    private void init() {
        setSingleLine();
        setEllipsize(null);
        setHorizontallyScrolling(true);
        if (mIsAutoStart) {
            startMarquee();
        }
        
        setOnClickListener(view -> {
            if (isClosable()) {
                pauseMarquee();
                setVisibility(GONE);
            } else if (isSkipable()) {
                Toast.makeText(mContext, "click ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void startMarquee() {
        mCurStartX = 0;
        mIsPaused = true;
        resumeMarquee();
    }
    
    public void resumeMarquee() {
        if (!mIsPaused) {
            return;
        }
        mIsPaused = false;
        if (mScroller == null) {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            setScroller(mScroller);
        }
        int mesureText = measureText();
        final int distance = mesureText - mCurStartX;
        mScroller.startScroll(mCurStartX, 0, distance, 0, mDuration);
        invalidate();
    }
    
    private int measureText() {
        TextPaint paint = getPaint();
        float size = paint.measureText(getText().toString());
        return Double.valueOf(size).intValue();
    }
    
    /**
     * invoke ViewGroup.dispatchDraw() -> ViewGroup.drawChild() -> View.draw(Canvas,ViewGroup,long)
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null == mScroller || mIsPaused) {
            return;
        }
        switch (mMarqueeMode) {
            case MARQUEE_MODE_ONCE:
                if (mScroller.isFinished()) {
                    resetMarquee();
                }
                break;
            case MARQUEE_MODE_REPEAT:
                if (mScroller.isFinished() && (!mIsPaused)) {
                    mIsPaused = true;
                    mCurStartX = -1 * measureText();
                    resumeMarquee();
                }
                break;
            default:
                break;
        }
    }
    
    public void pauseMarquee() {
        if (null == mScroller || mIsPaused) {
            return;
        }
        mIsPaused = true;
        mCurStartX = mScroller.getCurrX();
        mScroller.abortAnimation();
    }
    
    public void resetMarquee() {
        if (null == mScroller) {
            return;
        }
        mIsPaused = true;
        mScroller.startScroll(0, 0, 0, 0, 0);
    }
    
    //    @Override
    //    protected void onAttachedToWindow() {
    //        super.onAttachedToWindow();
    //        if (mIsAutoStart) {
    //            resumeMarquee();
    //        }
    //    }
    //
    //    @Override
    //    protected void onDetachedFromWindow() {
    //        super.onDetachedFromWindow();
    //        if (mIsAutoStart) {
    //            pauseMarquee();
    //        }
    //    }
    
    public void setMarqueeInterval(int interval) {
        mMarqueeInterval = interval;
    }
    
    public void setMarqueeMode(int mode) {
        mMarqueeMode = mode;
    }
    
    public boolean isPaused() {
        return mIsPaused;
    }
    
    public boolean isClosable() {
        return mClosable;
    }
    
    public void setClosable(boolean closable) {
        mClosable = closable;
    }
    
    public boolean isSkipable() {
        return mSkipable;
    }
    
    public void setSkipable(boolean clickable) {
        mSkipable = clickable;
    }
    
}