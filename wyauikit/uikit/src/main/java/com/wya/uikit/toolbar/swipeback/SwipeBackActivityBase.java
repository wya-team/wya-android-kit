package com.wya.uikit.toolbar.swipeback;

/**
 * @author Yrom
 */
public interface SwipeBackActivityBase {
    /**
     * the SwipeBackLayout associated with this activity.
     * @return
     */
    public abstract SwipeBackLayout getSwipeBackLayout();
    
    /**
     * setSwipeBackEnable
     * @param enable
     */
    public abstract void setSwipeBackEnable(boolean enable);
    
    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
    
}
