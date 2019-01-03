package com.wya.example.module.hardware.videoplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.hardware.videoplayer.WYAVideoView;
import com.wya.hardware.videoplayer.listener.SimpleOnVideoControlListener;
import com.wya.uikit.toolbar.StatusBarUtil;
import com.wya.utils.utils.ScreenUtil;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;

public class VideoPlayerExampleActivity extends BaseActivity {

    @BindView(R.id.video_player)
    WYAVideoView videoPlayer;

    private ViewGroup contentView;
    private VideoDetailInfo info;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_video_player_example;
    }


    @Override
    protected void initView() {
        getSwipeBackLayout().setToChangeWindowTranslucent(false);
        contentView = ((ViewGroup) findViewById(android.R.id.content));
        setToolBarTitle("视频播放(videoplayer)");
        initImgLeft(0, false);
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(VideoPlayerExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightImageAntherOnLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(VideoPlayerExampleActivity.this, url);
        });

        info = new VideoDetailInfo();
        info.videoPath = "http://221.228.226.5/14/z/w/y/y/zwyyobhyqvmwslabxyoaixvyubmekc/sh.yinyuetai.com/4599015ED06F94848EBF877EAAE13886.mp4";
        videoPlayer.setOnVideoControlListener(new SimpleOnVideoControlListener() {

            @Override
            public void onRetry(int errorStatus) {
                videoPlayer.startPlayVideo(info);
            }

            @Override
            public void onBack() {
                onBackPressed();
            }

            @Override
            public void onFullScreen() {
                initShowToolBar(false);
                contentView.setPadding(0, 0, 0, 0);
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
                contentView.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
                initShowToolBar(true);
                ScreenUtil.toggleScreenOrientation(this);
            }
        } else {
            super.onBackPressed();
        }
    }
}
