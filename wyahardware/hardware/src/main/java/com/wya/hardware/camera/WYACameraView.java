package com.wya.hardware.camera;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.wya.hardware.R;
import com.wya.hardware.camera.listener.CaptureListener;
import com.wya.hardware.camera.listener.ClickListener;
import com.wya.hardware.camera.listener.ErrorListener;
import com.wya.hardware.camera.listener.TypeListener;
import com.wya.hardware.camera.listener.WYACameraListener;
import com.wya.hardware.camera.state.CameraMachine;
import com.wya.hardware.camera.view.CameraView;

import java.io.File;
import java.io.IOException;

import static android.graphics.Color.TRANSPARENT;

/**
 * @date: 2018/12/5 14:25
 * @author: Chunjiang Mao
 * @classname: WYACameraView
 * @describe:
 */

public class WYACameraView extends FrameLayout implements CameraInterface.CameraOpenOverCallback, SurfaceHolder
        .Callback, CameraView {
    //    private static final String TAG = "JCameraView";
    
    /**
     * 拍照浏览时候的类型
     */
    public static final int TYPE_PICTURE = 0x001;
    public static final int TYPE_VIDEO = 0x002;
    public static final int TYPE_SHORT = 0x003;
    public static final int TYPE_DEFAULT = 0x004;
    /**
     * 录制视频比特率
     */
    public static final int MEDIA_QUALITY_HIGH = 20 * 100000;
    public static final int MEDIA_QUALITY_MIDDLE = 16 * 100000;
    public static final int MEDIA_QUALITY_LOW = 12 * 100000;
    public static final int MEDIA_QUALITY_POOR = 8 * 100000;
    public static final int MEDIA_QUALITY_FUNNY = 4 * 100000;
    public static final int MEDIA_QUALITY_DESPAIR = 2 * 100000;
    public static final int MEDIA_QUALITY_SORRY = 1 * 80000;
    /**
     * 只能拍照
     */
    public static final int BUTTON_STATE_ONLY_CAPTURE = 0x101;
    /**
     * 只能录像
     */
    public static final int BUTTON_STATE_ONLY_RECORDER = 0x102;
    /**
     * 两者都可以
     */
    public static final int BUTTON_STATE_BOTH = 0x103;
    /**
     * 闪关灯状态
     */
    private static final int TYPE_FLASH_AUTO = 0x021;
    private static final int TYPE_FLASH_ON = 0x022;
    private static final int TYPE_FLASH_OFF = 0x023;
    /**
     * Camera状态机
     */
    private CameraMachine machine;
    /**
     * 手电筒状态
     */
    private boolean typeFlashLight = false;
    private int typeFlash = TYPE_FLASH_OFF;
    /**
     * 回调监听
     */
    private WYACameraListener wyaCameraListener;
    private ClickListener leftClickListener;
    private ClickListener rightClickListener;
    
    private Context mContext;
    private VideoView mVideoView;
    private ImageView mPhoto;
    private ImageView mSwitchCamera;
    private CameraManager manager;
    private Camera mCamera = null;
    private ImageView mFlashLamp;
    private ImageView imageFlashLight;
    private CaptureLayout mCaptureLayout;
    private FoucsView mFoucsView;
    private MediaPlayer mMediaPlayer;
    
    private int layoutWidth;
    private float screenProp = 0f;
    /**
     * 捕获的图片
     */
    private Bitmap captureBitmap;
    /**
     * 第一帧图片
     */
    private Bitmap firstFrame;
    /**
     * 视频URL
     */
    private String videoUrl;
    
    //切换摄像头按钮的参数
    
    /**
     * 图标大小
     */
    private int iconSize = 0;
    /**
     * 右上边距
     */
    private int iconMargin = 0;
    /**
     * 图标资源
     */
    private int iconSrc = 0;
    /**
     * 左图标
     */
    private int iconLeft = 0;
    /**
     * 右图标
     */
    private int iconRight = 0;
    /**
     * 录制时间
     */
    private int duration = 0;
    
    /**
     * 缩放梯度
     */
    private int zoomGradient = 0;
    
    private boolean firstTouch = true;
    private float firstTouchLength = 0;
    private ErrorListener errorListener;
    
    public WYACameraView(Context context) {
        this(context, null);
    }
    
    public WYACameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public WYACameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //get AttributeSet
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WYACameraView, defStyleAttr, 0);
        iconSize = a.getDimensionPixelSize(R.styleable.WYACameraView_iconSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 35, getResources().getDisplayMetrics()));
        iconMargin = a.getDimensionPixelSize(R.styleable.WYACameraView_iconMargin, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        iconSrc = a.getResourceId(R.styleable.WYACameraView_iconSrc, R.drawable.icon_camera_switch);
        iconLeft = a.getResourceId(R.styleable.WYACameraView_iconLeft, R.drawable.icon_bottom);
        iconRight = a.getResourceId(R.styleable.WYACameraView_iconRight, 0);
        //没设置默认为10s
        duration = a.getInteger(R.styleable.WYACameraView_duration_max, 10 * 1000);
        a.recycle();
        initData();
        initView();
    }
    
    /**
     * 删除文件
     *
     * @param url
     * @return
     */
    private static boolean deleteFile(String url) {
        boolean result = false;
        File file = new File(url);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }
    
    private void initData() {
        layoutWidth = getScreenWidth(mContext);
        //缩放梯度
        zoomGradient = (int) (layoutWidth / 16f);
        machine = new CameraMachine(getContext(), this, this);
    }
    
    /**
     * 获取屏幕高
     *
     * @param context
     * @return
     */
    private int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
    
    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
    
    private void initView() {
        setWillNotDraw(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.camera_view, this);
        mVideoView = (VideoView) view.findViewById(R.id.video_preview);
        mPhoto = (ImageView) view.findViewById(R.id.image_photo);
        mSwitchCamera = (ImageView) view.findViewById(R.id.image_switch);
        mSwitchCamera.setImageResource(iconSrc);
        mFlashLamp = (ImageView) view.findViewById(R.id.image_flash);
        imageFlashLight = (ImageView) view.findViewById(R.id.image_flash_light);
        setFlashRes();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        }
        //闪光灯
        mFlashLamp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                typeFlash++;
                if (typeFlash > 0x023) {
                    typeFlash = TYPE_FLASH_AUTO;
                }
                setFlashRes();
            }
        });
        
        //手电筒
        imageFlashLight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setFlashLight();
            }
        });
        
        mCaptureLayout = (CaptureLayout) view.findViewById(R.id.capture_layout);
        mCaptureLayout.setDuration(duration);
        mCaptureLayout.setIconSrc(iconLeft, iconRight);
        mFoucsView = (FoucsView) view.findViewById(R.id.fouce_view);
        mVideoView.getHolder().addCallback(this);
        //切换摄像头
        mSwitchCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                turnCamera();
            }
        });
        //拍照 录像
        mCaptureLayout.setCaptureListener(new CaptureListener() {
            @Override
            public void takePictures() {
                mSwitchCamera.setVisibility(INVISIBLE);
                mFlashLamp.setVisibility(INVISIBLE);
                imageFlashLight.setVisibility(INVISIBLE);
                machine.capture();
            }
            
            @Override
            public void recordStart() {
                mSwitchCamera.setVisibility(INVISIBLE);
                mFlashLamp.setVisibility(INVISIBLE);
                imageFlashLight.setVisibility(INVISIBLE);
                machine.record(mVideoView.getHolder().getSurface(), screenProp);
            }
            
            @Override
            public void recordShort(final long time) {
                mCaptureLayout.setTextWithAnimation("录制时间过短");
                mSwitchCamera.setVisibility(VISIBLE);
                mFlashLamp.setVisibility(VISIBLE);
                imageFlashLight.setVisibility(VISIBLE);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        machine.stopRecord(true, time);
                    }
                }, 1500 - time);
            }
            
            @Override
            public void recordEnd(long time) {
                machine.stopRecord(false, time);
            }
            
            @Override
            public void recordZoom(float zoom) {
                machine.zoom(zoom, CameraInterface.TYPE_RECORDER);
            }
            
            @Override
            public void recordError() {
                if (errorListener != null) {
                    errorListener.audiopermissionerror();
                }
            }
        });
        //确认 取消
        mCaptureLayout.setTypeListener(new TypeListener() {
            @Override
            public void cancel() {
                machine.cancel(mVideoView.getHolder(), screenProp);
            }
            
            @Override
            public void confirm() {
                machine.confirm();
            }
        });
        mCaptureLayout.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (leftClickListener != null) {
                    leftClickListener.onClick();
                }
            }
        });
        mCaptureLayout.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (rightClickListener != null) {
                    rightClickListener.onClick();
                }
            }
        });
    }

    /**
     * 调转摄像头
     */
    private void turnCamera() {
        machine.mySwitch(mVideoView.getHolder(), screenProp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float widthSize = mVideoView.getMeasuredWidth();
        float heightSize = mVideoView.getMeasuredHeight();
        if (screenProp == 0) {
            screenProp = heightSize / widthSize;
        }
    }
    
    @Override
    public void cameraHasOpened() {
        CameraInterface.getInstance().doStartPreview(mVideoView.getHolder(), screenProp);
        getActivityFromView(this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoView.setBackgroundColor(TRANSPARENT);
                    }
                }, 80);
                
            }
        });
    }
    
    public void onResume() {
        //重置状态
        resetState(TYPE_DEFAULT);
        CameraInterface.getInstance().registerSensorManager(mContext);
        CameraInterface.getInstance().setSwitchView(mSwitchCamera, mFlashLamp);
        machine.start(mVideoView.getHolder(), screenProp);
    }
    
    public void onPause() {
        stopVideo();
        resetState(TYPE_PICTURE);
        CameraInterface.getInstance().isPreview(false);
        CameraInterface.getInstance().unregisterSensorManager(mContext);
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread() {
            @Override
            public void run() {
                CameraInterface.getInstance().doOpenCamera(WYACameraView.this);
            }
        }.start();
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        CameraInterface.getInstance().doDestroyCamera();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 1) {
                    //显示对焦指示器
                    setFocusViewWidthAnimation(event.getX(), event.getY());
                }
                if (event.getPointerCount() == 2) {
                    Log.i("MCJ", "ACTION_DOWN = " + 2);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    firstTouch = true;
                }
                if (event.getPointerCount() == 2) {
                    //第一个点
                    float point1X = event.getX(0);
                    float point1Y = event.getY(0);
                    //第二个点
                    float point2X = event.getX(1);
                    float point2Y = event.getY(1);
                    
                    float result = (float) Math.sqrt(Math.pow(point1X - point2X, 2) + Math.pow(point1Y -
                            point2Y, 2));
                    
                    if (firstTouch) {
                        firstTouchLength = result;
                        firstTouch = false;
                    }
                    if ((int) (result - firstTouchLength) / zoomGradient != 0) {
                        firstTouch = true;
                        machine.zoom(result - firstTouchLength, CameraInterface.TYPE_CAPTURE);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                firstTouch = true;
                break;
            default:
                break;
        }
        return true;
    }
    
    /**
     * 对焦框指示器动画
     *
     * @param x
     * @param y
     */
    private void setFocusViewWidthAnimation(float x, float y) {
        machine.focus(x, y, new CameraInterface.FocusCallback() {
            @Override
            public void focusSuccess() {
                mFoucsView.setVisibility(INVISIBLE);
            }
        });
    }
    
    private void updateVideoViewSize(float videoWidth, float videoHeight) {
        if (videoWidth > videoHeight) {
            LayoutParams videoViewParam;
            int height = (int) ((videoHeight / videoWidth) * getWidth());
            videoViewParam = new LayoutParams(LayoutParams.MATCH_PARENT, height);
            videoViewParam.gravity = Gravity.CENTER;
            mVideoView.setLayoutParams(videoViewParam);
        }
    }
    
    /**************************************************
     * 对外提供的API                     *
     **************************************************/
    
    public void setSaveVideoPath(String path) {
        CameraInterface.getInstance().setSaveVideoPath(path);
    }
    
    public void setCameraListener(WYACameraListener wyaCameraListener) {
        this.wyaCameraListener = wyaCameraListener;
    }
    
    /**
     * 启动Camera错误回调
     *
     * @param errorListener
     */
    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
        CameraInterface.getInstance().setErrorListener(errorListener);
    }
    
    /**
     * 设置CaptureButton功能（拍照和录像）
     *
     * @param state
     */
    public void setFeatures(int state) {
        mCaptureLayout.setButtonFeatures(state);
    }
    
    /**
     * 设置录制质量
     *
     * @param quality
     */
    public void setMediaQuality(int quality) {
        CameraInterface.getInstance().setMediaQuality(quality);
    }
    
    /**
     * 设置最长录制时间
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
        mCaptureLayout.setDuration(duration);
    }
    
    @Override
    public void resetState(int type) {
        switch (type) {
            case TYPE_VIDEO:
                stopVideo();//停止播放
                //初始化VideoView
                deleteFile(videoUrl);
                mVideoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                machine.start(mVideoView.getHolder(), screenProp);
                break;
            case TYPE_PICTURE:
                mPhoto.setVisibility(INVISIBLE);
                break;
            case TYPE_SHORT:
                break;
            case TYPE_DEFAULT:
                mVideoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                break;
            default:
                break;
        }
        mSwitchCamera.setVisibility(VISIBLE);
        mFlashLamp.setVisibility(VISIBLE);
        imageFlashLight.setVisibility(VISIBLE);
        mCaptureLayout.resetCaptureLayout();
    }
    
    @Override
    public void confirmState(int type) {
        switch (type) {
            case TYPE_VIDEO:
                stopVideo();    //停止播放
                mVideoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                machine.start(mVideoView.getHolder(), screenProp);
                if (wyaCameraListener != null) {
                    wyaCameraListener.recordSuccess(videoUrl, firstFrame);
                }
                break;
            case TYPE_PICTURE:
                mPhoto.setVisibility(INVISIBLE);
                if (wyaCameraListener != null) {
                    wyaCameraListener.captureSuccess(captureBitmap);
                }
                break;
            case TYPE_SHORT:
                break;
            case TYPE_DEFAULT:
                break;
            default:
                break;
        }
        mCaptureLayout.resetCaptureLayout();
    }
    
    @Override
    public void showPicture(Bitmap bitmap, boolean isVertical) {
        if (isVertical) {
            mPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            mPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        captureBitmap = bitmap;
        mPhoto.setImageBitmap(bitmap);
        mPhoto.setVisibility(VISIBLE);
        mCaptureLayout.startAlphaAnimation();
        mCaptureLayout.startTypeBtnAnimator();
    }
    
    @Override
    public void playVideo(Bitmap firstFrame, final String url) {
        videoUrl = url;
        WYACameraView.this.firstFrame = firstFrame;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                try {
                    if (mMediaPlayer == null) {
                        mMediaPlayer = new MediaPlayer();
                    } else {
                        mMediaPlayer.reset();
                    }
                    mMediaPlayer.setDataSource(url);
                    mMediaPlayer.setSurface(mVideoView.getHolder().getSurface());
                    mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer
                            .OnVideoSizeChangedListener() {
                        @Override
                        public void
                        onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            updateVideoViewSize(mMediaPlayer.getVideoWidth(), mMediaPlayer
                                    .getVideoHeight());
                        }
                    });
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mMediaPlayer.start();
                        }
                    });
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    @Override
    public void stopVideo() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    
    @Override
    public void setTip(String tip) {
        mCaptureLayout.setTip(tip);
    }
    
    @Override
    public void startPreviewCallback() {
        handlerFocus(mFoucsView.getWidth() / 2, mFoucsView.getHeight() / 2);
    }
    
    @Override
    public boolean handlerFocus(float x, float y) {
        if (y > mCaptureLayout.getTop()) {
            return false;
        }
        mFoucsView.setVisibility(VISIBLE);
        if (x < mFoucsView.getWidth() / 2) {
            x = mFoucsView.getWidth() / 2;
        }
        if (x > layoutWidth - mFoucsView.getWidth() / 2) {
            x = layoutWidth - mFoucsView.getWidth() / 2;
        }
        if (y < mFoucsView.getWidth() / 2) {
            y = mFoucsView.getWidth() / 2;
        }
        if (y > mCaptureLayout.getTop() - mFoucsView.getWidth() / 2) {
            y = mCaptureLayout.getTop() - mFoucsView.getWidth() / 2;
        }
        mFoucsView.setX(x - mFoucsView.getWidth() / 2);
        mFoucsView.setY(y - mFoucsView.getHeight() / 2);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFoucsView, "scaleX", 1, 0.6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFoucsView, "scaleY", 1, 0.6f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFoucsView, "alpha", 1f, 0.4f, 1f, 0.4f, 1f, 0.4f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(scaleX).with(scaleY).before(alpha);
        animSet.setDuration(400);
        animSet.start();
        return true;
    }
    
    public void setLeftClickListener(ClickListener clickListener) {
        leftClickListener = clickListener;
    }
    
    public void setRightClickListener(ClickListener clickListener) {
        rightClickListener = clickListener;
    }
    
    private void setFlashRes() {
        switch (typeFlash) {
            case TYPE_FLASH_AUTO:
                mFlashLamp.setImageResource(R.drawable.icon_camera_flash_yelow);
                machine.flash(Camera.Parameters.FLASH_MODE_AUTO);
                break;
            case TYPE_FLASH_ON:
                mFlashLamp.setImageResource(R.drawable.icon_camera_flash_open);
                machine.flash(Camera.Parameters.FLASH_MODE_ON);
                break;
            case TYPE_FLASH_OFF:
                mFlashLamp.setImageResource(R.drawable.icon_camera_flash_close);
                machine.flash(Camera.Parameters.FLASH_MODE_OFF);
                break;
            default:
                break;
        }
    }
    
    private void setFlashLight() {
        typeFlashLight = !typeFlashLight;
        PackageManager packageManager = getContext().getPackageManager();
        FeatureInfo[] features = packageManager.getSystemAvailableFeatures();
        for (FeatureInfo featureInfo : features) {
            if (PackageManager.FEATURE_CAMERA_FLASH.equals(featureInfo.name)) {
                // 判断设备是否支持闪光灯
                if (typeFlashLight) {
                    machine.flash(Camera.Parameters.FLASH_MODE_TORCH);
                } else {
                    machine.flash(Camera.Parameters.FLASH_MODE_OFF);
                }
            }
        }
    }
    
    public Activity getActivityFromView(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }
}
