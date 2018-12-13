package com.wya.uikit.notice.switcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewSwitcher;

import com.wya.uikit.R;

import java.lang.ref.WeakReference;

public class SwitcherView extends ViewSwitcher {
    
    public static final int DEF_ANIMATOR_DIRENCTION = SwitcherViewAnimDirection.DOWN_2_UP;
    
    private int mAnimDuration;
    private int mAnimDirection;
    private int mSwitchDuration;
    @LayoutRes
    private int mResLayout;
    private AutoPlayTask mAutoPlayTask;
    private SwitcherViewListener mListener;
    private boolean mIsTaskLive;
    private int mIndex;
    
    public SwitcherView(Context context) {
        this(context, null);
    }
    
    public SwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(context, attrs);
        init();
    }
    
    private void parseAttrs(Context context, AttributeSet attrs) {
        int DEF_ANIMATOR_DURATION = 400;
        int DEF_SWITCH_DURATION = 3_000;
        mAnimDirection = DEF_ANIMATOR_DIRENCTION;
        mAnimDuration = DEF_ANIMATOR_DURATION;
        mSwitchDuration = DEF_SWITCH_DURATION;
        
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwitcherView, 0, 0);
            if (null != typedArray) {
                mAnimDirection = typedArray.getInteger(R.styleable.SwitcherView_anim_direction, DEF_ANIMATOR_DIRENCTION);
                mAnimDuration = typedArray.getInteger(R.styleable.SwitcherView_anim_duration, DEF_ANIMATOR_DURATION);
                mSwitchDuration = typedArray.getInteger(R.styleable.SwitcherView_switch_duration, DEF_SWITCH_DURATION);
                typedArray.recycle();
            }
        }
    }
    
    private void init() {
        mAutoPlayTask = new AutoPlayTask(this);
        initAnimation();
    }
    
    public SwitcherView inflate(@LayoutRes final int resLayout) {
        if (mResLayout == 0) {
            mResLayout = resLayout;
            super.setFactory(new ViewFactory() {
                @Override
                public View makeView() {
                    return View.inflate(getContext(), mResLayout, null);
                }
            });
        }
        if (null != mListener && null != this.getCurrentView()) {
            mListener.onSwitch(getCurrentView(), mIndex = 0);
        }
        mIndex++;
        return this;
    }
    
    private void initAnimation() {
        setAnimation(mAnimDirection);
    }
    
    public SwitcherView setAnimation(@SwitcherViewAnimDirection int animDir) {
        setAnimation(animDir, null, null);
        return this;
    }
    
    public SwitcherView setAnimation(Animation animationIn, Animation animationOut) {
        setAnimation(-1, animationIn, animationOut);
        return this;
    }
    
    private void setAnimation(int animDir, @Nullable Animation animationIn, @Nullable Animation animationOut) {
        switch (animDir) {
            case SwitcherViewAnimDirection.UP_2_DOWN:
                animationIn = new TranslateAnimation(0, 0.0F, 0, 0.0F, Animation.RELATIVE_TO_SELF, -1.0F, Animation.RELATIVE_TO_SELF, 0.0F);
                animationOut = new TranslateAnimation(0, 0.0F, 0, 0.0F, Animation.RELATIVE_TO_SELF, 0.0F, Animation.RELATIVE_TO_SELF, 1.0F);
                animationIn.setInterpolator(new AccelerateDecelerateInterpolator());
                animationOut.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            
            case SwitcherViewAnimDirection.LEFT_2_RIGHT:
                animationIn = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0F, Animation.RELATIVE_TO_SELF, 0.0F, 0, 0.0F, 0, 0.0F);
                animationOut = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0F, Animation.RELATIVE_TO_SELF, 1.0F, 0, 0.0F, 0, 0.0F);
                animationIn.setInterpolator(new AccelerateDecelerateInterpolator());
                animationOut.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            
            case SwitcherViewAnimDirection.RIGHT_2_LEFT:
                animationIn = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0F, Animation.RELATIVE_TO_SELF, 0.0F, 0, 0.0F, 0, 0.0F);
                animationOut = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0F, Animation.RELATIVE_TO_SELF, -1.0F, 0, 0.0F, 0, 0.0F);
                animationIn.setInterpolator(new AccelerateDecelerateInterpolator());
                animationOut.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            
            case SwitcherViewAnimDirection.DOWN_2_UP: // def
                animationIn = new TranslateAnimation(0, 0.0F, 0, 0.0F, Animation.RELATIVE_TO_SELF, 1.0F, Animation.RELATIVE_TO_SELF, 0.0f);
                animationOut = new TranslateAnimation(0, 0.0F, 0, 0.0F, Animation.RELATIVE_TO_SELF, 0.0F, Animation.RELATIVE_TO_SELF, -1.0f);
                animationIn.setInterpolator(new AccelerateDecelerateInterpolator());
                animationOut.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            
            default:
                break;
        }
        
        if (null != animationIn && null != animationOut) {
            animationIn.setDuration(mAnimDuration);
            animationOut.setDuration(mAnimDuration);
            setInAnimation(animationIn);
            setOutAnimation(animationOut);
        }
    }
    
    @Override
    protected void onAttachedToWindow() {
        Log.e("ZCQ", "onAttachedToWindow");
        super.onAttachedToWindow();
        if (mIndex > 1) {
            startAutoTask();
        }
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("ZCQ", "onDetachedFromWindow");
        pauseAutoPlay();
    }
    
    public void startAutoPlay() {
        if (null == mAutoPlayTask) {
            mAutoPlayTask = new AutoPlayTask(this);
        }
        mIsTaskLive = true;
        startAutoTask();
    }
    
    private void startAutoTask() {
        removeCallbacks(mAutoPlayTask);
        postDelayed(mAutoPlayTask, mSwitchDuration);
    }
    
    public void resetAutoPlay() {
        mIsTaskLive = false;
        if (null != mAutoPlayTask) removeCallbacks(mAutoPlayTask);
        if (mListener != null) mListener.onSwitch(this.getCurrentView(), mIndex = 0);
    }
    
    public void pauseAutoPlay() {
        mIsTaskLive = false;
        if (null != mAutoPlayTask) {
            removeCallbacks(mAutoPlayTask);
        }
    }
    
    private class AutoPlayTask implements Runnable {
        private final WeakReference<SwitcherView> mSwitcher;
        
        private AutoPlayTask(SwitcherView switcher) {
            mSwitcher = new WeakReference<>(switcher);
        }
        
        @Override
        public void run() {
            if (mIsTaskLive) {
                SwitcherView switcher = mSwitcher.get();
                if (switcher != null) {
                    switcher.switchToNextView();
                    switcher.startAutoTask();
                }
            }
        }
    }
    
    public void switchToNextView() {
        if (null != mListener) {
            mListener.onSwitch(this.getNextView(), mIndex);
            this.showNext();
            mIndex++;
        }
    }
    
    public int getCurIndex() {
        return mIndex;
    }
    
    public interface SwitcherViewListener {
        void onSwitch(View view, int index);
    }
    
    public void setSwitcheNextViewListener(SwitcherViewListener listener) {
        this.mListener = listener;
    }
    
}
