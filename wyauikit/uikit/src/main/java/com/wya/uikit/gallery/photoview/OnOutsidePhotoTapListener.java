package com.wya.uikit.gallery.photoview;

import android.widget.ImageView;

/**
 * BaseCallback when the user tapped outside of the photo
 */
public interface OnOutsidePhotoTapListener {

    /**
     * The outside of the photo has been tapped
     * @param imageView
     */
    void onOutsidePhotoTap(ImageView imageView);
}
