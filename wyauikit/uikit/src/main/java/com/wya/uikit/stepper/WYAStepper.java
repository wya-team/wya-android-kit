package com.wya.uikit.stepper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wya.uikit.R;

/**
 * @date: 2018/12/3 9:32
 * @author: Chunjiang Mao
 * @classname:  WYAStepper
 * @describe: 数字选择
 */

public class WYAStepper extends LinearLayout {
    /**
     * 目前的值
     */
    private int value;
    /**
     * 最大值
     */
    private int maxNum;
    /**
     * 最小值
     */
    private int minNum;
    private EditText stepperEtNum;

    /**
     * 减号
     */
    private Drawable reduceDrawable = null;
    private Drawable reduceDrawablePress = null;
    private Drawable reduceDisableDrawablePress = null;
    private ImageView stepperImgReduce;

    /**
     * 加号
     */
    private Drawable addDrawable = null;
    private Drawable addDrawablePress = null;
    private Drawable addDisableDrawablePress = null;
    private ImageView stepperImgAdd;

    /**
     * 对输入值进行监听
     */
    private String inputBefore;

    public WYAStepper(Context context) {
        this(context, null);
    }

    public WYAStepper(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.wya_stepper, this, true);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WYAStepper);
        if (a != null) {
            //设置减号背景图片，若backColor与backGroundDrawable同时存在，则backGroundDrawable将覆盖backColor
            reduceDrawable = a.getDrawable(R.styleable.WYAStepper_reduceDrawable);
            setReduceBackgroundDrawable(reduceDrawable);

            //记录减号按钮被按下时的背景图片
            reduceDrawablePress = a.getDrawable(R.styleable.WYAStepper_reduceDrawablePress);

            //记录减号按钮无法点击时的背景图片
            reduceDisableDrawablePress = a.getDrawable(R.styleable.WYAStepper_reduceDisableDrawablePress);


            //设置加号背景图片，若backColor与backGroundDrawable同时存在，则backGroundDrawable将覆盖backColor
            addDrawable = a.getDrawable(R.styleable.WYAStepper_addDrawable);
            setAddBackgroundDrawable(addDrawable);

            //记录加号按钮被按下时的背景图片
            addDrawablePress = a.getDrawable(R.styleable.WYAStepper_addDrawablePress);
            //记录加号按钮无法点击时的背景图片
            addDisableDrawablePress = a.getDrawable(R.styleable.WYAStepper_addDisableDrawablePress);

            value = a.getInt(R.styleable.WYAStepper_value, 0);
            maxNum = a.getInt(R.styleable.WYAStepper_maxNum, 0);
            minNum = a.getInt(R.styleable.WYAStepper_minNum, 0);

            setWidth(maxNum);

            setValue(value);
            stepperEtNum.setCursorVisible(false);
            a.recycle();
        }

        stepperImgReduce.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                //根据touch事件设置按下抬起的样式
                return setReduceTouchStyle(event.getAction());
            }
        });

        stepperImgReduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reduce();
            }
        });

        stepperImgAdd.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                //根据touch事件设置按下抬起的样式
                return setAddTouchStyle(event.getAction());
            }
        });

        stepperImgAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        stepperEtNum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stepperEtNum.setCursorVisible(true);
            }
        });

        setValueListener();
    }

    private void setWidth(int maxNum) {
        stepperEtNum.setWidth((maxNum + "").length() * dip2px(getContext(), 10));
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public int dip2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    /**
     * 设置输入监听
     */
    private void setValueListener() {
        stepperEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if ("".equals(s.toString())) {
                    inputBefore = minNum + "";
                } else {
                    inputBefore = s.toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString())) {
                    int nowNum = Integer.valueOf(s.toString()).intValue();
                    if (nowNum > maxNum) {
                        Toast.makeText(getContext(), "输入的值太大", Toast.LENGTH_SHORT);
                        setValue(Integer.valueOf(inputBefore).intValue());
                        stepperEtNum.setCursorVisible(false);
                    } else if (nowNum < minNum) {
                        Toast.makeText(getContext(), "输入的值太小", Toast.LENGTH_SHORT);
                        setValue(Integer.valueOf(inputBefore).intValue());
                        stepperEtNum.setCursorVisible(false);
                    } else {
                        value = nowNum;
                    }
                } else {
                    value = minNum;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 设置当前值
     *
     * @param value
     */
    private void setValue(int value) {
        stepperEtNum.setText(value + "");
        stepperEtNum.setHint(minNum + "");
        if (value >= maxNum) {
            if (addDisableDrawablePress == null) {
                stepperImgAdd.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus_disable));
            } else {
                stepperImgAdd.setBackgroundDrawable(addDisableDrawablePress);
            }
            stepperImgAdd.setEnabled(false);
        } else {
            if (addDrawable == null) {
                stepperImgAdd.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus));
            } else {
                stepperImgAdd.setBackgroundDrawable(addDrawable);
            }
            stepperImgAdd.setBackgroundDrawable(addDrawable);
            stepperImgAdd.setEnabled(true);
        }
        if (value <= minNum) {
            if (reduceDisableDrawablePress == null) {
                stepperImgReduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus_disable));
            } else {
                stepperImgReduce.setBackgroundDrawable(reduceDisableDrawablePress);
            }
            stepperImgReduce.setEnabled(false);
        } else {
            if (reduceDrawable == null) {
                stepperImgReduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus));
            } else {
                stepperImgReduce.setBackgroundDrawable(reduceDrawable);
            }
            stepperImgReduce.setEnabled(true);
        }
    }


    /**
     * 根据按下或者抬起来改变背景
     *
     * @param state
     * @return isCost
     * 为解决onTouch和onClick冲突的问题
     * 根据事件分发机制，如果onTouch返回true，则不响应onClick事件
     * 因此采用isCost标识位，当用户设置了onClickListener则onTouch返回false
     */
    private boolean setReduceTouchStyle(int state) {
        if (state == MotionEvent.ACTION_DOWN) {
            setReduceBackgroundDrawablePress(reduceDrawablePress);
        }
        if (state == MotionEvent.ACTION_UP) {
            setReduceBackgroundDrawable(reduceDrawable);
        }
        return false;
    }

    /**
     * 根据按下或者抬起来改变背景
     *
     * @param state
     * @return isCost
     * 为解决onTouch和onClick冲突的问题
     * 根据事件分发机制，如果onTouch返回true，则不响应onClick事件
     * 因此采用isCost标识位，当用户设置了onClickListener则onTouch返回false
     */
    private boolean setAddTouchStyle(int state) {
        if (state == MotionEvent.ACTION_DOWN) {
            setAddBackgroundDrawablePress(addDrawablePress);
        }
        if (state == MotionEvent.ACTION_UP) {
            setAddBackgroundDrawable(addDrawable);
        }
        return false;
    }

    /**
     * 设置减号按钮默认背景图片
     *
     * @param reduceDrawable
     */
    private void setReduceBackgroundDrawable(Drawable reduceDrawable) {
        this.reduceDrawable = reduceDrawable;
        if (reduceDrawable != null) {
            stepperImgReduce.setBackgroundDrawable(reduceDrawable);
        } else {
            stepperImgReduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus));
        }
    }

    /**
     * 设置减号按钮点击图片
     *
     * @param reduceDrawablePress
     */
    private void setReduceBackgroundDrawablePress(Drawable reduceDrawablePress) {
        this.reduceDrawablePress = reduceDrawablePress;
        if (reduceDrawablePress != null) {
            stepperImgReduce.setBackgroundDrawable(reduceDrawablePress);
        } else {
            stepperImgReduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus_selected));
        }
    }

    /**
     * 设置加号按钮默认背景图片
     *
     * @param addDrawable
     */
    private void setAddBackgroundDrawable(Drawable addDrawable) {
        this.addDrawable = addDrawable;
        if (reduceDrawable != null) {
            stepperImgAdd.setBackgroundDrawable(addDrawable);
        } else {
            stepperImgAdd.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus));
        }
    }

    /**
     * 设置加号按钮点击图片
     *
     * @param reduceDrawablePress
     */
    private void setAddBackgroundDrawablePress(Drawable reduceDrawablePress) {
        this.addDrawablePress = reduceDrawablePress;
        if (reduceDrawablePress != null) {
            stepperImgAdd.setBackgroundDrawable(reduceDrawablePress);
        } else {
            stepperImgAdd.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus_selected));
        }
    }


    /**
     * 加一
     */
    private void add() {
        if (value < maxNum) {
            value++;
        }
        setValue(value);
        stepperEtNum.setCursorVisible(false);
    }

    /**
     * 减一
     */
    private void reduce() {
        if (value > minNum) {
            value--;
        }
        setValue(value);
        stepperEtNum.setCursorVisible(false);
    }


    /**
     * 获取当前值
     *
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取最大值
     *
     * @return
     */
    public int getMaxNum() {
        return maxNum;
    }

    /**
     * 设置最大值
     *
     * @param maxNum
     */
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public int getMinNum() {
        return minNum;
    }

    /**
     * 设置最小值
     *
     * @param minNum
     */
    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }


    private void initView() {
        stepperImgReduce = findViewById(R.id.stepper_img_reduce);
        stepperImgAdd = findViewById(R.id.stepper_img_add);
        stepperEtNum = findViewById(R.id.stepper_et_num);
    }


}
