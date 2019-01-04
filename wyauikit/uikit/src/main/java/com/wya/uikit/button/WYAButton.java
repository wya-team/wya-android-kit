package com.wya.uikit.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wya.uikit.R;

/**
 * @date: 2018/11/28 13:53
 * @author: Chunjiang Mao
 * @classname:  WYAButton
 * @describe: 自定义button
 */

@SuppressLint("AppCompatCustomView")
public class WYAButton extends Button {
    /**
     * 按钮的背景色
     */
    private int backColor = 0;
    /**
     * 按钮被按下时的背景色
     */
    private int backColorPress = 0;
    /**
     * 按钮的背景图片
     */
    private Drawable backGroundDrawable = null;
    /**
     * 按钮被按下时显示的背景图片
     */
    private Drawable backGroundDrawablePress = null;
    /**
     * 按钮文字的颜色
     */
    private ColorStateList textColor = null;
    /**
     * 按钮被按下时文字的颜色
     */
    private ColorStateList textColorPress = null;
    private GradientDrawable gradientDrawable = null;
    /**
     * 是否设置圆角或者圆形等样式
     */
    private boolean fillet = false;

    /**
     * 是否可以点击
     */
    private boolean enabled = false;
    /**
     * 标示onTouch方法的返回值，用来解决onClick和onTouch冲突问题
     */
    private boolean isCost = true;


    public WYAButton(Context context) {
        this(context, null);
    }

