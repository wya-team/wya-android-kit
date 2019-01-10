package com.wya.hardware.camera;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import com.wya.hardware.camera.listener.CaptureListener;
import com.wya.hardware.camera.util.CheckPermission;

import static com.wya.hardware.camera.WYACameraView.BUTTON_STATE_BOTH;
import static com.wya.hardware.camera.WYACameraView.BUTTON_STATE_ONLY_CAPTURE;
import static com.wya.hardware.camera.WYACameraView.BUTTON_STATE_ONLY_RECORDER;

/**
 * @date: 2018/12/5 14:11
 * @author: Chunjiang Mao
 * @classname: CaptureButton
 * @describe: 拍照, 录制, 两者按钮
 */

public class CaptureButton extends View {
    
    /**
     * 空闲状态
     */
    public static final int STATE_IDLE = 0x001;
    /**
     * 按下状态
     */
    public static final int STATE_PRESS = 0x002;
    /**
     * 长按状态
     */
    public static final int STATE_LONG_PRESS = 0x003;
    /**
     * 录制状态
     */
    public static final int STATE_RECORDERING = 0x004;
    /**
     * 禁止状态
     */
    public static final int STATE_BAN = 0x005;
    /**
     * 当前按钮状态
     */
    private int state;
    /**
     * 按钮可执行的功能状态（拍照,录制,两者）
     */
    private int buttonState;
    /**
     * 进度条颜色
     */
    private int progressColor = 0xEE16AE16;
    /**
     * 外圆背景色
     */
    private int outsideColor = 0xEEDCDCDC;
    /**
     * 内圆背景色
     */
    private int insideColor = 0xFFFFFFFF;
    
    /**
     * Touch_Event_Down时候记录的Y值
     */
    private float eventY;
    
    private Paint mPaint;
    /**
     * 进度条宽度
     */
    private float strokeWidth;
    /**
     * 长按外圆半径变大的Size
     */
    private int outsideAddSize;
    /**
     * 长安内圆缩小的Size
     */
    private int insideReduceSize;
    
    /**
     * 中心坐标
     */
    private float centerX;
    private float centerY;
    /**
     * 按钮半径
     */
    private float buttonRadius;
    /**
     * 外圆半径
     */
    private float buttonOutsideRadius;
    /**
     * 内圆半径
     */
    private float buttonInsideRadius;
    /**
     * 按钮大小
     */
    private int buttonSize;
    
    /**
     * 录制视频的进度
     */
    private float progress;
    /**
     * 录制视频最大时间长度
     */
    private int duration;
    /**
     * 最短录制时间限制
     */
    private int minDuration;
    /**
     * 记录当前录制的时间
     */
    private int recordedTime;
    
    private RectF rectF;
    
    /**
     * 长按后处理的逻辑Runnable
     */
    private LongPressRunnable longPressRunnable;
    /**
     * 按钮回调接口
     */
    private CaptureListener captureListener;
    /**
     * 计时器
     */
    private RecordCountDownTimer timer;
    
    public CaptureButton(Context context) {
        super(context);
    }
    
    public CaptureButton(Context context, int size) {
        super(context);
        buttonSize = size;
        buttonRadius = size / 2.0f;
        
        buttonOutsideRadius = buttonRadius;
        buttonInsideRadius = buttonRadius * 0.75f;
        
        strokeWidth = size / 15;
        outsideAddSize = size / 5;
        insideReduceSize = size / 8;
        
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        
        progress = 0;
        longPressRunnable = new LongPressRunnable();
        //初始化为空闲状态
        state = STATE_IDLE;
        //初始化按钮为可录制可拍照
        buttonState = BUTTON_STATE_BOTH;
        //默认最长录制时间为10s
        duration = 10 * 1000;
        //默认最短录制时间为1.5s
        minDuration = 1500;
        
        centerX = (buttonSize + outsideAddSize * 2) / 2;
        centerY = (buttonSize + outsideAddSize * 2) / 2;
        
        rectF = new RectF(
                centerX - (buttonRadius + outsideAddSize - strokeWidth / 2),
                centerY - (buttonRadius + outsideAddSize - strokeWidth / 2),
                centerX + (buttonRadius + outsideAddSize - strokeWidth / 2),
                centerY + (buttonRadius + outsideAddSize - strokeWidth / 2));
        //录制定时器
        timer = new RecordCountDownTimer(duration, duration / 360);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(buttonSize + outsideAddSize * 2, buttonSize + outsideAddSize * 2);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        //外圆（半透明灰色）
        mPaint.setColor(outsideColor);
        canvas.drawCircle(centerX, centerY, buttonOutsideRadius, mPaint);
        //内圆（白色）
        mPaint.setColor(insideColor);
        canvas.drawCircle(centerX, centerY, buttonInsideRadius, mPaint);
        
        //如果状态为录制状态，则绘制录制进度条
        if (state == STATE_RECORDERING) {
            mPaint.setColor(progressColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
            canvas.drawArc(rectF, -90, progress, false, mPaint);
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() > 1 || state != STATE_IDLE) {
                    break;
                }
                //记录Y值
                eventY = event.getY();
                //修改当前状态为点击按下
                state = STATE_PRESS;
    
                //判断按钮状态是否为可录制状态
                if ((buttonState == BUTTON_STATE_ONLY_RECORDER || buttonState == BUTTON_STATE_BOTH)) {
                    //同时延长500启动长按后处理的逻辑Runnable
                    postDelayed(longPressRunnable, 500);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (captureListener != null
                        && state == STATE_RECORDERING
                        && (buttonState == BUTTON_STATE_ONLY_RECORDER || buttonState == BUTTON_STATE_BOTH)) {
                    //记录当前Y值与按下时候Y值的差值，调用缩放回调接口
                    captureListener.recordZoom(eventY - event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                //根据当前按钮的状态进行相应的处理
                handlerUnpressByState();
                break;
            default:
                break;
        }
        return true;
    }
    
    /**
     * 当手指松开按钮时候处理的逻辑
     */
    private void handlerUnpressByState() {
        //移除长按逻辑的Runnable
        removeCallbacks(longPressRunnable);
        //根据当前状态处理
        switch (state) {
            //当前是点击按下
            case STATE_PRESS:
                if (captureListener != null && (buttonState == BUTTON_STATE_ONLY_CAPTURE || buttonState == BUTTON_STATE_BOTH)) {
                    startCaptureAnimation(buttonInsideRadius);
                } else {
                    state = STATE_IDLE;
                }
                break;
            //当前是长按状态
            case STATE_RECORDERING:
                //停止计时器
                timer.cancel();
                //录制结束
                recordEnd();
                break;
            default:
                break;
        }
    }
    
    /**
     * 录制结束
     */
    private void recordEnd() {
        if (captureListener != null) {
            if (recordedTime < minDuration) {
                //回调录制时间过短
                captureListener.recordShort(recordedTime);
            } else {
                //回调录制结束
                captureListener.recordEnd(recordedTime);
            }
        }
        resetRecordAnim();  //重制按钮状态
    }
    
    /**
     * 重制状态
     */
    private void resetRecordAnim() {
        state = STATE_BAN;
        //重制进度
        progress = 0;
        invalidate();
        //还原按钮初始状态动画
        startRecordAnimation(
                buttonOutsideRadius,
                buttonRadius,
                buttonInsideRadius,
                buttonRadius * 0.75f
        );
    }
    
    /**
     * 内圆动画
     *
     * @param insideStart
     */
    private void startCaptureAnimation(float insideStart) {
        ValueAnimator insideAnim = ValueAnimator.ofFloat(insideStart, insideStart * 0.75f, insideStart);
        insideAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonInsideRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        insideAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //回调拍照接口
                captureListener.takePictures();
                state = STATE_BAN;
            }
        });
        insideAnim.setDuration(100);
        insideAnim.start();
    }
    
