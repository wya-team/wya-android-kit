package com.wya.hardware.camera.listener;

/**
 * @date: 2018/12/5 13:35
 * @author: Chunjiang Mao
 * @classname: CaptureListener
 * @describe:
 */

public interface CaptureListener {
    /**
     * takePictures
     */
    void takePictures();
    
    /**
     * recordShort
     *
     * @param time
     */
    void recordShort(long time);
    
    /**
     * recordStart
     */
    void recordStart();
    
    /**
     * recordEnd
     *
     * @param time
     */
    void recordEnd(long time);
    
    /**
     * recordZoom
     *
     * @param zoom
     */
    void recordZoom(float zoom);
    
    /**
     * recordError
     */
    void recordError();
}
