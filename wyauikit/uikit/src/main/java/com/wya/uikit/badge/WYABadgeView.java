package com.wya.uikit.badge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.wya.uikit.R;
import com.wya.uikit.slideview.Utils;

public class WYABadgeView extends View implements IBadgeView {
    
    // text
    protected TextPaint mTextPaint;
    protected RectF mTextRect;
    protected int mTextColor;
    protected int mBadgeNum;
    private int mOmitNum = 99;
    private String mOmitText = "···";
    protected String mBadgeText;
    protected float mTextSize;
    protected Paint.FontMetrics mTextFontMetrics;
    protected boolean isOmitMode;
    
    // background
    protected Paint mBackgroundPaint;
    protected RectF mBackgroundRect;
    protected int mBackgroundColor;
    protected Drawable mBackgroundDrawable;
    
    // bitmap
    protected Drawable mBadgeDrawable;
    private int mBadgeBitmapSize;
    
    // border
    protected Paint mBorderPaint;
    protected int mBgBorderColor;
    protected float mBorderWidth;
    
    private boolean mIsAttach = true;
    protected View mTargetView;
    
    protected int mWidth;
    protected int mHeight;
    protected float mPadding;
    protected PointF mCenter;
    protected Builder.Gravity mGravity;
    
    public WYABadgeView(Context context) {
        this(context, null);
    }
    
