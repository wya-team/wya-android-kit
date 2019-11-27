package com.weiyian.android.xpopup.animator;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

import com.weiyian.android.xpopup.enums.PopupAnimation;
import com.weiyian.android.xpopup.util.XPopupUtils;

/**
 * Description: 平移动画
 * Create by dance, at 2018/12/9
 *
 * @author :
 */
public class TranslateAlphaAnimator extends BasePopupAnimator {
    
    // 动画起始坐标
    private float startTranslationX, startTranslationY;
    private float defTranslationX, defTranslationY;
    
    public TranslateAlphaAnimator(View target, PopupAnimation popupAnimation) {
        super(target, popupAnimation);
    }
    
    @Override
    public void initAnimator() {
        defTranslationX = targetView.getTranslationX();
        defTranslationY = targetView.getTranslationY();
        
        targetView.setAlpha(0);
        // 设置移动坐标
        applyTranslation();
        startTranslationX = targetView.getTranslationX();
        startTranslationY = targetView.getTranslationY();
    }
    
    private void applyTranslation() {
        int halfWidthOffset = XPopupUtils.getWindowWidth(targetView.getContext()) / 2 - targetView.getMeasuredWidth() / 2;
        int halfHeightOffset = XPopupUtils.getWindowHeight(targetView.getContext()) / 2 - targetView.getMeasuredHeight() / 2;
        switch (popupAnimation) {
            case TranslateAlphaFromLeft:
                /* + halfWidthOffset*/
                targetView.setTranslationX(-(targetView.getMeasuredWidth()));
                break;
            case TranslateAlphaFromTop:
                /*+ halfHeightOffset*/
                targetView.setTranslationY(-(targetView.getMeasuredHeight()));
                break;
            case TranslateAlphaFromRight:
                /*+ halfWidthOffset*/
                targetView.setTranslationX(targetView.getMeasuredWidth());
                break;
            case TranslateAlphaFromBottom:
                /*+ halfHeightOffset*/
                targetView.setTranslationY(targetView.getMeasuredHeight());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void animateShow() {
        targetView.animate().translationX(defTranslationX).translationY(defTranslationY).alpha(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(animateDuration).start();
    }
    
    @Override
    public void animateDismiss() {
        targetView.animate().translationX(startTranslationX).translationY(startTranslationY).alpha(0f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(animateDuration).start();
    }
}
