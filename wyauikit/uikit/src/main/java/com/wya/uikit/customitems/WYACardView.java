package com.wya.uikit.customitems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.uikit.R;

/**
 * @date: 2018/11/29 11:56
 * @author: Chunjiang Mao
 * @classname: WYACardView
 * @describe: 自定义CardView
 */

@SuppressLint("AppCompatCustomView")
public class WYACardView extends LinearLayout {
    ImageView imgWyaCardViewTitle;
    TextView tvWyaCardViewTitle;
    TextView tvWyaCardViewRight;
    TextView tvWyaCardViewContent;
    TextView tvWyaCardViewAssist;
    LinearLayout wyaCardView;
    /**
     * 背景色
     */
    private int backColor = 0;
    
    /**
     * 背景图片
     */
    private GradientDrawable gradientDrawable = null;
    
    /**
     * 标题左边
     */
    private int imgTitleLeftDrawable;
    
    /**
     * 标题文字的颜色
     */
    private ColorStateList titleTextColor = null;
    private ColorStateList rightTextColor = null;
    private ColorStateList contentTextColor = null;
    private ColorStateList assistTextColor = null;
    
    private float radius;
    
    public WYACardView(Context context) {
        this(context, null);
    }
    
    public WYACardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.wya_card_view, this);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WYACardView);
        if (a != null) {
            //设置背景色
            ColorStateList colorList = a.getColorStateList(R.styleable.WYACardView_backColor);
            if (colorList != null) {
                backColor = colorList.getColorForState(getDrawableState(), 0);
                if (backColor != 0) {
                    setBackgroundColor(backColor);
                }
            }
    
            //设置标题左边背景图
            imgTitleLeftDrawable = a.getResourceId(R.styleable.WYACardView_imgTitleLeft, 0);
            setTitleLeftBackgroundDrawable(imgTitleLeftDrawable);
    
            //设置标题文字的颜色
            titleTextColor = a.getColorStateList(R.styleable.WYACardView_titleTextColor);
            if (titleTextColor != null) {
                setTitleTextColor(titleTextColor);
            }
    
            //设置右边文字的颜色
            rightTextColor = a.getColorStateList(R.styleable.WYACardView_rightTextColor);
            if (rightTextColor != null) {
                setRightTextColor(rightTextColor);
            }
    
            //设置内容文字的颜色
            contentTextColor = a.getColorStateList(R.styleable.WYACardView_contentTextColor);
            if (contentTextColor != null) {
                setContentTextColor(contentTextColor);
            }
    
            //设置辅助文字的颜色
            assistTextColor = a.getColorStateList(R.styleable.WYACardView_assistTextColor);
            if (assistTextColor != null) {
                setAssistTextColor(assistTextColor);
            }
    
            //设置背景图片，若backColor与backGroundDrawable同时存在，则backGroundDrawable将覆盖backColor
            gradientDrawable = (GradientDrawable) a.getDrawable(R.styleable.WYACardView_backGroundImage);
            if (gradientDrawable != null) {
                setBackgroundDrawable(gradientDrawable);
            }
    
            getGradientDrawable();
            if (backColor != 0) {
                gradientDrawable.setColor(backColor);
                setBackgroundDrawable(gradientDrawable);
            }
            //设置圆角矩形的角度
            radius = a.getDimension(R.styleable.WYACardView_radius, 0);
            if (radius != 0) {
                setRadius(radius);
            }
            a.recycle();
        }
    }
    
    private void initView() {
        imgWyaCardViewTitle = findViewById(R.id.img_wya_card_view_title);
        tvWyaCardViewTitle = findViewById(R.id.tv_wya_card_view_title);
        tvWyaCardViewRight = findViewById(R.id.tv_wya_card_view_right);
        tvWyaCardViewContent = findViewById(R.id.tv_wya_card_view_content);
        tvWyaCardViewAssist = findViewById(R.id.tv_wya_card_view_assist);
        wyaCardView = findViewById(R.id.wya_card_view);
    }
    
    /**
     * 设置标题左边背景图
     *
     * @param imgTitleLeftDrawable
     */
    private void setTitleLeftBackgroundDrawable(int imgTitleLeftDrawable) {
        if (imgTitleLeftDrawable != 0) {
            imgWyaCardViewTitle.setVisibility(View.VISIBLE);
            imgWyaCardViewTitle.setBackgroundResource(imgTitleLeftDrawable);
        } else {
            imgWyaCardViewTitle.setVisibility(View.GONE);
        }
    }
    
    /**
     * 设置标题字体颜色
     *
     * @param titleTextColor
     */
    private void setTitleTextColor(ColorStateList titleTextColor) {
        this.titleTextColor = titleTextColor;
        tvWyaCardViewTitle.setTextColor(titleTextColor);
    }
    
    /**
     * 设置右边字体颜色
     *
     * @param rightTextColor
     */
    private void setRightTextColor(ColorStateList rightTextColor) {
        this.rightTextColor = rightTextColor;
        tvWyaCardViewRight.setTextColor(rightTextColor);
    }
    
    /**
     * 设置内容字体颜色
     *
     * @param contentTextColor
     */
    private void setContentTextColor(ColorStateList contentTextColor) {
        this.contentTextColor = contentTextColor;
        tvWyaCardViewContent.setTextColor(contentTextColor);
    }
    
    /**
     * 设置辅助字体颜色
     *
     * @param assistTextColor
     */
    private void setAssistTextColor(ColorStateList assistTextColor) {
        this.assistTextColor = assistTextColor;
        tvWyaCardViewAssist.setTextColor(assistTextColor);
    }
    
    /**
     * 设置圆角的角度
     *
     * @param radius
     */
    public void setRadius(float radius) {
        getGradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        setBackgroundDrawable(gradientDrawable);
    }
    
    private void getGradientDrawable() {
        if (gradientDrawable == null) {
            gradientDrawable = new GradientDrawable();
        }
    }
    
    /**
     * 设置背景的背景色
     *
     * @param backColor
     */
    public void setBackColor(int backColor) {
        this.backColor = backColor;
        gradientDrawable.setColor(backColor);
        wyaCardView.setBackgroundDrawable(gradientDrawable);
    }
    
    /**
     * 设置卡片标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvWyaCardViewTitle.setText(title);
    }
}
