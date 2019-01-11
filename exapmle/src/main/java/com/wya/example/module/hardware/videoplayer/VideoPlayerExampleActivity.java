package com.wya.example.module.hardware.videoplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.arialyy.aria.core.download.DownloadReceiver;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.example.module.utils.fliedownload.DownLoadExampleActivity;
import com.wya.example.module.utils.fliedownload.MediaUtils;
import com.wya.hardware.videoplayer.WYAVideoView;
import com.wya.hardware.videoplayer.listener.SimpleOnVideoControlListener;
import com.wya.uikit.toolbar.StatusBarUtil;
import com.wya.utils.utils.FileManagerUtil;
import com.wya.utils.utils.ScreenUtil;
import com.wya.utils.utils.StringUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_IMG_DIR;
import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_VIDEO_DIR;

/**
 * @date: 2019/1/10 14:33
 * @author: Chunjiang Mao
 * @classname: VideoPlayerExampleActivity
 * @describe:
 */

public class VideoPlayerExampleActivity extends BaseActivity {
    
    private static final String MVIDEOPATH =
            "http://221.228.226.5/14/z/w/y/y/zwyyobhyqvmwslabxyoaixvyubmekc/sh" +
                    ".yinyuetai.com/4599015ED06F94848EBF877EAAE13886.mp4";
    @BindView(R.id.video_player)
    WYAVideoView videoPlayer;
    private ViewGroup contentView;
    private VideoDetailInfo info;
    private FileManagerUtil mFileManagerUtil;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_player_example;
    }
    
    @Override
    protected void initView() {
        getSwipeBackLayout().setToChangeWindowTranslucent(false);
        contentView = ((ViewGroup) findViewById(android.R.id.content));
        setTitle("视频播放(videoplayer)");
        showLeftIcon(false);
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(VideoPlayerExampleActivity.this, ReadmeActivity.class)
                    .putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(VideoPlayerExampleActivity.this, url);
        });
        
        mFileManagerUtil = new FileManagerUtil();
        info = new VideoDetailInfo();
        
        info.videoPath = MVIDEOPATH;
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
                showToolBar(false);
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
                showToolBar(true);
                ScreenUtil.toggleScreenOrientation(this);
            }
        } else {
            super.onBackPressed();
        }
    }
    
    @OnClick(R.id.download_file)
    public void onViewClicked() {
        DownloadReceiver downloadReceiver = mFileManagerUtil.getDownloadReceiver();
        downloadReceiver.load(MVIDEOPATH)
                .setFilePath(FILE_VIDEO_DIR + File.separator + "viedo_" + StringUtil.getSign
                        (MVIDEOPATH)
                        + ".mp4")
                .start();
        
        File file = new File(FILE_IMG_DIR + "/" + "IMG_" + StringUtil.getSign(MVIDEOPATH) + ".jpg");
        if (!file.exists()) {
            MediaUtils.getImageForVideo(MVIDEOPATH, null);
        }
        startActivity(new Intent(this, DownLoadExampleActivity.class));
        finish();
    }
}
