package com.wya.hardware.videoplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.hardware.R;
import com.wya.hardware.videoplayer.WYAVideoPlayer;
import com.wya.hardware.videoplayer.bean.IVideoInfo;
import com.wya.hardware.videoplayer.listener.OnVideoControlListener;
import com.wya.hardware.videoplayer.listener.SimpleOnVideoControlListener;

import java.util.Locale;


/**
 * 创建日期：2018/12/6 14:19
 * 作者： Mao Chunjiang
 * 文件名称：VideoControllerView
 * 类说明：视频控制器，可替换或自定义样式
 */

public class VideoControllerView extends FrameLayout {

    public static final int DEFAULT_SHOW_TIME = 3000;

    private View mControllerBack;
    private View mControllerTitle;
    private TextView mVideoTitle;
    private View mControllerBottom;
    private SeekBar mPlayerSeekBar;
    private ImageView mVideoPlayState;
    private TextView mVideoProgress;
    private TextView mVideoDuration;
    private ImageView mVideoFullScreen;
    private ImageView mScreenLock;
    private VideoErrorView mErrorView;

    private boolean isScreenLock;
    private boolean mShowing;
    private boolean mAllowUnWifiPlay;
    private WYAVideoPlayer mPlayer;
    private IVideoInfo videoInfo;
    private OnVideoControlListener onVideoControlListener;

    public void setOnVideoControlListener(OnVideoControlListener onVideoControlListener) {
        this.onVideoControlListener = onVideoControlListener;
    }

    public VideoControllerView(Context context) {
        super(context);
        init();
    }

