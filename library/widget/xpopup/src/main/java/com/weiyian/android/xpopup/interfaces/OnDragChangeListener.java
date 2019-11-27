package com.weiyian.android.xpopup.interfaces;

public interface OnDragChangeListener {
    
    /**
     * onRelease
     */
    void onRelease();
    
    /**
     * onDragChange
     *
     * @param dy       :
     * @param scale    :
     * @param fraction :
     */
    void onDragChange(int dy, float scale, float fraction);
}
