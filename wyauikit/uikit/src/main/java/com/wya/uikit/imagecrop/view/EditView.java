package com.wya.uikit.imagecrop.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.wya.uikit.imagecrop.core.EditImage;
import com.wya.uikit.imagecrop.core.EditMode;
import com.wya.uikit.imagecrop.core.EditPath;
import com.wya.uikit.imagecrop.core.EditText;
import com.wya.uikit.imagecrop.core.anim.CropHomingAnimator;
import com.wya.uikit.imagecrop.core.homing.CropHoming;
import com.wya.uikit.imagecrop.core.sticker.Sticker;
import com.wya.uikit.imagecrop.core.sticker.StickerPortrait;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class EditView extends FrameLayout implements Runnable, ScaleGestureDetector.OnScaleGestureListener,
        ValueAnimator.AnimatorUpdateListener, StickerPortrait.Callback, Animator.AnimatorListener {
    
    private static final String TAG = "IMGView";
    private static final boolean DEBUG = true;
    private EditMode mPreMode = EditMode.NONE;
    private EditImage mImage = new EditImage();
    private GestureDetector mGDetector;
    private ScaleGestureDetector mSGDetector;
    private CropHomingAnimator mHomingAnimator;
    private Pen mPen = new Pen();
    private int mPointerCount = 0;
    private Paint mDoodlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMosaicPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    {
        // 涂鸦画刷
        mDoodlePaint.setStyle(Paint.Style.STROKE);
        mDoodlePaint.setStrokeWidth(EditPath.BASE_DOODLE_WIDTH);
        mDoodlePaint.setColor(Color.RED);
        mDoodlePaint.setPathEffect(new CornerPathEffect(EditPath.BASE_DOODLE_WIDTH));
        mDoodlePaint.setStrokeCap(Paint.Cap.ROUND);
        mDoodlePaint.setStrokeJoin(Paint.Join.ROUND);
        
        // 马赛克画刷
        mMosaicPaint.setStyle(Paint.Style.STROKE);
        mMosaicPaint.setStrokeWidth(EditPath.BASE_MOSAIC_WIDTH);
        mMosaicPaint.setColor(Color.BLACK);
        mMosaicPaint.setPathEffect(new CornerPathEffect(EditPath.BASE_MOSAIC_WIDTH));
        mMosaicPaint.setStrokeCap(Paint.Cap.ROUND);
        mMosaicPaint.setStrokeJoin(Paint.Join.ROUND);
    }
    
    public EditView(Context context) {
        this(context, null, 0);
    }
    
    public EditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public EditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }
    
    private void initialize(Context context) {
        mPen.setMode(mImage.getMode());
        mGDetector = new GestureDetector(context, new MoveAdapter());
        mSGDetector = new ScaleGestureDetector(context, this);
    }
    
    public void setImageBitmap(Bitmap image) {
        mImage.setBitmap(image);
        invalidate();
    }
    
    /**
     * 是否真正修正归位
     */
    boolean isHoming() {
        return mHomingAnimator != null
                && mHomingAnimator.isRunning();
    }
    
    private void onHoming() {
        invalidate();
        stopHoming();
        startHoming(mImage.getStartHoming(getScrollX(), getScrollY()),
                mImage.getEndHoming(getScrollX(), getScrollY()));
    }
    
    private void startHoming(CropHoming sHoming, CropHoming eHoming) {
        if (mHomingAnimator == null) {
            mHomingAnimator = new CropHomingAnimator();
            mHomingAnimator.addUpdateListener(this);
            mHomingAnimator.addListener(this);
        }
        mHomingAnimator.setHomingValues(sHoming, eHoming);
        mHomingAnimator.start();
    }
    
    private void stopHoming() {
        if (mHomingAnimator != null) {
            mHomingAnimator.cancel();
        }
    }
    
    public void doRotate() {
        if (!isHoming()) {
            mImage.rotate(-90);
            onHoming();
        }
    }
    
    public void resetClip() {
        mImage.resetClip();
        onHoming();
    }
    
    public void doClip() {
        mImage.clip(getScrollX(), getScrollY());
        setMode(mPreMode);
        onHoming();
    }
    
    public void cancelClip() {
        mImage.toBackupClip();
        setMode(mPreMode);
    }
    
    public void setPenColor(int color) {
        mPen.setColor(color);
    }
    
    public boolean isDoodleEmpty() {
        return mImage.isDoodleEmpty();
    }
    
    public void undoDoodle() {
        mImage.undoDoodle();
        invalidate();
    }
    
    public boolean isMosaicEmpty() {
        return mImage.isMosaicEmpty();
    }
    
    public void undoMosaic() {
        mImage.undoMosaic();
        invalidate();
    }
    
    public EditMode getMode() {
        return mImage.getMode();
    }
    
    public void setMode(EditMode mode) {
        // 保存现在的编辑模式
        mPreMode = mImage.getMode();
        
        // 设置新的编辑模式
        mImage.setMode(mode);
        mPen.setMode(mode);
        
        // 矫正区域
        onHoming();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }
    
    private void onDrawImages(Canvas canvas) {
        canvas.save();
        
        // clip 中心旋转
        RectF clipFrame = mImage.getClipFrame();
        canvas.rotate(mImage.getRotate(), clipFrame.centerX(), clipFrame.centerY());
        
        // 图片
        mImage.onDrawImage(canvas);
        
        // 马赛克
        if (!mImage.isMosaicEmpty() || (mImage.getMode() == EditMode.MOSAIC && !mPen.isEmpty())) {
            int count = mImage.onDrawMosaicsPath(canvas);
            if (mImage.getMode() == EditMode.MOSAIC && !mPen.isEmpty()) {
                mDoodlePaint.setStrokeWidth(EditPath.BASE_MOSAIC_WIDTH);
                canvas.save();
                RectF frame = mImage.getClipFrame();
                canvas.rotate(-mImage.getRotate(), frame.centerX(), frame.centerY());
                canvas.translate(getScrollX(), getScrollY());
                canvas.drawPath(mPen.getPath(), mDoodlePaint);
                canvas.restore();
            }
            mImage.onDrawMosaic(canvas, count);
        }
        
        // 涂鸦
        mImage.onDrawDoodles(canvas);
        if (mImage.getMode() == EditMode.DOODLE && !mPen.isEmpty()) {
            mDoodlePaint.setColor(mPen.getColor());
            mDoodlePaint.setStrokeWidth(EditPath.BASE_DOODLE_WIDTH * mImage.getScale());
            canvas.save();
            RectF frame = mImage.getClipFrame();
            canvas.rotate(-mImage.getRotate(), frame.centerX(), frame.centerY());
            canvas.translate(getScrollX(), getScrollY());
            canvas.drawPath(mPen.getPath(), mDoodlePaint);
            canvas.restore();
        }
        
        if (mImage.isFreezing()) {
            // 文字贴片
            mImage.onDrawStickers(canvas);
        }
        
        mImage.onDrawShade(canvas);
        
        canvas.restore();
        
        if (!mImage.isFreezing()) {
            // 文字贴片
            mImage.onDrawStickerClip(canvas);
            mImage.onDrawStickers(canvas);
        }
        
        // 裁剪
        if (mImage.getMode() == EditMode.CLIP) {
            canvas.save();
            canvas.translate(getScrollX(), getScrollY());
            mImage.onDrawClip(canvas, getScrollX(), getScrollY());
            canvas.restore();
        }
    }
    
    public Bitmap saveBitmap() {
        mImage.stickAll();
        
        float scale = 1f / mImage.getScale();
        
        RectF frame = new RectF(mImage.getClipFrame());
        
        // 旋转基画布
        Matrix m = new Matrix();
        m.setRotate(mImage.getRotate(), frame.centerX(), frame.centerY());
        m.mapRect(frame);
        
        // 缩放基画布
        m.setScale(scale, scale, frame.left, frame.top);
        m.mapRect(frame);
        
        Bitmap bitmap = Bitmap.createBitmap(Math.round(frame.width()),
                Math.round(frame.height()), Bitmap.Config.ARGB_8888);
        
        Canvas canvas = new Canvas(bitmap);
        
        // 平移到基画布原点&缩放到原尺寸
        canvas.translate(-frame.left, -frame.top);
        canvas.scale(scale, scale, frame.left, frame.top);
        
        onDrawImages(canvas);
        
        return bitmap;
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mImage.onWindowChanged(right - left, bottom - top);
        }
    }
    
    public <V extends View & Sticker> void addStickerView(V stickerView, LayoutParams params) {
        if (stickerView != null) {
    
            addView(stickerView, params);
    
            stickerView.registerCallback(this);
            mImage.addSticker(stickerView);
        }
    }
    
    public void addStickerText(EditText text) {
        BaseStickerTextView textView = new BaseStickerTextView(getContext());
        
        textView.setText(text);
        
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        
        // Center of the drawing window.
        layoutParams.gravity = Gravity.CENTER;
        
        textView.setX(getScrollX());
        textView.setY(getScrollY());
        
        addStickerView(textView, layoutParams);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            return onInterceptTouch(ev) || super.onInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }
    
    boolean onInterceptTouch(MotionEvent event) {
        if (isHoming()) {
            stopHoming();
            return true;
        } else if (mImage.getMode() == EditMode.CLIP) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                postDelayed(this, 500);
                break;
            default:
                break;
        }
        return onTouch(event);
    }
    
    boolean onTouch(MotionEvent event) {
        
        if (isHoming()) {
            // Homing
            return false;
        }
        
        mPointerCount = event.getPointerCount();
        
        boolean handled = mSGDetector.onTouchEvent(event);
        
        EditMode mode = mImage.getMode();
        
        if (mode == EditMode.NONE || mode == EditMode.CLIP) {
            handled |= onTouchNONE(event);
        } else if (mPointerCount > 1) {
            onPathDone();
            handled |= onTouchNONE(event);
        } else {
            handled |= onTouchPath(event);
        }
        
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mImage.onTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mImage.onTouchUp(getScrollX(), getScrollY());
                onHoming();
                break;
            default:
                break;
        }
        
        return handled;
    }
    
    private boolean onTouchNONE(MotionEvent event) {
        return mGDetector.onTouchEvent(event);
    }
    
    private boolean onTouchPath(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                return onPathBegin(event);
            case MotionEvent.ACTION_MOVE:
                return onPathMove(event);
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return mPen.isIdentity(event.getPointerId(0)) && onPathDone();
            default:
                break;
        }
        return false;
    }
    
    private boolean onPathBegin(MotionEvent event) {
        mPen.reset(event.getX(), event.getY());
        mPen.setIdentity(event.getPointerId(0));
        return true;
    }
    
    private boolean onPathMove(MotionEvent event) {
        if (mPen.isIdentity(event.getPointerId(0))) {
            mPen.lineTo(event.getX(), event.getY());
            invalidate();
            return true;
        }
        return false;
    }
    
    private boolean onPathDone() {
        if (mPen.isEmpty()) {
            return false;
        }
        mImage.addPath(mPen.toPath(), getScrollX(), getScrollY());
        mPen.reset();
        invalidate();
        return true;
    }
    
    @Override
    public void run() {
        // 稳定触发
        if (!onSteady()) {
            postDelayed(this, 500);
        }
    }
    
    boolean onSteady() {
        if (DEBUG) {
            Log.d(TAG, "onSteady: isHoming=" + isHoming());
        }
        if (!isHoming()) {
            mImage.onSteady(getScrollX(), getScrollY());
            onHoming();
            return true;
        }
        return false;
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
        mImage.release();
    }
    
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (mPointerCount > 1) {
            mImage.onScale(detector.getScaleFactor(),
                    getScrollX() + detector.getFocusX(),
                    getScrollY() + detector.getFocusY());
            invalidate();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        if (mPointerCount > 1) {
            mImage.onScaleBegin();
            return true;
        }
        return false;
    }
    
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        mImage.onScaleEnd();
    }
    
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mImage.onHoming(animation.getAnimatedFraction());
        toApplyHoming((CropHoming) animation.getAnimatedValue());
    }
    
    private void toApplyHoming(CropHoming homing) {
        mImage.setScale(homing.scale);
        mImage.setRotate(homing.rotate);
        if (!onScrollTo(Math.round(homing.x), Math.round(homing.y))) {
            invalidate();
        }
    }
    
    private boolean onScrollTo(int x, int y) {
        if (getScrollX() != x || getScrollY() != y) {
            scrollTo(x, y);
            return true;
        }
        return false;
    }
    
    @Override
    public <V extends View & Sticker> void onDismiss(V stickerView) {
        mImage.onDismiss(stickerView);
        invalidate();
    }
    
    @Override
    public <V extends View & Sticker> void onShowing(V stickerView) {
        mImage.onShowing(stickerView);
        invalidate();
    }
    
    @Override
    public <V extends View & Sticker> boolean onRemove(V stickerView) {
        if (mImage != null) {
            mImage.onRemoveSticker(stickerView);
        }
        stickerView.unregisterCallback(this);
        ViewParent parent = stickerView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(stickerView);
        }
        return true;
    }
    
    @Override
    public void onAnimationStart(Animator animation) {
        if (DEBUG) {
            Log.d(TAG, "onAnimationStart");
        }
        mImage.onHomingStart(mHomingAnimator.isRotate());
    }
    
    @Override
    public void onAnimationEnd(Animator animation) {
        if (DEBUG) {
            Log.d(TAG, "onAnimationEnd");
        }
        if (mImage.onHomingEnd(getScrollX(), getScrollY(), mHomingAnimator.isRotate())) {
            toApplyHoming(mImage.clip(getScrollX(), getScrollY()));
        }
    }
    
    @Override
    public void onAnimationCancel(Animator animation) {
        if (DEBUG) {
            Log.d(TAG, "onAnimationCancel");
        }
        mImage.onHomingCancel(mHomingAnimator.isRotate());
    }
    
    @Override
    public void onAnimationRepeat(Animator animation) {
        // empty implementation.
    }
    
    private boolean onScroll(float dx, float dy) {
        CropHoming homing = mImage.onScroll(getScrollX(), getScrollY(), -dx, -dy);
        if (homing != null) {
            toApplyHoming(homing);
            return true;
        }
        return onScrollTo(getScrollX() + Math.round(dx), getScrollY() + Math.round(dy));
    }
    
    private static class Pen extends EditPath {
        
        private int identity = Integer.MIN_VALUE;
        
        void reset() {
            path.reset();
            identity = Integer.MIN_VALUE;
        }
        
        void reset(float x, float y) {
            path.reset();
            path.moveTo(x, y);
            identity = Integer.MIN_VALUE;
        }
        
        void setIdentity(int identity) {
            this.identity = identity;
        }
        
        boolean isIdentity(int identity) {
            return this.identity == identity;
        }
        
        void lineTo(float x, float y) {
            path.lineTo(x, y);
        }
        
        boolean isEmpty() {
            return path.isEmpty();
        }
        
        EditPath toPath() {
            return new EditPath(new Path(path), getMode(), getColor(), getWidth());
        }
    }
    
    private class MoveAdapter extends GestureDetector.SimpleOnGestureListener {
        
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return EditView.this.onScroll(distanceX, distanceY);
        }
        
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
