package com.wya.uikit.gallery.photoview;


/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10
 *  @description     :
 */
public interface OnScaleChangedListener {

    /**
     * BaseCallback for when the scale changes
     *
     * @param scaleFactor the scale factor (less than 1 for zoom out, greater than 1 for zoom in)
     * @param focusX      focal point X position
     * @param focusY      focal point Y position
     */
    void onScaleChange(float scaleFactor, float focusX, float focusY);
}