    private WYABadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    private WYABadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 取消硬件加速
        init();
    }
    
    private void init() {
        initText();
        initBorder();
        initBackground();
        initBadgeDrawable();
        
        mCenter = new PointF();
        mGravity = new Builder.Gravity(Builder.BadgeGravity.GRAVITY_END_TOP, 0, 0);
    }
    
    private void initBadgeDrawable() {
    }
    
    private Bitmap getBitmap(int size, Drawable drawable) {
        if (null == drawable || size <= 0) {
            return null;
        }
        return Utils.drawableToBitmap(size, drawable);
    }
    
    private void initText() {
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setSubpixelText(true);
        mTextPaint.setFakeBoldText(true);
        
        mTextColor = Color.WHITE;
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); // 文本在最上层
        mTextRect = new RectF();
        
        mBadgeNum = 0;
        mBadgeText = "";
    }
    
    private void initBorder() {
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBgBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }
    
    private void initBackground() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundColor = Color.RED;
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundRect = new RectF();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (null != mBadgeDrawable) {
            drawBadgeBitmap(canvas);
        } else {
            PointF badgeCenter = getCenter();
            drawBadge(canvas, badgeCenter);
        }
    }
    
    @Override
    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeDrawable = drawable;
        invalidate();
    }
    
    @Override
    public void setBadgeBitmapSize(int size) {
        this.mBadgeBitmapSize = size;
    }
    
    private void drawBadgeBitmap(Canvas canvas) {
        Bitmap bitmap = getBitmap(mBadgeBitmapSize, getResources().getDrawable(R.drawable.sale_badge)); // TODO: 2018/12/24 ZCQ TEST
        if (null != bitmap) {
            canvas.drawBitmap(bitmap, mWidth - getPaddingRight() - mBadgeBitmapSize, getTop() / 2, null);
        }
    }
    
    private void drawBadge(Canvas canvas, PointF center) {
        drawBackground(canvas, center);
        drawText(canvas, center);
    }
    
    private void drawBackground(Canvas canvas, PointF center) {
        if (null != mBackgroundDrawable) {
            drawDrawable(canvas);
        } else {
            drawBorder(canvas, center);
        }
    }
    
    /**
     * 设置背景Drawable时调用
     *
     * @param canvas :
     */
    private void drawDrawable(Canvas canvas) {
        mBackgroundDrawable.setBounds((int) mBackgroundRect.left, (int) mBackgroundRect.top, (int) mBackgroundRect.right, (int) mBackgroundRect.bottom);
        mBackgroundDrawable.draw(canvas);
        canvas.drawRect(mBackgroundRect, mBackgroundPaint);
    }
    
    private void drawBorder(Canvas canvas, PointF center) {
        if (TextUtils.isEmpty(mBadgeText) || mBadgeText.length() == 1) {
            drawCircelBorder(canvas, center);
        } else {
            drawRoundRectBorder(canvas, center);
        }
    }
    
    /**
     * 绘制circle
     *
     * @param canvas :
     * @param center :
     */
    private void drawCircelBorder(Canvas canvas, PointF center) {
        float radius = mPadding;
        if (!TextUtils.isEmpty(mBadgeText) && mBadgeText.length() == 1) {
            float halfHeight = mTextRect.height() / 2f + mPadding * 0.5f;
            float halfWidth = mTextRect.width() / 2f + mPadding * 0.5f;
            radius = mTextRect.height() > mTextRect.width() ? halfHeight : halfWidth;
        }
        mBackgroundRect.left = center.x - (int) radius;
        mBackgroundRect.top = center.y - (int) radius;
        mBackgroundRect.right = center.x + (int) radius;
        mBackgroundRect.bottom = center.y + (int) radius;
        canvas.drawCircle(center.x, center.y, radius, mBackgroundPaint);
        if (mBgBorderColor != 0 && mBorderWidth > 0) {
            canvas.drawCircle(center.x, center.y, radius, mBorderPaint);
        }
    }
    
    /**
     * 绘制roundRect
     *
     * @param canvas :
     * @param center :
     */
    private void drawRoundRectBorder(Canvas canvas, PointF center) {
        mBackgroundRect.left = center.x - (mTextRect.width() / 2f + mPadding);
        mBackgroundRect.top = center.y - (mTextRect.height() / 2f + mPadding * 0.5f);
        mBackgroundRect.right = center.x + (mTextRect.width() / 2f + mPadding);
        mBackgroundRect.bottom = center.y + (mTextRect.height() / 2f + mPadding * 0.5f);
        float radius = mBackgroundRect.height() / 2f;
        canvas.drawRoundRect(mBackgroundRect, radius, radius, mBackgroundPaint);
        if (mBgBorderColor != 0 && mBorderWidth > 0) {
            canvas.drawRoundRect(mBackgroundRect, radius, radius, mBorderPaint);
        }
    }
    
    private void drawText(Canvas canvas, PointF center) {
        if (!TextUtils.isEmpty(mBadgeText)) {
            float x = center.x;
            float y = (mBackgroundRect.bottom + mBackgroundRect.top - mTextFontMetrics.bottom - mTextFontMetrics.top) / 2f;
            canvas.drawText(mBadgeText, x, y, mTextPaint);
        }
    }
    
    /**
     * 获取中心点
     *
     * @return : PointF（x,y）
     */
    private PointF getCenter() {
        float rectWidth = mTextRect.height() > mTextRect.width() ? mTextRect.height() : mTextRect.width();
        switch (mGravity.getGravity()) {
            case Builder.BadgeGravity.GRAVITY_START_TOP:
                mCenter.x = mGravity.xOffset + mPadding + rectWidth / 2f;
                mCenter.y = mGravity.yOffset + mPadding + mTextRect.height() / 2f;
                break;
            case Builder.BadgeGravity.GRAVITY_START_BOTTOM:
                mCenter.x = mGravity.xOffset + mPadding + rectWidth / 2f;
                mCenter.y = mHeight - (mGravity.yOffset + mPadding + mTextRect.height() / 2f);
                break;
            case Builder.BadgeGravity.GRAVITY_END_TOP:
                mCenter.x = mWidth - (mGravity.xOffset + mPadding + rectWidth / 2f);
                mCenter.y = mGravity.yOffset + mPadding + mTextRect.height() / 2f;
                break;
            case Builder.BadgeGravity.GRAVITY_END_BOTTOM:
                mCenter.x = mWidth - (mGravity.xOffset + mPadding + rectWidth / 2f);
                mCenter.y = mHeight - (mGravity.yOffset + mPadding + mTextRect.height() / 2f);
                break;
            case Builder.BadgeGravity.GRAVITY_CENTER:
                mCenter.x = mWidth / 2f;
                mCenter.y = mHeight / 2f;
                break;
            case Builder.BadgeGravity.GRAVITY_CENTER_TOP:
                mCenter.x = mWidth / 2f + mGravity.xOffset;
                mCenter.y = mGravity.yOffset + mPadding + mTextRect.height() / 2f;
                break;
            case Builder.BadgeGravity.GRAVITY_CENTER_BOTTOM:
                mCenter.x = mWidth / 2f;
                mCenter.y = mHeight - (mGravity.yOffset + mPadding + mTextRect.height() / 2f);
                break;
            case Builder.BadgeGravity.GRAVITY_CENTER_START:
                mCenter.x = mGravity.xOffset + mPadding + rectWidth / 2f;
                mCenter.y = mHeight / 2f;
                break;
            case Builder.BadgeGravity.GRAVITY_CENTER_END:
                mCenter.x = mWidth - (mGravity.xOffset + mPadding + rectWidth / 2f);
                mCenter.y = mHeight / 2f;
                break;
            default:
                break;
        }
        return mCenter;
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
    
    @Override
    public void bindToTarget(final View targetView) {
        if (targetView == null) {
            throw new IllegalStateException("bindToTarget : targetView can not be null");
        }
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        ViewParent targetParent = targetView.getParent();
        if (!(targetParent instanceof ViewGroup)) {
            throw new IllegalStateException("bindToTarget : targetView must have a parent");
        } else {
            mTargetView = targetView;
            if (targetParent instanceof BadgeContainer) {
                ((BadgeContainer) targetParent).addView(this);
            } else {
                ViewGroup targetContainer = (ViewGroup) targetParent;
                int index = targetContainer.indexOfChild(targetView);
                ViewGroup.LayoutParams targetParams = targetView.getLayoutParams();
                targetContainer.removeView(targetView);
                final BadgeContainer badgeContainer = new BadgeContainer(getContext());
                if (targetContainer instanceof RelativeLayout) {
                    badgeContainer.setId(targetView.getId());
                }
                targetContainer.addView(badgeContainer, index, targetParams);
                badgeContainer.addView(targetView);
                badgeContainer.addView(this);
            }
        }
    }
    
    private void getActivityRoot(View view) {
        if (view.getParent() != null && view.getParent() instanceof View) {
            getActivityRoot((View) view.getParent());
        }
    }
    
    @Override
    public void setOmitNum(int omitNum) {
        this.mOmitNum = omitNum;
        if (mBadgeNum > mOmitNum) {
            setBadgeNum(mBadgeNum);
        }
    }
    
    @Override
    public void update(boolean isShow) {
        setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
    
    /**
     * 设置数字类型
     *
     * @param badgeNumber :数字类型
     */
    @Override
    public void setBadgeNum(int badgeNumber) {
        mBadgeNum = badgeNumber;
        if (mBadgeNum < 0) { // < 0 不显示
            mBadgeText = "";
        } else if (mBadgeNum == 0) { // = 0 显示红点
            mBadgeText = null;
        } else if (mBadgeNum <= mOmitNum) {
            mBadgeText = String.valueOf(mBadgeNum);
        } else { // > mOmitNum 根据是否省略显示
            mBadgeText = isOmitMode ? mOmitText : String.valueOf(mBadgeNum);
        }
        measureText();
        invalidate();
    }
    
    @Override
    public void setBadgeText(String badgeText) {
        mBadgeText = badgeText;
        mBadgeNum = 0;
        measureText();
        invalidate();
    }
    
    private void measureText() {
        mTextRect.left = 0;
        mTextRect.top = 0;
        if (TextUtils.isEmpty(mBadgeText)) {  // 文本为空 显示红点
            mTextRect.right = 0;
            mTextRect.bottom = 0;
        } else {
            mTextPaint.setTextSize(mTextSize);
            mTextRect.right = mTextPaint.measureText(mBadgeText);
            mTextFontMetrics = mTextPaint.getFontMetrics();
            mTextRect.bottom = mTextFontMetrics.descent - mTextFontMetrics.ascent;
        }
    }
    
    /**
     * @param isOmitMode : 是否省略模式
     */
    @Override
    public void setOmitMode(boolean isOmitMode) {
        this.isOmitMode = isOmitMode;
        if (mBadgeNum > mOmitNum) {
            setBadgeNum(mBadgeNum);
        }
    }
    
    /**
     * @param omitText : 省略模式的文本 默认 "···"
     */
    @Override
    public void setOmitText(String omitText) {
        this.mOmitText = omitText;
        if (mBadgeNum > mOmitNum) {
            setBadgeNum(mBadgeNum);
        }
    }
    
    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); // 文本在最上层
        invalidate();
    }
    
    @Override
    public void setBackgroundDrawable(Drawable drawable) {
        if (null != drawable) {
            mBackgroundDrawable = drawable;
            invalidate();
        }
    }
    
    @Override
    public void setTextColor(int color) {
        mTextColor = color;
        invalidate();
    }
    
    @Override
    public void setTextSize(float size) {
        mTextSize = size;
        measureText();
        invalidate();
    }
    
    @Override
    public void setPadding(float padding) {
        mPadding = padding;
        invalidate();
    }
    
    @Override
    public void setGravity(Builder.Gravity gravity) {
        mGravity = gravity;
        invalidate();
        setOffset(gravity.xOffset, gravity.yOffset);
    }
    
    @Override
    public void setOffset(float offsetX, float offsetY) {
        mGravity.setxOffset(offsetX);
        mGravity.setyOffset(offsetY);
        invalidate();
    }
    
    @Override
    public void setAttach(boolean isAttach) {
        this.mIsAttach = isAttach;
    }
    
    private class BadgeContainer extends ViewGroup {
        
        @Override
        protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
            if (!(getParent() instanceof RelativeLayout)) {
                super.dispatchRestoreInstanceState(container);
            }
        }
        
        public BadgeContainer(Context context) {
            super(context);
        }
        
        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }
        
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            View targetView = null, badgeView = null;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!(child instanceof WYABadgeView)) {
                    targetView = child;
                } else {
                    badgeView = child;
                }
            }
            if (targetView == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                int widthMode = MeasureSpec.getMode(widthMeasureSpec);
                
                int widthNeeded = widthSize;
                if (!mIsAttach) {
                    if (null == mBadgeDrawable) {
                        mBackgroundRect.left = getCenter().x - (mTextRect.width() / 2f + mPadding);
                        mBackgroundRect.right = getCenter().x + (mTextRect.width() / 2f + mPadding);
                        widthNeeded = Double.valueOf(mBackgroundRect.right - mBackgroundRect.left).intValue();
                    }
                }
                
                switch (widthMode) {
                    case MeasureSpec.EXACTLY:
                        widthSize = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
                        break;
                    case MeasureSpec.AT_MOST:
                        widthSize = MeasureSpec.makeMeasureSpec(widthNeeded, MeasureSpec.AT_MOST);
                        break;
                    default:
                        widthSize = MeasureSpec.makeMeasureSpec(widthNeeded, MeasureSpec.EXACTLY);
                        break;
                }
                targetView.measure(widthSize, heightMeasureSpec);
                if (badgeView != null) {
                    badgeView.measure(MeasureSpec.makeMeasureSpec(targetView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(targetView.getMeasuredHeight(), MeasureSpec.EXACTLY));
                }
                setMeasuredDimension(targetView.getMeasuredWidth(), targetView.getMeasuredHeight());
            }
        }
    }
    
}
