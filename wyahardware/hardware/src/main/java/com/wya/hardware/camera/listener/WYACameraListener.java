package com.wya.hardware.camera.listener;

import android.graphics.Bitmap;

 /**
  * 创建日期：2018/12/5 13:37
  * 作者： Mao Chunjiang
  * 文件名称：WYACameraListener
  * 类说明：
  */

public interface WYACameraListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame);

}
