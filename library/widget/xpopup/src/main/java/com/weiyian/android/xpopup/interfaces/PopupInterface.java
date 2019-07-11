package com.weiyian.android.xpopup.interfaces;

import android.view.View;

/**
 * Description:
 * Create by lxj, at 2018/12/7
 */
public interface PopupInterface {
    
    /**
     * getPopupContentView
     *
     * @return :
     */
    View getPopupContentView();
    
    /**
     * getAnimationDuration
     *
     * @return :
     */
    int getAnimationDuration();
    
    /**
     * doShowAnimation
     */
    void doShowAnimation();
    
    /**
     * doDismissAnimation
     */
    void doDismissAnimation();
    
    /**
     * init
     *
     * @param afterAnimationStarted :
     * @param afterAnimationEnd     :
     */
    void init(final Runnable afterAnimationStarted, Runnable afterAnimationEnd);
}
