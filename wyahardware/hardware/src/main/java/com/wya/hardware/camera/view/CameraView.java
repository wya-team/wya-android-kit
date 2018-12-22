package com.wya.hardware.camera.view;

import android.graphics.Bitmap;

 /**
  * 创建日期：2018/12/5 14:09
  * 作者： Mao Chunjiang
  * 文件名称：CameraView
  * 类说明：
  */

public interface CameraView {
    void resetState(int type);

    void confirmState(int type);

    void showPicture(Bitmap bitmap, boolean isVertical);

    void playVideo(Bitmap firstFrame, String url);

    void stopVideo();

    void setTip(String tip);

    void startPreviewCallback();

    boolean handlerFocus(float x, float y);
}
