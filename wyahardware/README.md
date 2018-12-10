## 1.Camera
相机拍照、录制视频
#### 使用例子在 example module下的 hardware/camera 依赖库在hardware/camera 文件夹下
##### 使用说明
#####  1、布局使用
    <com.wya.hardware.camera.WYACameraView
           android:id="@+id/wya_camera_view"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           app:duration_max="10000"
           app:iconLeft="@drawable/wya_camera_ic_back"
           app:iconMargin="20dp"
           app:iconSize="30dp"
           app:iconSrc="@drawable/wya_camera_ic_camera" />

#####  2、方法说明
方法|说明
---|---
setSaveVideoPath(String path)|设置视频保存路径
setFeatures(int state)|设置只有拍照、只有录像、拍照和录像
setTip(String tip)|设置拍照按钮上面的文字
setMediaQuality(int quality)|设置录制质量
setDuration(int duration)|设置最长录制时间
setErrorListener(ErrorListener errorListener)|启动Camera错误回调
setWyaCameraListener(WYACameraListener wyaCameraListener)|拍照和录制的回调监听


## 2.VideoPlayer
视频播放器
#### 使用例子在 example module下的 hardware/videoplayer 依赖库在hardware/videoplayer 文件夹下
##### 使用说明（具体使用请查看example）
#####  1、布局使用
     <com.wya.hardware.videoplayer.WYAVideoView
            android:id="@+id/video_player"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:background="#888">
        </com.wya.hardware.videoplayer.WYAVideoView>

#####  2、方法说明
方法|说明
---|---
setOnVideoControlListener(OnVideoControlListener onVideoControlListener)|设置返回、全屏、重播监听
startPlayVideo(final IVideoInfo video)|设置播放的视频
isLock()|锁屏



