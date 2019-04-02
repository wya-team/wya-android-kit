package com.wya.hardware.upload;

/**
 * @author :
 */
@FunctionalInterface
public interface PostBeforeInterface {
    
    /**
     * post before
     *
     * @return :
     */
    OssInfo onPostBefore();
    
}
