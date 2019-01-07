package com.wya.uikit.slidder;

public interface OnSlidderViewChangedListener {
    
    /**
     * 进度改变
     *
     * @param view        :
     * @param progressMin :
     * @param progressMax :
     * @param isFromUser  :
     */
    void onProgressChanged(WYASlidderView view, float progressMin, float progressMax, boolean isFromUser);
    
    /**
     * 开始滑动
     *
     * @param view  :
     * @param isMin :
     */
    void onStartTrackingTouch(WYASlidderView view, boolean isMin);
    
    /**
     * 停止滑动
     *
     * @param view  :
     * @param isMin :
     */
    void onStopTrackingTouch(WYASlidderView view, boolean isMin);
}
