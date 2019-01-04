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

    void start(SurfaceHolder holder, float screenProp);

    void stop();

    void focus(float x, float y, CameraInterface.FocusCallback callback);

    void mySwitch(SurfaceHolder holder, float screenProp);

    void restart();

    void capture();

    void record(Surface surface, float screenProp);

    void stopRecord(boolean isShort, long time);

    void cancel(SurfaceHolder holder, float screenProp);

    void confirm();

    void zoom(float zoom, int type);

    void flash(String mode);
}
