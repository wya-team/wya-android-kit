package com.wya.uikit.toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;

/**
 * 创建日期：2018/11/16 17:48
 * 作者： Mao Chunjiang
 * 文件名称： BaseToolBarActivity
 * 类说明：标题栏
 */

public abstract class BaseToolBarActivity extends Activity {

    private WYAToolBarHelper wyaToolBarHelper;
    private TextView title, tv_left, tv_right;
    private RelativeLayout rl_right, rl_left;
    private ImageView img_right, img_left;
    private RelativeLayout title_bar_bg;

    private LinearLayout parentLinearLayout;


    protected abstract int getLayoutID();


    private onLeftOnclickListener onLeftOnclickListener;//设置左边点击事件
    private onRightOnclickListener onRightOnclickListener;//设置右边点击事件


    /**
     * 设置左边点击事件
     */
    public interface onLeftOnclickListener {
        void onLeftClick();
    }

    /**
     * 设置右边点击事件
     */
    public interface onRightOnclickListener {
        void onRightClick();
    }



    /**
     * 设置左边点击事件内容和监听
     * @param onLeftOnclickListener
     */
    public void setLeftOnclickListener(onLeftOnclickListener onLeftOnclickListener) {
        this.onLeftOnclickListener = onLeftOnclickListener;
    }

    /**
     * 设置右边边点击事件内容和监听
     * @param onRightOnclickListener
     */
    public void setRightOnclickListener(onRightOnclickListener onRightOnclickListener) {
        this.onRightOnclickListener = onRightOnclickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.base_toolbar_layout);
        setContentView(getLayoutID());
        initWYAActionBar();
    }

    /**
     * uikit-ToolBar
     * 初始化控件
     */

    private void initWYAActionBar() {
        wyaToolBarHelper = new WYAToolBarHelper();
        title = findViewById(R.id.title);
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        rl_right = findViewById(R.id.rl_right);
        rl_left = findViewById(R.id.rl_left);
        img_right = findViewById(R.id.img_right);
        img_left = findViewById(R.id.img_left);
        title_bar_bg = findViewById(R.id.title_bar_bg);

        initToolBarBgColor("#1890FF");
        initToolBaseTitle(wyaToolBarHelper.getTitleStr(), wyaToolBarHelper.getTitleTextSize(), wyaToolBarHelper.getTitleTextColor(), wyaToolBarHelper.isShowTitle());
        initLeft(wyaToolBarHelper.getImgLeftRes(), wyaToolBarHelper.getTvLeftStr(), wyaToolBarHelper.getTvLeftTextColor(), wyaToolBarHelper.isShowImgLeft(), wyaToolBarHelper.getTvLeftTextSize(), wyaToolBarHelper.isShowTvLeft());
        initRight(wyaToolBarHelper.getImgLeftRes(), wyaToolBarHelper.getTvRightStr(), wyaToolBarHelper.getTvRightTextColor(), wyaToolBarHelper.isShowImgRight(), wyaToolBarHelper.getTvRightTextSize(), wyaToolBarHelper.isShowTvRight());

        initClick();
    }

    /**
     * 左右两边点击事件监听设置
     */
    private void initClick() {
        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onLeftOnclickListener != null){
                    onLeftOnclickListener.onLeftClick();
                } else {
                    BaseToolBarActivity.this.finish();
                }
            }
        });
        rl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onRightOnclickListener != null){
                    onRightOnclickListener.onRightClick();
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "右边", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 设置标题右边的文字以及图片
     * @param imgRightRes
     * @param tvRightStr
     * @param tvRightTextColor
     * @param showImgRight
     * @param tvRightTextSize
     * @param showTvRight
     */
    @SuppressLint("NewApi")
    public void initRight(int imgRightRes, String tvRightStr, int tvRightTextColor, boolean showImgRight, int tvRightTextSize, boolean showTvRight) {
        if (showImgRight) {
            if (imgRightRes != 0) {
                img_right.setVisibility(View.VISIBLE);
                img_right.setBackground(getResources().getDrawable(imgRightRes));
            } else {
                img_right.setVisibility(View.GONE);
            }
        } else {
            img_right.setVisibility(View.GONE);
        }
        if(showTvRight){
            if(tvRightStr != null){
                tv_right.setTextColor(this.getResources().getColor(tvRightTextColor));
                tv_right.setText(tvRightStr);
                tv_right.setTextSize(tvRightTextSize);
                tv_right.setVisibility(View.VISIBLE);
            } else {
                tv_right.setVisibility(View.GONE);
            }
        } else {
            tv_right.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题左边文字以及图片
     * @param imgLeftRes
     * @param tvLeftStr
     * @param tvLeftTextColor
     * @param showImgLeft
     * @param tvLeftTextSize
     * @param showTvLeft
     */
    @SuppressLint("NewApi")
    public void initLeft(int imgLeftRes, String tvLeftStr, int tvLeftTextColor, boolean showImgLeft, int tvLeftTextSize, boolean showTvLeft) {
        if (showImgLeft) {
            if (imgLeftRes != 0) {
                img_left.setVisibility(View.VISIBLE);
                img_left.setBackground(getResources().getDrawable(imgLeftRes));
            } else {
                img_left.setVisibility(View.GONE);
            }
        } else {
            img_left.setVisibility(View.GONE);
        }
        if(showTvLeft){
            if(tvLeftStr != null){
                tv_left.setTextColor(this.getResources().getColor(tvLeftTextColor));
                tv_left.setText(tvLeftStr);
                tv_left.setTextSize(tvLeftTextSize);
                tv_left.setVisibility(View.VISIBLE);
            } else {
                tv_left.setVisibility(View.GONE);
            }
        } else {
            tv_left.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题的名字
     * @param titleStr
     * @param titleTextSize
     * @param titleTextColor
     * @param showTitle
     */
    public void initToolBaseTitle(String titleStr, int titleTextSize, int titleTextColor, boolean showTitle) {
        title.setText(titleStr);
        title.setTextSize(titleTextSize);
        title.setTextColor(titleTextColor);
        if (showTitle) {
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
    }

    /**
     * 初始标题栏颜色
     */
    public void initToolBarBgColor(String toolbar_bg_color_value) {
        title_bar_bg.setBackgroundColor(Color.parseColor(toolbar_bg_color_value));
    }

    /**
     * 将activity的布局添加到住布局中
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //  added the sub-activity layout id in parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }


    /**
     * uikit-ToolBar
     *
     * @param layoutResID
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }
}
