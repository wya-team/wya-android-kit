package com.wya.uikit.gallery.photoview;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10
 *  @description     :
 */
public interface OnViewDragListener {

    /**
     * BaseCallback for when the photo is experiencing a drag event. This cannot be invoked when the
     * user is scaling.
     *
     * @param dx The change of the coordinates in the x-direction
     * @param dy The change of the coordinates in the y-direction
     */
    void onDrag(float dx, float dy);
}
