package com.wya.uikit.progress;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wya.uikit.R;

import java.math.BigDecimal;

import static android.graphics.Paint.Style.STROKE;

/**
 * @date: 2018/12/4 14:10
 * @author: Chunjiang Mao
 * @classname:  WYAProgress
 * @describe: 圆形progress
 */

public class WYAProgress extends View {

    private Paint paint;
    //最外层圆环的颜色
    private int circleColor;
    //圆环进度的颜色
    private int progressCircleColor;

    //圆环的厚度，即大小
    private float circleThickness;
    //RoundProgress开始的颜色，决定RoundProgress的渐变区间
    private int progressStartColor;
    //RoundProgress结束的颜色，决定RoundProgress的渐变区间
    private int progressEndColor;
    //进度的最大值，默认是1000
    private double maxProgress = 100.0;
    //当前的进度，默认0
    private double currentProgress = 0.0;
    //最外层圆的半径
    private int outerFirstCircleRadius;
    //动画时长，默认时长为1000
    private long animationDuration;

    //颜色是否argb变化
    private boolean progressArgbColor;
    //是否圆角
    private boolean smallCircleEnable;
    private int center;
    private ArgbEvaluator mArgbEvaluator;

    public WYAProgress(Context context) {
        this(context, null);
    }

    public WYAProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WYAProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();

