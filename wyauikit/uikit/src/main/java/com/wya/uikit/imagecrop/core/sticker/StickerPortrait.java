package com.wya.uikit.imagecrop.core.sticker;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

public interface StickerPortrait {
    
    /**
     * show
     *
     * @return
     */
    boolean show();
    
    /**
     * remove
     *
     * @return
     */
    boolean remove();
    
    /**
     * dismiss
     *
     * @return
     */
    boolean dismiss();
    
    /**
     * isShowing
     *
     * @return
     */
    boolean isShowing();
    
    /**
     * getFrame
     *
     * @return
     */
    RectF getFrame();
    
    //    RectF getAdjustFrame();
    //
    //    RectF getDeleteFrame();
    
    /**
     * onSticker
     *
     * @param canvas
     */
    void onSticker(Canvas canvas);
    
    /**
     * registerCallback
     *
     * @param callback
     */
    void registerCallback(Sticker.Callback callback);
    
    /**
     * unregisterCallback
     *
     * @param callback
     */
    void unregisterCallback(Sticker.Callback callback);
    
    interface Callback {
        
        /**
         * onDismiss
         *
         * @param stickerView
         * @param <V>
         */
        <V extends View & Sticker> void onDismiss(V stickerView);
        
        /**
         * onShowing
         *
         * @param stickerView
         * @param <V>
         */
        <V extends View & Sticker> void onShowing(V stickerView);
        
        /**
         * onRemove
         *
         * @param stickerView
         * @param <V>
         *
         * @return
         */
        <V extends View & Sticker> boolean onRemove(V stickerView);
    }
}
