package com.wya.hardware.camera.listener;

import android.graphics.Bitmap;

 /**
  * @date: 2018/12/5 13:37
  * @author: Chunjiang Mao
  * @classname: WYACameraListener
  * @describe:
  */

public interface WYACameraListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame);

}
