# VideoPlayer
## 功能说明
- 视频播放器

## 属性说明


## 用法说明
- 布局引用
```     
       <com.wya.hardware.videoplayer.WYAVideoView
                   android:id="@+id/video_player"
                   android:layout_width="match_parent"
                   android:layout_height="200dp"
                   android:background="#888">
               </com.wya.hardware.videoplayer.WYAVideoView>
```
- 方法

方法|说明
---|---
setOnVideoControlListener(OnVideoControlListener onVideoControlListener)|设置返回、全屏、重播监听
startPlayVideo(final IVideoInfo video)|设置播放的视频
isLock()|锁屏