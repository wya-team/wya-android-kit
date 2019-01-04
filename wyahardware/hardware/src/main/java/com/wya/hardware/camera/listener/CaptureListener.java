package com.wya.hardware.camera.listener;
 /**
  * @date: 2018/12/5 13:35
  * @author: Chunjiang Mao
  * @classname: CaptureListener
  * @describe:
  */

public interface CaptureListener {
    void takePictures();

    void recordShort(long time);

    void recordStart();

    void recordEnd(long time);

    void recordZoom(float zoom);

    void recordError();
}
