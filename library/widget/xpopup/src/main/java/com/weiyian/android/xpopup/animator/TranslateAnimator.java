package com.weiyian.android.xpopup.animator;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

import com.weiyian.android.xpopup.enums.PopupAnimation;

/**
 * Description: 平移动画，不带渐变
 * Create by dance, at 2018/12/9
 *
 * @author :
 */
public class TranslateAnimator extends BasePopupAnimator {
    
    private float startTranslationX, startTranslationY;
    
    public TranslateAnimator(View target, PopupAnimation popupAnimation) {
        super(target, popupAnimation);
    }
    
    @Override
    public void initAnimator() {
        // 设置移动坐标
        applyTranslation();
        startTranslationX = targetView.getTranslationX();
        startTranslationY = targetView.getTranslationY();
    }
    
    private void applyTranslation() {
        switch (popupAnimation) {
            case TranslateFromLeft:
                targetView.setTranslationX(-targetView.getRight());
                break;
            case TranslateFromTop:
                targetView.setTranslationY(-targetView.getBottom());
                break;
            case TranslateFromRight:
                targetView.setTranslationX(((View) targetView.getParent()).getMeasuredWidth() - targetView.getLeft());
                break;
            case TranslateFromBottom:
                targetView.setTranslationY(((View) targetView.getParent()).getMeasuredHeight() - targetView.getTop());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void animateShow() {
        targetView.animate().translationX(0).translationY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(animateDuration).start();
    }
    
    @Override
    public void animateDismiss() {
        targetView.animate().translationX(startTranslationX).translationY(startTranslationY)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(animateDuration).start();
    }
}
