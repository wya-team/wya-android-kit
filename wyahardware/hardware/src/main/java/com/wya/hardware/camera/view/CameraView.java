package com.wya.hardware.camera.view;

import android.graphics.Bitmap;

/**
 * @date: 2018/12/5 14:09
 * @author: Chunjiang Mao
 * @classname: CameraView
 * @describe:
 */

public interface CameraView {
    /**
     * resetState
     *
     * @param type
     */
    void resetState(int type);
    
    /**
     * confirmState
     *
     * @param type
     */
    void confirmState(int type);
    
    /**
     * showPicture
     *
     * @param bitmap
     * @param isVertical
     */
    void showPicture(Bitmap bitmap, boolean isVertical);
    
    /**
     * playVideo
     *
     * @param firstFrame
     * @param url
     */
    void playVideo(Bitmap firstFrame, String url);
    
    /**
     * stopVideo
     */
    void stopVideo();
    
    /**
     * setTip
     *
     * @param tip
     */
    void setTip(String tip);
    
    /**
     * startPreviewCallback
     */
    void startPreviewCallback();
    
    /**
     * handlerFocus
     *
     * @param x
     * @param y
     * @return
     */
    boolean handlerFocus(float x, float y);
}
