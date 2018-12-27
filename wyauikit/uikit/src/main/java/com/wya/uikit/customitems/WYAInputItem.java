package com.wya.uikit.customitems;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.uikit.R;
import com.wya.uikit.customitems.view.MyEditText;

/**
 * 创建日期：2018/11/30 17:12
 * 作者： Mao Chunjiang
 * 文件名称：WYAInputItem
 * 类说明：常见item
 */

public class WYAInputItem extends LinearLayout {
    /**
     * 背景色
     */
    private int backColor = 0;
    private LinearLayout input_item_bg;

    /**
     * 左边图片
     */
    private Drawable leftDrawable = null;
    private ImageView img_input_item_left;

    /**
     * 右边图片
     */
    private Drawable rightDrawable = null;
    private ImageView img_input_item_right;

    /**
     * 左边标题颜色/内容
     */
    private ColorStateList leftTextColor = null;
    private String leftText = null;
    private TextView tv_input_item_left;

    /**
     * 编辑框内容
     */
    private String contentText = null;
    private String contentHint = null;
    private ColorStateList contentTextColor = null;
    private EditText et_input_item_content;
    private boolean canEdit = false;
    private int contentInputType = 0;

    /**
     * 右边编辑框内容
     */
    private String rightText = null;
    private String rightHint = null;
    private ColorStateList rightTextColor = null;
    private MyEditText et_input_item_right;
    private boolean rightCanEdit = false;

    /**
     * 底部线条分隔先颜色
     */
    private View line_view;
    private String lineViewColor = null;

    public WYAInputItem(Context context) {
        this(context, null);
    }


