package com.weiyian.android.xpopup.animator;

import android.view.View;

import com.weiyian.android.xpopup.enums.PopupAnimation;

/**
 * Description: 弹窗动画执行器
 * Create by dance, at 2018/12/9
 *
 * @author :
 */
public abstract class BasePopupAnimator {
    
    public View targetView;
    public int animateDuration = 400;
    
    public PopupAnimation popupAnimation;
    
    public BasePopupAnimator() {
    }
    
    public BasePopupAnimator(View target) {
        this(target, null);
    }
    
    public BasePopupAnimator(View target, PopupAnimation popupAnimation) {
        this.targetView = target;
        this.popupAnimation = popupAnimation;
    }
    
    /**
     * initAnimator
     */
    public abstract void initAnimator();
    
    /**
     * animateShow
     */
    public abstract void animateShow();
    
    /**
     * animateDismiss
     */
    public abstract void animateDismiss();
    
}
