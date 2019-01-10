package com.wya.hardware.camera.state;

import android.view.Surface;
import android.view.SurfaceHolder;

import com.wya.hardware.camera.CameraInterface;
import com.wya.hardware.camera.WYACameraView;

/**
 * @date: 2018/12/5 13:39
 * @author: Chunjiang Mao
 * @classname: BorrowPictureState
 * @describe:
 */

public class BorrowPictureState implements State {
    private final String TAG = "BorrowPictureState";
    private CameraMachine machine;
    
    public BorrowPictureState(CameraMachine machine) {
        this.machine = machine;
    }
    
    @Override
    public void start(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().doStartPreview(holder, screenProp);
        machine.setState(machine.getPreviewState());
    }
    
    @Override
    public void stop() {
    
    }
    
    @Override
    public void focus(float x, float y, CameraInterface.FocusCallback callback) {
    }
    
    @Override
    public void mySwitch(SurfaceHolder holder, float screenProp) {
    
    }
    
    @Override
    public void restart() {
    
    }
    
    @Override
    public void capture() {
    
    }
    
    @Override
    public void record(Surface surface, float screenProp) {
    
    }
    
    @Override
    public void stopRecord(boolean isShort, long time) {
    }
    
    @Override
    public void cancel(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().doStartPreview(holder, screenProp);
        machine.getView().resetState(WYACameraView.TYPE_PICTURE);
        machine.setState(machine.getPreviewState());
    }
    
    @Override
    public void confirm() {
        machine.getView().confirmState(WYACameraView.TYPE_PICTURE);
        machine.setState(machine.getPreviewState());
    }
    
    @Override
    public void zoom(float zoom, int type) {
    }
    
    @Override
    public void flash(String mode) {
    
    }
    
}
