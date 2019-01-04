package com.wya.hardware.camera.view;

import android.graphics.Bitmap;

 /**
  * @date: 2018/12/5 14:09
  * @author: Chunjiang Mao
  * @classname: CameraView
  * @describe:
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
