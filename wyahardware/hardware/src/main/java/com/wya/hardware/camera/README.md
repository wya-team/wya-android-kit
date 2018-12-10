# Camera
## 功能说明
- 实现相机的拍照、录制视频功能

## 属性说明
属性 | 说明 | 类型 | 默认值
---|---|---|---
iconSize|图片大小|int|-
duration|拍视频默认最大时间|int|10000（ms）
iconMargin|图片边距|int|-
iconLeft|左图片资源|int|-
iconSrc|右上角图片资源|int|-

## 方法说明
方法|说明
---|---
setSaveVideoPath(String path)|设置视频保存路径
setFeatures(int state)|设置只有拍照、只有录像、拍照和录像
setTip(String tip)|设置拍照按钮上面的文字
setMediaQuality(int quality)|设置录制质量
setDuration(int duration)|设置最长录制时间
setErrorListener(ErrorListener errorListener)|启动Camera错误回调
setWyaCameraListener(WYACameraListener wyaCameraListener)|拍照和录制的回调监听

## 用法说明
- 布局引用
       
       <com.wya.hardware.camera.WYACameraView
                   android:id="@+id/wya_camera_view"
                   android:layout_width="match_parent"
                   android:layout_height="match_parent"
                   app:duration_max="10000"
                   app:iconLeft="@drawable/wya_camera_ic_back"
                   app:iconMargin="20dp"
                   app:iconSize="30dp"
                   app:iconSrc="@drawable/wya_camera_ic_camera" />

- 使用说明
方法|说明
---|---
setSaveVideoPath(String path)|设置视频保存路径
setFeatures(int state)|设置只有拍照、只有录像、拍照和录像
setTip(String tip)|设置拍照按钮上面的文字
setMediaQuality(int quality)|设置录制质量
setDuration(int duration)|设置最长录制时间
setErrorListener(ErrorListener errorListener)|启动Camera错误回调
setWyaCameraListener(WYACameraListener wyaCameraListener)|拍照和录制的回调监听