    public WYAInputItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.wya_input_item, this);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WYAInputItem);
        if (a != null) {
            //设置背景色
            ColorStateList colorList = a.getColorStateList(R.styleable.WYAInputItem_backColor);
            if (colorList != null) {
                backColor = colorList.getColorForState(getDrawableState(), 0);
                if (backColor != 0) {
                    setBackgroundColor(backColor);
                }
            }

            //设置背景图片，若backColor与backGroundDrawable同时存在，则backGroundDrawable将覆盖backColor
            leftDrawable = a.getDrawable(R.styleable.WYAInputItem_leftImage);
            setLeftBackgroundDrawable(leftDrawable);

            //设置右边背景图片，若backColor与backGroundDrawable同时存在，则backGroundDrawable将覆盖backColor
            rightDrawable = a.getDrawable(R.styleable.WYAInputItem_rightImage);
            setRightBackgroundDrawable(rightDrawable);


            //设置左边文字的颜色
            leftTextColor = a.getColorStateList(R.styleable.WYAInputItem_leftTvColor);
            if (leftTextColor != null) {
                setLeftTextColor(leftTextColor);
            }

            //设置左边文字内容
            leftText = a.getString(R.styleable.WYAInputItem_leftTvText);
            if (leftText != null) {
                setLeftText(leftText);
            }

            //设置中间文字内容
            contentText = a.getString(R.styleable.WYAInputItem_contentText);
            if (contentText != null) {
                setContentText(contentText);
            }

            //设置输入样式
            contentInputType = a.getInt(R.styleable.WYAInputItem_contentInputType, 0);
            setContentInputType(contentInputType);


            //设置中间文字提示内容
            contentHint = a.getString(R.styleable.WYAInputItem_contentHint);
            if (contentHint != null) {
                setContentHint(contentHint);
            }

            //设置文本是否可以编辑
            canEdit = a.getBoolean(R.styleable.WYAInputItem_canEdit, true);
            setContentEdit(canEdit);


            //设置中间文字的颜色
            contentTextColor = a.getColorStateList(R.styleable.WYAInputItem_contentTextColor);
            if (contentTextColor != null) {
                setContentTextColor(contentTextColor);
            }


            //设置右边文字内容
            rightText = a.getString(R.styleable.WYAInputItem_rightText);
            if (rightText != null) {
                setRightText(rightText);
            }

            //设置右边文字提示内容
            rightHint = a.getString(R.styleable.WYAInputItem_rightHint);
            if (rightHint != null) {
                setRightHint(rightHint);
            }

            //设置右边文本是否可以编辑
            rightCanEdit = a.getBoolean(R.styleable.WYAInputItem_rightCanEdit, true);
            setRightEdit(rightCanEdit);

            //设置右边文字的颜色
            rightTextColor = a.getColorStateList(R.styleable.WYAInputItem_rightTextColor);
            if (rightTextColor != null) {
                setRightTextColor(rightTextColor);
            }

            //设置分割线的颜色
            lineViewColor = a.getString(R.styleable.WYAInputItem_lineViewColor);
            if (lineViewColor != null) {
                setLineViewColor(lineViewColor);
            }

            a.recycle();
        }
    }


    /**
     * 设置左边的图片
     *
     * @param leftDrawable
     */
    private void setLeftBackgroundDrawable(Drawable leftDrawable) {
        this.leftDrawable = leftDrawable;
        if (leftDrawable != null) {
            img_input_item_left.setVisibility(View.VISIBLE);
            img_input_item_left.setBackgroundDrawable(leftDrawable);
        } else {
            img_input_item_left.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边的图片
     *
     * @param rightDrawable
     */
    private void setRightBackgroundDrawable(Drawable rightDrawable) {
        this.rightDrawable = rightDrawable;
        if (rightDrawable != null) {
            img_input_item_right.setVisibility(View.VISIBLE);
            img_input_item_right.setBackgroundDrawable(rightDrawable);
        } else {
            img_input_item_right.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左边文字内容
     *
     * @param leftText
     */
    private void setLeftText(String leftText) {
        this.leftText = leftText;
        if (leftText != null && !leftText.equals("")) {
            tv_input_item_left.setText(leftText);
            tv_input_item_left.setVisibility(VISIBLE);
        } else {
            tv_input_item_left.setVisibility(GONE);
        }
    }

    /**
     * 设置左边文字的颜色
     *
     * @param leftTextColor
     */
    private void setLeftTextColor(ColorStateList leftTextColor) {
        this.leftTextColor = leftTextColor;
        tv_input_item_left.setTextColor(leftTextColor);
    }


    /**
     * 设置编辑框内容
     *
     * @param contentText
     */
    private void setContentText(String contentText) {
        this.contentText = contentText;
        if (contentText != null) {
            et_input_item_content.setText(contentText);
            et_input_item_content.setVisibility(VISIBLE);
        } else {
            et_input_item_content.setVisibility(GONE);
        }
    }

    /**
     * 设置编辑框提示语
     *
     * @param contentHint
     */
    private void setContentHint(String contentHint) {
        this.contentHint = contentHint;
        if (contentHint != null) {
            et_input_item_content.setHint(contentHint);
            et_input_item_content.setVisibility(VISIBLE);
        } else {
            et_input_item_content.setVisibility(GONE);
        }
    }


    /**
     * 是否可以编辑
     *
     * @param canEdit
     */
    private void setContentEdit(boolean canEdit) {
        this.canEdit = canEdit;
        et_input_item_content.setFocusable(canEdit);
        et_input_item_content.setEnabled(canEdit);
        et_input_item_content.setFocusableInTouchMode(canEdit);
    }


    /**
     * 设置编辑框文字
     *
     * @param contentTextColor
     */
    private void setContentTextColor(ColorStateList contentTextColor) {
        this.contentTextColor = contentTextColor;
        et_input_item_content.setTextColor(contentTextColor);
    }

    /**
     * 设置编辑框输入格式
     *
     * @param contentInputType
     */
    private void setContentInputType(int contentInputType) {
        this.contentInputType = contentInputType;
        if (contentInputType != 0) {
            if (contentInputType == 1) {//电话
                et_input_item_content.setInputType(InputType.TYPE_CLASS_NUMBER);
                et_input_item_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            } else if (contentInputType == 2) {//卡
                et_input_item_content.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (contentInputType == 3) {//密码
                et_input_item_content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (contentInputType == 4) {//钱
                et_input_item_content.setInputType(InputType.TYPE_CLASS_NUMBER & InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
        }
    }


    /**
     * 设置右边编辑框内容
     *
     * @param rightText
     */
    private void setRightText(String rightText) {
        this.rightText = rightText;
        if (rightText != null) {
            et_input_item_right.setText(rightText);
            et_input_item_right.setVisibility(VISIBLE);
        } else {
            et_input_item_right.setVisibility(GONE);
        }
    }

    /**
     * 设置右边编辑框提示语
     *
     * @param rightHint
     */
    private void setRightHint(String rightHint) {
        this.rightHint = rightHint;
        if (rightHint != null) {
            et_input_item_right.setHint(rightHint);
            et_input_item_right.setVisibility(VISIBLE);
        } else {
            et_input_item_right.setVisibility(GONE);
        }
    }


    /**
     * 右边编辑框是否可以编辑
     *
     * @param rightCanEdit
     */
    private void setRightEdit(boolean rightCanEdit) {
        this.rightCanEdit = rightCanEdit;
        et_input_item_right.setFocusable(rightCanEdit);
        et_input_item_right.setEnabled(rightCanEdit);
        et_input_item_right.setFocusableInTouchMode(rightCanEdit);
    }


    /**
     * 设置右边编辑框文字
     *
     * @param rightTextColor
     */
    private void setRightTextColor(ColorStateList rightTextColor) {
        this.rightTextColor = rightTextColor;
        et_input_item_right.setTextColor(rightTextColor);
    }


    /**
     * 设置分隔线颜色
     *
     * @param lineViewColor
     */
    private void setLineViewColor(String lineViewColor) {
        this.lineViewColor = lineViewColor;
        line_view.setBackgroundColor(Color.parseColor(lineViewColor));
    }

    /**
     * 获取左边文字内容
     *
     * @return
     */
    public String getLeftText() {
        return leftText;
    }

    /**
     * 获取中间编辑框内容
     *
     * @return
     */
    public String getContentText() {
        return contentText;
    }

    /**
     * 获取右边编辑框内容
     *
     * @return
     */
    public String getRightText() {
        return rightText;
    }

    private void initView() {
        input_item_bg = findViewById(R.id.input_item_bg);
        tv_input_item_left = findViewById(R.id.tv_input_item_left);
        img_input_item_left = findViewById(R.id.img_input_item_left);
        img_input_item_right = findViewById(R.id.img_input_item_right);
        et_input_item_content = findViewById(R.id.et_input_item_content);
        et_input_item_right = findViewById(R.id.et_input_item_right);
        line_view = findViewById(R.id.line_view);
    }

}
