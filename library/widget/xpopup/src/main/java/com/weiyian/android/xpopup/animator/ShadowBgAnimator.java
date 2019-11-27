package com.weiyian.android.xpopup.animator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;

/**
 * Description: 背景Shadow动画器，负责执行半透明的渐入渐出动画
 * Create by dance, at 2018/12/9
 *
 * @author :
 */
public class ShadowBgAnimator extends BasePopupAnimator {
    
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private int startColor = Color.TRANSPARENT;
    private int endBgColor = Color.parseColor("#77000000");
    
    public ShadowBgAnimator(View target) {
        super(target);
    }
    
    public ShadowBgAnimator() {
    }
    
    @Override
    public void initAnimator() {
        targetView.setBackgroundColor(startColor);
    }
    
    @Override
    public void animateShow() {
        ValueAnimator animator = ValueAnimator.ofObject(argbEvaluator, startColor, endBgColor);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                targetView.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        animator.setDuration(animateDuration).start();
    }
    
    @Override
    public void animateDismiss() {
        ValueAnimator animator = ValueAnimator.ofObject(argbEvaluator, endBgColor, startColor);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                targetView.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        animator.setDuration(animateDuration).start();
    }
    
    public int calculateBgColor(float fraction) {
        return (int) argbEvaluator.evaluate(fraction, startColor, endBgColor);
    }
    
}