        mArgbEvaluator = new ArgbEvaluator();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WYAProgress);

        //获取自定义属性和默认值
        circleColor = typedArray.getColor(R.styleable.WYAProgress_circleColor, context.getResources().getColor(R.color.progress_gray));

        progressCircleColor = typedArray.getColor(R.styleable.WYAProgress_progressCircleColor, context.getResources().getColor(R.color.progress_end));

        progressStartColor = typedArray.getColor(R.styleable.WYAProgress_progressStartColor, getResources().getColor(R.color.progress_start));
        progressEndColor = typedArray.getColor(R.styleable.WYAProgress_progressEndColor, getResources().getColor(R.color.progress_end));

        circleThickness = typedArray.getDimension(R.styleable.WYAProgress_circleThickness, dip2px(context, 10));

        maxProgress = typedArray.getInt(R.styleable.WYAProgress_maxProgress, 100);
        animationDuration = typedArray.getInt(R.styleable.WYAProgress_animationDuration, 1000);


        //控制颜色渐变的开关
        progressArgbColor = typedArray.getBoolean(R.styleable.WYAProgress_progressArgbColor, false);

        smallCircleEnable = typedArray.getBoolean(R.styleable.WYAProgress_smallCircleEnable, true);


        typedArray.recycle();
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * <p>
     * （DisplayMetrics类中属性density）
     */
    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //第1步：画出最外层的圆环
        drawOuterFirstCircle(canvas);

        //第2步，画出圆弧
        drawArc(canvas);
    }


    /**
     * 绘制最外层的圆
     *
     * @param canvas 画笔
     */
    private void drawOuterFirstCircle(Canvas canvas) {
        //设置圆的颜色
        paint.setColor(circleColor);

        //设置只绘制边框
        paint.setStyle(STROKE);
        //设置圆的宽度
        paint.setStrokeWidth(circleThickness);
        //消除锯齿
        paint.setAntiAlias(true);
        //画出圆
        canvas.drawCircle(center, center, outerFirstCircleRadius, paint);
    }


    /**
     * 画出圆弧
     *
     * @param canvas 画笔
     */
    private void drawArc(Canvas canvas) {

        paint.setStrokeWidth(circleColor);
        paint.setStyle(Paint.Style.STROKE);
        if (smallCircleEnable) {
            paint.setStrokeCap(Paint.Cap.ROUND);
        } else {
            paint.setStrokeCap(Paint.Cap.BUTT);
        }
        paint.setAntiAlias(true);

        //设置圆弧宽度
        paint.setStrokeWidth(circleThickness + 1);

        RectF oval2 = new RectF(center - outerFirstCircleRadius, center - outerFirstCircleRadius, center + outerFirstCircleRadius, center + outerFirstCircleRadius);  //用于定义的圆弧的形状和大小的界限

        double progress;

        //这里画圆环的时候第二个参数为开始角度，0表示右边中线，90表示底部，-outerFirstCircleRadius
        if (currentProgress < maxProgress) {
            progress = currentProgress;
            drawArcByColor(canvas, oval2, progress);
        } else {
            progress = maxProgress;
            drawArcByColor(canvas, oval2, progress);
        }
    }

    /**
     * 根据颜色来画圆弧
     *
     * @param canvas   画笔
     * @param oval2    圆弧
     * @param progress 进度
     */
    private void drawArcByColor(Canvas canvas, RectF oval2, double progress) {
        for (int i = 0; i < progress / maxProgress * 360; i++) {
            //颜色渐变
            if (progressArgbColor) {        //如果颜色渐变， 则改变色值
                progressCircleColor = (Integer) mArgbEvaluator.evaluate(i / 360f, progressStartColor, progressEndColor);//颜色插值器（level 11以上才可以用）
            }

            paint.setColor(progressCircleColor);

            if (i < maxProgress * 360) {
                canvas.drawArc(oval2, (float) (-90 + i), 1.35f, false, paint);
            }
        }
    }


    /**
     * 当控件的宽高发生变化的时候调用的方法
     * 在这里得到控件的宽高,避免在onDraw的时候多次初始化
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取圆心的坐标，对于自身控件而言是1/2
        center = getMeasuredWidth() / 2;

        //原本是半径是等于中心点，但是由于设置画笔宽度的时候，这个宽度会根据当前的半径，往外部和内部各扩展1/2。
        //所以在设置半径是需要减去圆环宽度的一半。
        //这里减去整个圆环厚度是因为想让圆环距离本控件有左右间距，故意为之
        outerFirstCircleRadius = (int) (center - circleThickness);
    }


    /**
     * 设置进度的最大值
     *
     * @param maxProgress 最大进度
     */
    public void setMaxProgress(double maxProgress) {

        if (maxProgress < 0) {
            this.maxProgress = 0;
        }
        this.maxProgress = maxProgress;

        setAnimation(0, currentProgress);
    }


    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param currentProgress 当前进度
     */
    public void setCurrentProgress(double currentProgress) {
        setAnimation(this.currentProgress, currentProgress);
        if (currentProgress < 0) {
            this.currentProgress = 0;
        } else if (currentProgress > maxProgress) {
            this.currentProgress = maxProgress;
        } else if (currentProgress <= maxProgress) {
            this.currentProgress = currentProgress;
        }

    }

    /**
     * 为进度设置动画
     * ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
     * 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
     * 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
     * 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
     * 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
     *
     * @param start 初始值
     * @param end   结束值
     */
    private void setAnimation(double start, double end) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat((float) start, (float) end);
        progressAnimator.setDuration(animationDuration);
        progressAnimator.setTarget(start);

        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里必须经过两次转换才可以
                double temp = (float) animation.getAnimatedValue();

                BigDecimal bd = new BigDecimal(temp);

                currentProgress = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

                postInvalidate();
            }
        });
        progressAnimator.start();
    }

    /**
     * 是否argb变化
     *
     * @param progressArgbColor 是或否
     */
    public void setProgressArgbColor(boolean progressArgbColor) {
        this.progressArgbColor = progressArgbColor;
        invalidate();
    }


    /**
     * 进度条的颜色
     *
     * @param progressCircleColor 颜色值
     */
    public void setProgressCircleColor(int progressCircleColor) {
        this.progressCircleColor = progressCircleColor;
        invalidate();
    }

    /**
     * 控制RoundProgress颜色渐变
     *
     * @param progressCircleColor startColor
     */
    public void setProgressStartColor(int progressCircleColor) {
        this.progressStartColor = progressCircleColor;
        invalidate();
    }

    /**
     * 控制RoundProgress颜色渐变
     *
     * @param progressCircleColor endColor
     */
    public void setProgressEndColor(int progressCircleColor) {
        this.progressEndColor = progressCircleColor;
        invalidate();
    }

    /**
     * 设置动画时长
     *
     * @param animationDuration 时长
     */
    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;

        setAnimation(0, currentProgress);
    }


    /**
     * 圆环厚度
     *
     * @param circleThickness 厚度
     */
    public void setCircleThickness(float circleThickness) {

        this.circleThickness = circleThickness;
        invalidate();
    }

    /**
     * 设置是否圆角
     *
     * @param smallCircleEnable 装填
     */
    public void setSmallCircleEnable(boolean smallCircleEnable) {
        this.smallCircleEnable = smallCircleEnable;
        setAnimation(0, currentProgress);
    }

}

