package com.wya.hardware.upload;

/**
 * @author :
 */
@FunctionalInterface
public interface PostAfterInterface<T> {
    
    /**
     * after post
     *
     * @param status :
     * @param msg    :
     * @param data   :
     */
    void onPostAfter(int status, String msg, T data);
    
}
