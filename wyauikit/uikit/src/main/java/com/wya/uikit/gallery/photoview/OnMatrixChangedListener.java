package com.wya.uikit.gallery.photoview;

import android.graphics.RectF;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public interface OnMatrixChangedListener {
    
    /**
     * BaseCallback for when the Matrix displaying the Drawable has changed. This could be because
     * the View's bounds have changed, or the user has zoomed.
     *
     * @param rect - Rectangle displaying the Drawable's new bounds.
     */
    void onMatrixChanged(RectF rect);
}
