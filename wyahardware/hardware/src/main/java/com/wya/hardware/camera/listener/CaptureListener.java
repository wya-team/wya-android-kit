package com.wya.hardware.camera.listener;
 /**
  * 创建日期：2018/12/5 13:35
  * 作者： Mao Chunjiang
  * 文件名称：CaptureListener
  * 类说明：
  */

public interface CaptureListener {
    void takePictures();

    void recordShort(long time);

    void recordStart();

    void recordEnd(long time);

    void recordZoom(float zoom);

    void recordError();
}