    /**
     * 内外圆动画
     *
     * @param outsideStart
     * @param outsideEnd
     * @param insideStart
     * @param insideEnd
     */
    private void startRecordAnimation(float outsideStart, float outsideEnd, float insideStart, float insideEnd) {
        ValueAnimator outsideAnim = ValueAnimator.ofFloat(outsideStart, outsideEnd);
        ValueAnimator insideAnim = ValueAnimator.ofFloat(insideStart, insideEnd);
        //外圆动画监听
        outsideAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonOutsideRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        //内圆动画监听
        insideAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonInsideRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        AnimatorSet set = new AnimatorSet();
        //当动画结束后启动录像Runnable并且回调录像开始接口
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //设置为录制状态
                if (state == STATE_LONG_PRESS) {
                    if (captureListener != null) {
                        captureListener.recordStart();
                    }
                    state = STATE_RECORDERING;
                    timer.start();
                }
            }
        });
        set.playTogether(outsideAnim, insideAnim);
        set.setDuration(100);
        set.start();
    }
    
    /**
     * 更新进度条
     *
     * @param millisUntilFinished
     */
    private void updateProgress(long millisUntilFinished) {
        recordedTime = (int) (duration - millisUntilFinished);
        progress = 360f - millisUntilFinished / (float) duration * 360f;
        invalidate();
    }
    
    /**
     * 设置最长录制时间
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
        //录制定时器
        timer = new RecordCountDownTimer(duration, duration / 360);
    }
    
    /**
     * 设置最短录制时间
     *
     * @param duration
     */
    public void setMinDuration(int duration) {
        minDuration = duration;
    }
    
    /**************************************************
     * 对外提供的API                     *
     **************************************************/
    
    /**
     * 设置回调接口
     *
     * @param captureListener
     */
    public void setCaptureListener(CaptureListener captureListener) {
        this.captureListener = captureListener;
    }
    
    /**
     * 设置按钮功能（拍照和录像）
     *
     * @param state
     */
    public void setButtonFeatures(int state) {
        buttonState = state;
    }
    
    /**
     * 是否空闲状态
     *
     * @return
     */
    public boolean isIdle() {
        return state == STATE_IDLE ? true : false;
    }
    
    /**
     * 设置状态
     */
    public void resetState() {
        state = STATE_IDLE;
    }
    
    /**
     * 录制视频计时器
     */
    private class RecordCountDownTimer extends CountDownTimer {
        RecordCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
    
        @Override
        public void onTick(long millisUntilFinished) {
            updateProgress(millisUntilFinished);
        }
    
        @Override
        public void onFinish() {
            updateProgress(0);
            recordEnd();
        }
    }
    
    /**
     * 长按线程
     */
    private class LongPressRunnable implements Runnable {
        @Override
        public void run() {
            //如果按下后经过500毫秒则会修改当前状态为长按状态
            state = STATE_LONG_PRESS;
            //没有录制权限
            if (CheckPermission.getRecordState() != CheckPermission.STATE_SUCCESS) {
                state = STATE_IDLE;
                if (captureListener != null) {
                    captureListener.recordError();
                    return;
                }
            }
            //启动按钮动画，外圆变大，内圆缩小
            startRecordAnimation(
                    buttonOutsideRadius,
                    buttonOutsideRadius + outsideAddSize,
                    buttonInsideRadius,
                    buttonInsideRadius - insideReduceSize
            );
        }
    }
}
