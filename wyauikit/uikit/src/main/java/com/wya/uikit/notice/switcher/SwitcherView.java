package com.wya.uikit.notice.switcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
    private AutoSwitcherTask mAutoPlayTask;
    private SwitcherViewListener mListener;
    private boolean mIsTaskLive;
    private int mIndex;
    private boolean mClosable;
    private boolean mSkipable;
    
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
                mClosable = typedArray.getBoolean(R.styleable.SwitcherView_switch_closable, false);
                mSkipable = typedArray.getBoolean(R.styleable.SwitcherView_switch_skipable, false);
                mSwitchDuration = typedArray.getInteger(R.styleable.SwitcherView_switch_duration, DEF_SWITCH_DURATION);
                typedArray.recycle();
            }
        }
    }
    
    private void init() {
        mAutoPlayTask = new AutoSwitcherTask(this);
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
        super.onAttachedToWindow();
        if (mIndex > 1) {
            startAutoSwitcher();
        }
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pauseSwitcher();
    }
    
    public void startSwitcher() {
        if (null == mAutoPlayTask) {
            mAutoPlayTask = new AutoSwitcherTask(this);
        }
        mIsTaskLive = true;
        startAutoSwitcher();
    }
    
    private void startAutoSwitcher() {
        removeCallbacks(mAutoPlayTask);
        postDelayed(mAutoPlayTask, mSwitchDuration);
    }
    
    public void pauseSwitcher() {
        mIsTaskLive = false;
        if (null != mAutoPlayTask) {
            removeCallbacks(mAutoPlayTask);
        }
    }
    
    public void resetSwitcher() {
        mIsTaskLive = false;
        if (null != mAutoPlayTask) {
            removeCallbacks(mAutoPlayTask);
        }
        if (mListener != null) {
            mListener.onSwitch(this.getCurrentView(), mIndex = 0);
        }
    }
    
    private class AutoSwitcherTask implements Runnable {
        private final WeakReference<SwitcherView> mSwitcher;
        
        private AutoSwitcherTask(SwitcherView switcher) {
            mSwitcher = new WeakReference<>(switcher);
        }
        
        @Override
        public void run() {
            if (mIsTaskLive) {
                SwitcherView switcher = mSwitcher.get();
                if (switcher != null) {
                    switcher.switchToNextView();
                    switcher.startAutoSwitcher();
                }
            }
        }
    }
    
    public void switchToNextView() {
        if (null != mListener && null != getNextView()) {
            mListener.onSwitch(this.getNextView(), mIndex);
            this.showNext();
            mIndex++;
        }
    }
    
    public int getCurIndex() {
        return mIndex;
    }
    
    public void setAnimDirection(int direction) {
        this.mAnimDirection = direction;
    }
    
    public void setSwitchDuration(int duration) {
        this.mAnimDuration = duration;
    }
    
    public interface SwitcherViewListener {
        
        void onSwitch(View view, int index);
        
    }
    
    public void setSwitcheNextViewListener(SwitcherViewListener listener) {
        this.mListener = listener;
    }
    
    public void setClosable(boolean closable) {
        mClosable = closable;
    }
    
    public boolean isClosable() {
        return mClosable;
    }
    
    public void setSkipable(boolean skipable) {
        mSkipable = skipable;
    }
    
    public boolean isSkipable() {
        return mSkipable;
    }
}
