package com.wya.uikit.slideview;

public interface OnSlideViewChangedListener {
    void onSliderChanged(RangeSlideView view, float leftValue, float rightValue, boolean isFromUser);
    
    void onStartTrackingTouch(RangeSlideView view, boolean isLeft);
    
    void onStopTrackingTouch(RangeSlideView view, boolean isLeft);
}
