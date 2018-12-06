package com.wya.example.module.hardware.videoplayer;


import com.wya.hardware.videoplayer.bean.IVideoInfo;

public class VideoDetailInfo implements IVideoInfo {

    public String title;
    public String videoPath;

    @Override
    public String getVideoTitle() {
        return title;
    }

    @Override
    public String getVideoPath() {
        return videoPath;
    }
}
