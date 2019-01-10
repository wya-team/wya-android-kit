package com.wya.hardware.camera.state;

import android.view.Surface;
import android.view.SurfaceHolder;

import com.wya.hardware.camera.CameraInterface;

/**
 * @date: 2018/12/5 13:52
 * @author: Chunjiang Mao
 * @classname: State
 * @describe:
 */

public interface State {
    
    /**
     * start
     *
     * @param holder
     * @param screenProp
     */
    void start(SurfaceHolder holder, float screenProp);
    
    /**
     * stop
     */
    void stop();
    
    /**
     * focus
     *
     * @param x
     * @param y
     * @param callback
     */
    void focus(float x, float y, CameraInterface.FocusCallback callback);
    
    /**
     * mySwitch
     *
     * @param holder
     * @param screenProp
     */
    void mySwitch(SurfaceHolder holder, float screenProp);
    
    /**
     * restart
     */
    void restart();
    
    /**
     * capture
     */
    void capture();
    
    /**
     * record
     *
     * @param surface
     * @param screenProp
     */
    void record(Surface surface, float screenProp);
    
    /**
     * stopRecord
     *
     * @param isShort
     * @param time
     */
    void stopRecord(boolean isShort, long time);
    
    /**
     * cancel
     *
     * @param holder
     * @param screenProp
     */
    void cancel(SurfaceHolder holder, float screenProp);
    
    /**
     * confirm
     */
    void confirm();
    
    /**
     * zoom
     *
     * @param zoom
     * @param type
     */
    void zoom(float zoom, int type);
    
    /**
     * flash
     *
     * @param mode
     */
    void flash(String mode);
}
