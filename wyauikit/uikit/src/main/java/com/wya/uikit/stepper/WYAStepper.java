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

    private int value;//目前的值
    private int max_num;//最大值
    private int min_num;//最小值
    private EditText stepper_et_num;

    /**
     * 减号
     */
    private Drawable reduceDrawable = null;
    private Drawable reduceDrawablePress = null;
    private Drawable reduceDisableDrawablePress = null;
    private ImageView stepper_img_reduce;

    /**
     * 加号
     */
    private Drawable addDrawable = null;
    private Drawable addDrawablePress = null;
    private Drawable addDisableDrawablePress = null;
    private ImageView stepper_img_add;

    /**
     * 对输入值进行监听
     */
    private String input_before;

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
            max_num = a.getInt(R.styleable.WYAStepper_max_num, 0);
            min_num = a.getInt(R.styleable.WYAStepper_min_num, 0);

            setWidth(max_num);

            setValue(value);
            stepper_et_num.setCursorVisible(false);
            a.recycle();
        }

        stepper_img_reduce.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                //根据touch事件设置按下抬起的样式
                return setReduceTouchStyle(event.getAction());
            }
        });

        stepper_img_reduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reduce();
            }
        });

        stepper_img_add.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                //根据touch事件设置按下抬起的样式
                return setAddTouchStyle(event.getAction());
            }
        });

        stepper_img_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        stepper_et_num.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stepper_et_num.setCursorVisible(true);
            }
        });

        setValueListener();
    }

    private void setWidth(int max_num) {
        stepper_et_num.setWidth((max_num + "").length() * dip2px(getContext(), 10));
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
        stepper_et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().equals("")) {
                    input_before = min_num + "";
                } else {
                    input_before = s.toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    int now_num = Integer.valueOf(s.toString()).intValue();
                    if (now_num > max_num) {
                        Toast.makeText(getContext(), "输入的值太大", Toast.LENGTH_SHORT);
                        setValue(Integer.valueOf(input_before).intValue());
                        stepper_et_num.setCursorVisible(false);
                    } else if (now_num < min_num) {
                        Toast.makeText(getContext(), "输入的值太小", Toast.LENGTH_SHORT);
                        setValue(Integer.valueOf(input_before).intValue());
                        stepper_et_num.setCursorVisible(false);
                    } else {
                        value = now_num;
                    }
                } else {
                    value = min_num;
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
        stepper_et_num.setText(value + "");
        stepper_et_num.setHint(min_num + "");
        if (value >= max_num) {
            if (addDisableDrawablePress == null) {
                stepper_img_add.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus_disable));
            } else {
                stepper_img_add.setBackgroundDrawable(addDisableDrawablePress);
            }
            stepper_img_add.setEnabled(false);
        } else {
            if (addDrawable == null) {
                stepper_img_add.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus));
            } else {
                stepper_img_add.setBackgroundDrawable(addDrawable);
            }
            stepper_img_add.setBackgroundDrawable(addDrawable);
            stepper_img_add.setEnabled(true);
        }
        if (value <= min_num) {
            if (reduceDisableDrawablePress == null) {
                stepper_img_reduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus_disable));
            } else {
                stepper_img_reduce.setBackgroundDrawable(reduceDisableDrawablePress);
            }
            stepper_img_reduce.setEnabled(false);
        } else {
            if (reduceDrawable == null) {
                stepper_img_reduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus));
            } else {
                stepper_img_reduce.setBackgroundDrawable(reduceDrawable);
            }
            stepper_img_reduce.setEnabled(true);
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
            stepper_img_reduce.setBackgroundDrawable(reduceDrawable);
        } else {
            stepper_img_reduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus));
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
            stepper_img_reduce.setBackgroundDrawable(reduceDrawablePress);
        } else {
            stepper_img_reduce.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_minus_selected));
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
            stepper_img_add.setBackgroundDrawable(addDrawable);
        } else {
            stepper_img_add.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus));
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
            stepper_img_add.setBackgroundDrawable(reduceDrawablePress);
        } else {
            stepper_img_add.setImageDrawable(getResources().getDrawable(R.drawable.icon_stepper_plus_selected));
        }
    }


    /**
     * 加一
     */
    private void add() {
        if (value < max_num) {
            value++;
        }
        setValue(value);
        stepper_et_num.setCursorVisible(false);
    }

    /**
     * 减一
     */
    private void reduce() {
        if (value > min_num) {
            value--;
        }
        setValue(value);
        stepper_et_num.setCursorVisible(false);
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
    public int getMax_num() {
        return max_num;
    }

    /**
     * 设置最大值
     *
     * @param max_num
     */
    public void setMax_num(int max_num) {
        this.max_num = max_num;
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public int getMin_num() {
        return min_num;
    }

    /**
     * 设置最小值
     *
     * @param min_num
     */
    public void setMin_num(int min_num) {
        this.min_num = min_num;
    }


    private void initView() {
        stepper_img_reduce = findViewById(R.id.stepper_img_reduce);
        stepper_img_add = findViewById(R.id.stepper_img_add);
        stepper_et_num = findViewById(R.id.stepper_et_num);
    }


}
