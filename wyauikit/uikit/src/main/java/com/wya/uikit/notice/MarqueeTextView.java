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

public class MarqueeTextView extends AppCompatTextView {
    
    private static final int MARQUEE_DEFAULT_INTERVAL = 5000;
    public static final int MARQUEE_MODE_REPEAT = 0;
    public static final int MARQUEE_MODE_ONCE = 1;
    
    private Scroller mScroller;
    
    private int mCurStartX = 0;
    private int mMarqueeInterval;
    private int mMarqueeMode;
    private boolean mClosable;
    private boolean mSkipable;
    
    private boolean mIsPaused = true;
    private Context mContext;
    
    public MarqueeTextView(Context context) {
        this(context, null);
    }
    
    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        parseAttrs(context, attrs);
        init();
    }
    
    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        if (null != typedArray) {
            mMarqueeInterval = typedArray.getInt(R.styleable.MarqueeTextView_mtv_interval, MARQUEE_DEFAULT_INTERVAL);
            mMarqueeMode = typedArray.getInt(R.styleable.MarqueeTextView_mtv_mode, MARQUEE_MODE_REPEAT);
            mClosable = typedArray.getBoolean(R.styleable.MarqueeTextView_mtv_closable, false);
            mSkipable = typedArray.getBoolean(R.styleable.MarqueeTextView_mtv_skipable, false);
            typedArray.recycle();
        }
    }
    
    private void init() {
        setSingleLine();
        setEllipsize(null);
        setHorizontallyScrolling(true);
        startMarquee();
        
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
            mScroller = new Scroller(this.getContext(), new LinearInterpolator());
            setScroller(mScroller);
        }
        int mesureText = measureText();
        final int distance = mesureText - mCurStartX;
        final int duration = (Double.valueOf(mMarqueeInterval * distance / mesureText)).intValue();
        mScroller.startScroll(mCurStartX, 0, distance, 0, duration);
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
    
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        resumeMarquee();
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pauseMarquee();
    }
    
    public void setMarqueeInterval(int interval) {
        this.mMarqueeInterval = interval;
    }
    
    public void setMarqueeMode(int mode) {
        this.mMarqueeMode = mode;
    }
    
    public boolean isPaused() {
        return mIsPaused;
    }
    
    public boolean isClosable() {
        return mClosable;
    }
    
    public void setClosable(boolean closable) {
        this.mClosable = closable;
    }
    
    public boolean isSkipable() {
        return mSkipable;
    }
    
    public void setSkipable(boolean clickable) {
        mSkipable = clickable;
    }
    
}