package com.wya.hardware.upload;

/**
 * @author :
 */
public interface ProgressListener {
    
    /**
     * 进度
     *
     * @param progress :
     */
    void onProgress(double progress);
}
