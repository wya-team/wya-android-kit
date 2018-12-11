package com.wya.example.module.hardware.videoplayer;

import android.content.res.Configuration;
import android.view.WindowManager;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.hardware.videoplayer.WYAVideoView;
import com.wya.hardware.videoplayer.listener.SimpleOnVideoControlListener;
import com.wya.utils.utils.ScreenUtil;

import butterknife.BindView;

public class VideoPlayerExampleActivity extends BaseActivity {


    @BindView(R.id.video_player)
    WYAVideoView videoPlayer;

    private VideoDetailInfo info;
    @Override
    protected int getLayoutID() {
        return R.layout.activity_video_player_example;
    }


    @Override
    protected void initView() {
        initShowToolBar(false);
        info = new VideoDetailInfo();
        info.title = "测试视频";
        info.videoPath = "http://221.228.226.5/14/z/w/y/y/zwyyobhyqvmwslabxyoaixvyubmekc/sh.yinyuetai.com/4599015ED06F94848EBF877EAAE13886.mp4";
        videoPlayer.setOnVideoControlListener(new SimpleOnVideoControlListener() {

            @Override
            public void onRetry(int errorStatus) {
                // TODO:  调用业务接口重新获取数据
                // get info and call method "videoPlayer.startPlayVideo(info);"
            }

            @Override
            public void onBack() {
                onBackPressed();
            }

            @Override
            public void onFullScreen() {
                ScreenUtil.toggleScreenOrientation(VideoPlayerExampleActivity.this);
            }
        });
        videoPlayer.startPlayVideo(info);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoPlayer.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        videoPlayer.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayer.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!ScreenUtil.isPortrait(this)) {
            if (!videoPlayer.isLock()) {
                ScreenUtil.toggleScreenOrientation(this);
            }
        } else {
            super.onBackPressed();
        }
    }

}
