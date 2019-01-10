package com.wya.hardware.videoplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wya.hardware.R;
import com.wya.hardware.videoplayer.listener.OnVideoControlListener;

/**
 * @date: 2018/12/6 14:20
 * @author: Chunjiang Mao
 * @classname: VideoErrorView
 * @describe: 视频错误View
 */

public class VideoErrorView extends FrameLayout {
    
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_VIDEO_DETAIL_ERROR = 1;
    public static final int STATUS_VIDEO_SRC_ERROR = 2;
    public static final int STATUS_UN_WIFI_ERROR = 3;
    public static final int STATUS_NO_NETWORK_ERROR = 4;
    
    private int curStatus;
    private TextView videoErrorInfo;
    private Button videoErrorRetry;
    private OnVideoControlListener onVideoControlListener;
    
    public VideoErrorView(Context context) {
        super(context);
        init();
    }
    
    public VideoErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public VideoErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    public int getCurStatus() {
        return curStatus;
    }
    
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.video_controller_error, this);
    
        videoErrorInfo = (TextView) findViewById(R.id.video_error_info);
        videoErrorRetry = (Button) findViewById(R.id.video_error_retry);
    
        videoErrorRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVideoControlListener != null) {
                    onVideoControlListener.onRetry(curStatus);
                }
            }
        });
    
        hideError();
    }
    
    public void showError(int status) {
        setVisibility(View.VISIBLE);
        
        if (curStatus == status) {
            return;
        }
        
        curStatus = status;
        
        switch (status) {
            case STATUS_VIDEO_DETAIL_ERROR:
                Log.i("DDD", "showVideoDetailError");
                videoErrorInfo.setText("视频加载失败");
                videoErrorRetry.setText("点此重试");
                break;
            case STATUS_VIDEO_SRC_ERROR:
                Log.i("DDD", "showVideoSrcError");
                videoErrorInfo.setText("视频加载失败");
                videoErrorRetry.setText("点此重试");
                break;
            case STATUS_NO_NETWORK_ERROR:
                Log.i("DDD", "showNoNetWorkError");
                videoErrorInfo.setText("网络连接异常，请检查网络设置后重试");
                videoErrorRetry.setText("重试");
                break;
            case STATUS_UN_WIFI_ERROR:
                Log.i("DDD", "showUnWifiError");
                videoErrorInfo.setText("温馨提示：您正在使用非WiFi网络，播放将产生流量费用");
                videoErrorRetry.setText("继续播放");
                break;
            default:
                break;
        }
    }
    
    public void hideError() {
        Log.i("DDD", "hideError");
        setVisibility(GONE);
        curStatus = STATUS_NORMAL;
    }
    
    public void setOnVideoControlListener(OnVideoControlListener onVideoControlListener) {
        this.onVideoControlListener = onVideoControlListener;
    }
}