    public WYAButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WYAButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WYAButton, defStyleAttr, 0);

        if (a != null) {
            //设置背景色
            ColorStateList colorList = a.getColorStateList(R.styleable.WYAButton_backColor);
            if (colorList != null) {
                backColor = colorList.getColorForState(getDrawableState(), 0);
                if (backColor != 0) {
                    setBackgroundColor(backColor);
                }
            }
            //记录按钮被按下时的背景色
            ColorStateList colorListPress = a.getColorStateList(R.styleable.WYAButton_backColorPress);
            if (colorListPress != null) {
                backColorPress = colorListPress.getColorForState(getDrawableState(), 0);
            }
            //记录按钮被按下时的背景图片
            backGroundDrawablePress = a.getDrawable(R.styleable.WYAButton_backGroundImagePress);
            //设置文字的颜色
            textColor = a.getColorStateList(R.styleable.WYAButton_textColor);
            if (textColor != null) {
                setTextColor(textColor);
            }
            //记录按钮被按下时文字的颜色
            textColorPress = a.getColorStateList(R.styleable.WYAButton_textColorPress);
            //设置圆角或圆形等样式的背景色
            fillet = a.getBoolean(R.styleable.WYAButton_fillet, false);
            if (fillet) {
                getGradientDrawable();
                if (backColor != 0) {
                    gradientDrawable.setColor(backColor);
                    setBackgroundDrawable(gradientDrawable);
                }
            }

            //设置是否可以点击
            enabled = a.getBoolean(R.styleable.WYAButton_enabled, true);
            if (enabled) {
                setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View arg0, MotionEvent event) {
                        //根据touch事件设置按下抬起的样式
                        return setTouchStyle(event.getAction());
                    }
                });


            } else {
                setButtonEnabled(enabled);
            }
            //设置圆角矩形的角度，fillet为true时才生效
            float radius = a.getFloat(R.styleable.WYAButton_wya_button_radius, 0);
            if (fillet && radius != 0) {
                setRadius(radius);
            }

            //设置按钮形状，fillet为true时才生效
            int shape = a.getInteger(R.styleable.WYAButton_shape, 0);
            if (fillet && shape != 0) {
                setShape(shape);
            }
            //设置背景图片，若backColor与backGroundDrawable同时存在，则backGroundDrawable将覆盖backColor
            backGroundDrawable = a.getDrawable(R.styleable.WYAButton_backGroundImage);
            if (backGroundDrawable != null) {
                setBackgroundDrawable(backGroundDrawable);
            }
            this.setGravity(Gravity.CENTER);

            a.recycle();
        }
    }

    /**
     * 设置是否可以点击
     *
     * @param enabled
     */
    private void setButtonEnabled(boolean enabled) {
        if (enabled) {
            setBackColor(Color.parseColor("#108de7"));
            setTextColor(textColor);
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent event) {
                    //根据touch事件设置按下抬起的样式
                    return setTouchStyle(event.getAction());
                }
            });

        } else {
            setBackColor(Color.parseColor("#DEDEDE"));
            setTextColor(Color.parseColor("#909090"));
            setEnabled(enabled);
        }
    }

    /**
     * 根据按下或者抬起来改变背景和文字样式
     *
     * @param state
     * @return isCost
     * 为解决onTouch和onClick冲突的问题
     * 根据事件分发机制，如果onTouch返回true，则不响应onClick事件
     * 因此采用isCost标识位，当用户设置了onClickListener则onTouch返回false
     */
    private boolean setTouchStyle(int state) {
        if (state == MotionEvent.ACTION_DOWN) {
            if (backColorPress != 0) {
                if (fillet) {
                    gradientDrawable.setColor(backColorPress);
                    setBackgroundDrawable(gradientDrawable);
                } else {
                    setBackgroundColor(backColorPress);
                }
            }
            if (backGroundDrawablePress != null) {
                setBackgroundDrawable(backGroundDrawablePress);
            }
            if (textColorPress != null) {
                setTextColor(textColorPress);
            }
        }
        if (state == MotionEvent.ACTION_UP) {
            if (backColor != 0) {
                if (fillet) {
                    gradientDrawable.setColor(backColor);
                    setBackgroundDrawable(gradientDrawable);
                } else {
                    setBackgroundColor(backColor);
                }
            }
            if (backGroundDrawable != null) {
                setBackgroundDrawable(backGroundDrawable);
            }
            if (textColor != null) {
                setTextColor(textColor);
            }
        }
        return isCost;
    }

    /**
     * 重写setOnClickListener方法，解决onTouch和onClick冲突问题
     *
     * @param l
     */
    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        isCost = false;
    }

    /**
     * 设置按钮的背景色
     *
     * @param backColor
     */
    public void setBackColor(int backColor) {
        this.backColor = backColor;
        if (fillet) {
            gradientDrawable.setColor(backColor);
            setBackgroundDrawable(gradientDrawable);
        } else {
            setBackgroundColor(backColor);
        }
    }

    /**
     * 设置按钮被按下时的背景色
     *
     * @param backColorPress
     */
    public void setBackColorPress(int backColorPress) {
        this.backColorPress = backColorPress;
    }

    /**
     * 设置按钮的背景图片
     *
     * @param backGroundDrawable
     */
    public void setBackGroundDrawable(Drawable backGroundDrawable) {
        this.backGroundDrawable = backGroundDrawable;
        setBackgroundDrawable(backGroundDrawable);
    }

    /**
     * 设置按钮被按下时的背景图片
     *
     * @param backGroundDrawablePress
     */
    public void setBackGroundDrawablePress(Drawable backGroundDrawablePress) {
        this.backGroundDrawablePress = backGroundDrawablePress;
    }

    /**
     * 设置文字的颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        if (textColor == 0) return;
        //此处应加super关键字，调用父类的setTextColor方法，否则会造成递归导致内存溢出
        super.setTextColor(ColorStateList.valueOf(textColor));
    }

    /**
     * 设置按钮被按下时文字的颜色
     *
     * @param textColorPress
     */
    public void setTextColorPress(int textColorPress) {
        if (textColorPress == 0) return;
        this.textColorPress = ColorStateList.valueOf(textColorPress);
    }

    /**
     * 设置按钮是否设置圆角或者圆形等样式
     *
     * @param fillet
     */
    public void setFillet(boolean fillet) {
        this.fillet = fillet;
        getGradientDrawable();
    }

    /**
     * 设置圆角按钮的角度
     *
     * @param radius
     */
    public void setRadius(float radius) {
        if (!fillet) return;
        getGradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        setBackgroundDrawable(gradientDrawable);
    }

    /**
     * 设置按钮的形状
     *
     * @param shape
     */
    public void setShape(int shape) {
        if (!fillet) return;
        getGradientDrawable();
        gradientDrawable.setShape(shape);
        setBackgroundDrawable(gradientDrawable);
    }

    private void getGradientDrawable() {
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    /**
     * 设置按钮大小
     *
     * @param type   1正常， 2小， 3自定义
     * @param height 自定义高 dp单位
     * @param width  自定义宽 dp单位
     */
    public void setSize(Context context, int type, int height, int width) {
        ViewGroup.LayoutParams wya_button_params = this.getLayoutParams();
        if (type == 1) {
            wya_button_params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wya_button_params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (type == 2) {
            wya_button_params.height = dip2px(context, 36);
            wya_button_params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (type == 3) {
            wya_button_params.height = dip2px(context, height);
            wya_button_params.width = dip2px(context, width);
        }
        this.setLayoutParams(wya_button_params);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @SuppressLint("NewApi")
    public void setLoading(Context context, Drawable drawableLeft, boolean showText) {
        if (!showText) {
            setText("");
        } else {
            setText("按钮");
        }
        if (drawableLeft != null) {
            setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
            drawableLeft.setBounds(0, 0, dip2px(context, 24), dip2px(context, 24));
            setCompoundDrawables(drawableLeft, null, null, null);
        } else {
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
            setCompoundDrawables(null, null, null, null);
        }
    }
}