    public VideoControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.video_media_controller, this);

        initControllerPanel();
    }

    private void initControllerPanel() {
        // back
        mControllerBack = findViewById(R.id.video_back);
        mControllerBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVideoControlListener != null) {
                    onVideoControlListener.onBack();
                }
            }
        });
        // top
        mControllerTitle = findViewById(R.id.video_controller_title);
        mVideoTitle = (TextView) mControllerTitle.findViewById(R.id.video_title);
        // bottom
        mControllerBottom = findViewById(R.id.video_controller_bottom);
        mPlayerSeekBar = (SeekBar) mControllerBottom.findViewById(R.id.player_seek_bar);
        mVideoPlayState = (ImageView) mControllerBottom.findViewById(R.id.player_pause);
        mVideoProgress = (TextView) mControllerBottom.findViewById(R.id.player_progress);
        mVideoDuration = (TextView) mControllerBottom.findViewById(R.id.player_duration);
        mVideoFullScreen = (ImageView) mControllerBottom.findViewById(R.id.video_full_screen);
        mVideoPlayState.setOnClickListener(mOnPlayerPauseClick);
        mVideoPlayState.setImageResource(R.drawable.icon_pause);
        mPlayerSeekBar.setOnSeekBarChangeListener(mSeekListener);
        mVideoFullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVideoControlListener != null) {
                    onVideoControlListener.onFullScreen();
                }
            }
        });

        // lock
        mScreenLock = (ImageView) findViewById(R.id.player_lock_screen);
        mScreenLock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScreenLock) unlock();
                else lock();
                show();
            }
        });

        // error
        mErrorView = (VideoErrorView) findViewById(R.id.video_controller_error);
        mErrorView.setOnVideoControlListener(new SimpleOnVideoControlListener() {
            @Override
            public void onRetry(int errorStatus) {
                retry(errorStatus);
            }
        });

        mPlayerSeekBar.setMax(1000);
    }

    public void setMediaPlayer(WYAVideoPlayer player) {
        mPlayer = player;
        updatePausePlay();
    }

    public void setVideoInfo(IVideoInfo videoInfo) {
        this.videoInfo = videoInfo;
        mVideoTitle.setText(videoInfo.getVideoTitle());
    }

    public void toggleDisplay() {
        if (mShowing) {
            hide();
        } else {
            show();
        }
    }

    public void show() {
        show(DEFAULT_SHOW_TIME);
    }

    public void show(int timeout) {
        setProgress();

        if (!isScreenLock) {
            mControllerBack.setVisibility(VISIBLE);
            mControllerTitle.setVisibility(VISIBLE);
            mControllerBottom.setVisibility(VISIBLE);
        } else {
            if (!isPortrait(getContext())) {
                mControllerBack.setVisibility(GONE);
            }
            mControllerTitle.setVisibility(GONE);
            mControllerBottom.setVisibility(GONE);
        }

        if (!isPortrait(getContext())) {
            mScreenLock.setVisibility(VISIBLE);
        }

        mShowing = true;

        updatePausePlay();

        post(mShowProgress);

        if (timeout > 0) {
            removeCallbacks(mFadeOut);
            postDelayed(mFadeOut, timeout);
        }
    }

    /**
     * 获得当前屏幕的方向.
     *
     * @return 是否竖屏.
     */
    private boolean isPortrait(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }


    private void hide() {
        if (!mShowing) {
            return;
        }

        if (!isPortrait(getContext())) {
            // 横屏才消失
            mControllerBack.setVisibility(GONE);
        }
        mControllerTitle.setVisibility(GONE);
        mControllerBottom.setVisibility(GONE);
        mScreenLock.setVisibility(GONE);

        removeCallbacks(mShowProgress);

        mShowing = false;
    }

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private boolean mDragging;
    private long mDraggingProgress;
    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            int pos = setProgress();
            if (!mDragging && mShowing && mPlayer.isPlaying()) {
                postDelayed(mShowProgress, 1000 - (pos % 1000));
            }
        }
    };

    private int setProgress() {
        if (mPlayer == null || mDragging) {
            return 0;
        }
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        if (mPlayerSeekBar != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mPlayerSeekBar.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mPlayerSeekBar.setSecondaryProgress(percent * 10);
        }

        mVideoProgress.setText(stringForTime(position));
        mVideoDuration.setText(stringForTime(duration));

        return position;
    }

    /**
     * 将毫秒值转化为时分秒显示
     *
     * @param timeMs 毫秒值
     * @return
     */
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
    }

    /**
     * 判断显示错误类型
     */
    public void checkShowError(boolean isNetChanged) {
        boolean isConnect = isNetworkConnected(getContext());
        boolean isMobileNet = isMobileConnected(getContext());
        boolean isWifiNet = isWifiConnected(getContext());

        if (isConnect) {
            // 如果已经联网
            if (mErrorView.getCurStatus() == VideoErrorView.STATUS_NO_NETWORK_ERROR && !(isMobileNet && !isWifiNet)) {
                // 如果之前是无网络 TODO 应该提示“网络已经重新连上，请重试”，这里暂不处理
            } else if (videoInfo == null) {
                // 优先判断是否有video数据
                showError(VideoErrorView.STATUS_VIDEO_DETAIL_ERROR);
            } else if (isMobileNet && !isWifiNet && !mAllowUnWifiPlay) {
                // 如果是手机流量，且未同意过播放，且非本地视频，则提示错误
                mErrorView.showError(VideoErrorView.STATUS_UN_WIFI_ERROR);
                mPlayer.pause();
            } else if (isWifiNet && isNetChanged && mErrorView.getCurStatus() == VideoErrorView.STATUS_UN_WIFI_ERROR) {
                // 如果是wifi流量，且之前是非wifi错误，则恢复播放
                playFromUnWifiError();
            } else if (!isNetChanged) {
                showError(VideoErrorView.STATUS_VIDEO_SRC_ERROR);
            }
        } else {
            mPlayer.pause();
            showError(VideoErrorView.STATUS_NO_NETWORK_ERROR);
        }
    }

    /**
     * 判断WIFI网络是否可用
     */
    private boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isConnected();
            }
        }
        return false;
    }


    /**
     * 判断是否有网络连接
     */
    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    private boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }


    public void hideErrorView() {
        mErrorView.hideError();
    }

    private void reload() {
        mPlayer.restart();
    }

    public void release() {
        removeCallbacks(mShowProgress);
        removeCallbacks(mFadeOut);
    }

    private void retry(int status) {
        Log.i("DDD", "retry " + status);

        switch (status) {
            case VideoErrorView.STATUS_VIDEO_DETAIL_ERROR:
                // 传递给activity
                if (onVideoControlListener != null) {
                    onVideoControlListener.onRetry(status);
                }
                break;
            case VideoErrorView.STATUS_VIDEO_SRC_ERROR:
                reload();
                break;
            case VideoErrorView.STATUS_UN_WIFI_ERROR:
                allowUnWifiPlay();
                break;
            case VideoErrorView.STATUS_NO_NETWORK_ERROR:
                // 无网络时
                if (isNetworkConnected(getContext())) {
                    if (videoInfo == null) {
                        // 如果video为空，重新请求详情
                        retry(VideoErrorView.STATUS_VIDEO_DETAIL_ERROR);
                    } else if (mPlayer.isInPlaybackState()) {
                        // 如果有video，可以直接播放的直接恢复
                        mPlayer.start();
                    } else {
                        // 视频未准备好，重新加载
                        reload();
                    }
                    hideErrorView();
                } else {
                    Toast.makeText(getContext(), "网络未连接", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            show(3600000);

            mDragging = true;

            removeCallbacks(mShowProgress);
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {
                return;
            }

            long duration = mPlayer.getDuration();
            mDraggingProgress = (duration * progress) / 1000L;

            if (mVideoProgress != null) {
                mVideoProgress.setText(stringForTime((int) mDraggingProgress));
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            mPlayer.seekTo((int) mDraggingProgress);
            play();
            mDragging = false;
            mDraggingProgress = 0;

            post(mShowProgress);
        }
    };

    private void showError(int status) {
        mErrorView.showError(status);
        hide();

        // 如果提示了错误，则看需要解锁
        if (isScreenLock) {
            unlock();
        }
    }

    public boolean isLock() {
        return isScreenLock;
    }

    private void lock() {
        Log.i("DDD", "lock");
        isScreenLock = true;
        mScreenLock.setImageResource(R.drawable.wya_video_player_video_locked);
    }

    private void unlock() {
        Log.i("DDD", "unlock");
        isScreenLock = false;
        mScreenLock.setImageResource(R.drawable.wya_video_player_video_unlock);
    }

    private void allowUnWifiPlay() {
        Log.i("DDD", "allowUnWifiPlay");

        mAllowUnWifiPlay = true;

        playFromUnWifiError();
    }

    private void playFromUnWifiError() {
        Log.i("DDD", "playFromUnWifiError");

        // TODO: 2017/6/19 check me
        if (mPlayer.isInPlaybackState()) {
            mPlayer.start();
            hideErrorView();
        } else {
            mPlayer.restart();
        }
    }

    private OnClickListener mOnPlayerPauseClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
        }
    };

    public void updatePausePlay() {
        if (mPlayer.isPlaying()) {
            mVideoPlayState.setImageResource(R.drawable.icon_pause);
        } else {
            mVideoPlayState.setImageResource(R.drawable.icon_begin);
        }
    }

    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    private void pause() {
        mPlayer.pause();
        updatePausePlay();
        removeCallbacks(mFadeOut);
    }

    private void play() {
        mPlayer.start();
        show();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggleVideoLayoutParams();
    }

    void toggleVideoLayoutParams() {
        final boolean isPortrait = isPortrait(getContext());

        if (isPortrait) {
            mControllerBack.setVisibility(VISIBLE);
            mVideoFullScreen.setVisibility(View.VISIBLE);
            mScreenLock.setVisibility(GONE);
        } else {
            mVideoFullScreen.setVisibility(View.GONE);
            if (mShowing) {
                mScreenLock.setVisibility(VISIBLE);
            }
        }
    }

}
