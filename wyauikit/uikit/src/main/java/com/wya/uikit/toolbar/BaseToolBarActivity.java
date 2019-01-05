package com.wya.uikit.toolbar;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;
import com.wya.uikit.toolbar.swipeback.SwipeBackActivity;

/**
 * @date: 2018/11/16 17:48
 * @author: Chunjiang Mao
 * @classname: BaseToolBarActivity
 * @describe: 标题栏
 */

public abstract class BaseToolBarActivity extends SwipeBackActivity {

    private WYAToolBarHelper wyaToolBarHelper;
    private TextView tvTitle, mTvLeft, mTvRightFirst, mTvRightSecond;
    private ImageView mImgRightFirst, mImgLeft, mImgRightSecond;
    private RelativeLayout toolBar;

    private LinearLayout parentLinearLayout;

    /**
     * 获取布局文件id
     *
     * @return
     */
    protected abstract int getLayoutId();

    //设置左边点击事件
    private LeftIconClickListener leftIconClickListener;
    //设置左边点击事件
    private LeftTextClickListener leftTextClickListener;
    //设置右边第一个文字点击事件
    private RightFirstTextClickListener rightFirstTextClickListener;
    //设置右边第二个文字点击事件
    private RightSecondTextClickListener rightSecondTextClickListener;
    //设置右边第一个图片点击事件
    private RightFirstIconClickListener rightFirstIconClickListener;
    //设置右边第二个图片点击事件
    private RightSecondIconClickListener rightSecondIconClickListener;
    //设置右边长按点击事件
    private RightSecondIconLongClickListener rightSecondIconLongClickListener;

    public interface LeftIconClickListener {
        /**
         * 设置左边点击事件
         * @param view
         */
        void leftIconClick(View view);
    }

    public interface LeftTextClickListener {
        /**
         * 设置左边点击事件
         * @param view
         */
        void leftTextClick(View view);
    }

    public interface RightFirstTextClickListener {
        /**
         * 设置右边文字点击事件
         * @param view
         */
        void rightFirstTextClick(View view);
    }

    public interface RightSecondTextClickListener {
        /**
         * 设置右边第二个文字点击事件
         * @param view
         */
        void rightSecondTextClick(View view);
    }

    public interface RightFirstIconClickListener {
        /**
         * 设置图片点击事件
         * @param view
         */
        void rightFirstIconClick(View view);
    }

    public interface RightSecondIconClickListener {
        /**
         * 设置第二张图片点击事件
         * @param view
         */
        void rightSecondIconClick(View view);
    }

    public interface RightSecondIconLongClickListener {
        /**
         * 设置第二张长按点击事件
         * @param view
         */
        void rightSecondIconLongClick(View view);
    }


    /**
     * 设置左边图标点击事件监听
     *
     * @param leftIconClickListener
     */
    public void setLeftIconClickListener(LeftIconClickListener leftIconClickListener) {
        this.leftIconClickListener = leftIconClickListener;
    }

    /**
     * 设置左边点击事件内容和监听
     *
     * @param leftTextClickListener
     */
    public void setLeftTextClickListener(LeftTextClickListener leftTextClickListener) {
        this.leftTextClickListener = leftTextClickListener;
    }

    /**
     * 设置右边第一个文字点击事件
     *
     * @param rightFirstTextClickListener
     */
    public void setRightFirstTextClickListener(RightFirstTextClickListener rightFirstTextClickListener) {
        this.rightFirstTextClickListener = rightFirstTextClickListener;
    }

    /**
     * 设置右边第二个文字点击事件监听
     *
     * @param rightSecondTextClickListener
     */
    public void setRightSecondTextClickListener(RightSecondTextClickListener rightSecondTextClickListener) {
        this.rightSecondTextClickListener = rightSecondTextClickListener;
    }

    /**
     * 设置右边图片点击事件监听
     *
     * @param rightFirstIconClickListener
     */
    public void setRightFirstIconClickListener(RightFirstIconClickListener rightFirstIconClickListener) {
        this.rightFirstIconClickListener = rightFirstIconClickListener;
    }

    /**
     * 设置右边第二个图片点击事件监听
     *
     * @param rightSecondIconClickListener
     */
    public void setRightSecondIconClickListener(RightSecondIconClickListener rightSecondIconClickListener) {
        this.rightSecondIconClickListener = rightSecondIconClickListener;
    }

    /**
     * 设置右边第二个图片长按点击事件监听
     *
     * @param rightSecondIconLongClickListener
     */
    public void setRightSecondIconLongClickListener(RightSecondIconLongClickListener rightSecondIconLongClickListener) {
        this.rightSecondIconLongClickListener = rightSecondIconLongClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.base_toolbar_layout);
        setContentView(getLayoutId());
        initWYAActionBar();
    }

    /**
     * uikit-ToolBar
     * 初始化控件
     */
    public void initWYAActionBar() {
        wyaToolBarHelper = new WYAToolBarHelper();
        tvTitle = (TextView) findViewById(R.id.title);
        mTvLeft = (TextView) findViewById(R.id.tv_left);
        mTvRightFirst = (TextView) findViewById(R.id.tv_right_first);
        mTvRightSecond = (TextView) findViewById(R.id.tv_right_second);
        mImgRightFirst = (ImageView) findViewById(R.id.img_right_first);
        mImgRightSecond = (ImageView) findViewById(R.id.img_right_second);
        mImgLeft = (ImageView) findViewById(R.id.img_left);
        toolBar = (RelativeLayout) findViewById(R.id.tool_bar);

        showToolBar(wyaToolBarHelper.isShow());
        setBackgroundColor(wyaToolBarHelper.getBackgroundColor(), wyaToolBarHelper.isLight());
        setTitle(wyaToolBarHelper.getTitle());
        setTitleColor(wyaToolBarHelper.getTitleColor());
        setTitleSize(wyaToolBarHelper.getTitleSize());

        setLeftText(wyaToolBarHelper.getLeftText());
        setLeftTextSize(wyaToolBarHelper.getLeftTextSize());
        setLeftTextColor(wyaToolBarHelper.getLeftTextColor());
        showLeftText(wyaToolBarHelper.isShowLeftText());

        setLeftIcon(wyaToolBarHelper.getLeftIcon());
        showLeftIcon(wyaToolBarHelper.isShowLeftIcon());

        showFirstRightText(wyaToolBarHelper.isShowFirstRightText());
        setFirstRightText(wyaToolBarHelper.getFirstRightText());
        setFirstRightTextColor(wyaToolBarHelper.getFirstRightTextColor());
        setFirstRightTextSize(wyaToolBarHelper.getFirstRightTextSize());

        showSecondRightText(wyaToolBarHelper.isShowSecondRightText());
        setSecondRightText(wyaToolBarHelper.getSecondRightText());
        setSecondRightTextColor(wyaToolBarHelper.getSecondRightTextColor());
        setSecondRightTextSize(wyaToolBarHelper.getSecondRightTextSize());

        setFirstRightIcon(wyaToolBarHelper.getFirstRightIcon());
        showFirstRightIcon(wyaToolBarHelper.isShowFirstRightIcon());

        setSecondRightIcon(wyaToolBarHelper.getSecondRightIcon());
        showSecondRightIcon(wyaToolBarHelper.isShowSecondRightIcon());

        initClick();
    }


    /**
     * 设置标题栏显示
     *
     * @param show
     */
    public void showToolBar(boolean show) {
        if (show) {
            toolBar.setVisibility(View.VISIBLE);
        } else {
            toolBar.setVisibility(View.GONE);
        }
    }


    /**
     * 设置标题栏颜色和状态栏颜色
     *
     * @param backgroundColor 标题栏背景颜色
     * @param isLight         修改状态栏文字图标颜色
     */
    public void setBackgroundColor(int backgroundColor, boolean isLight) {
        toolBar.setBackgroundColor(backgroundColor);
        StatusBarUtil.setColorForSwipeBack(this, backgroundColor, 0);
        if (isLight) {
            StatusBarUtil.setLightMode(this);
        } else {
            StatusBarUtil.setDarkMode(this);
        }
    }

    /**
     * 设置标题内容
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置标题颜色
     *
     * @param titleColor
     */
    @Override
    public void setTitleColor(int titleColor) {
        tvTitle.setTextColor(titleColor);
    }

    /**
     * 设置标题字体大小
     *
     * @param titleSize
     */
    public void setTitleSize(int titleSize) {
        tvTitle.setTextSize(titleSize);
    }

    /**
     * 设置标题左边文字内容
     *
     * @param leftText
     */
    public void setLeftText(String leftText) {
        mTvLeft.setText(leftText);
    }

    /**
     * 设置标题左边文字大小
     *
     * @param leftTextSize
     */
    public void setLeftTextSize(int leftTextSize) {
        mTvLeft.setTextSize(leftTextSize);
    }

    /**
     * 设置标题左边文字颜色
     *
     * @param leftTextColor
     */
    public void setLeftTextColor(int leftTextColor) {
        mTvLeft.setTextColor(leftTextColor);
    }

    /**
     * 设置是否显示左边文字
     *
     * @param showLeftText
     */
    public void showLeftText(boolean showLeftText) {
        if (showLeftText) {
            mTvLeft.setVisibility(View.VISIBLE);
        } else {
            mTvLeft.setVisibility(View.GONE);
        }
    }


    /**
     * 设置标题左边图标
     *
     * @param leftIcon
     */
    public void setLeftIcon(int leftIcon) {
        if (leftIcon != 0) {
            mImgLeft.setImageDrawable(ContextCompat.getDrawable(this, leftIcon));
        } else {
            mImgLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题左边图标显示
     *
     * @param showLeftIcon
     */
    public void showLeftIcon(boolean showLeftIcon) {
        if (showLeftIcon) {
            mImgLeft.setVisibility(View.VISIBLE);
        } else {
            mImgLeft.setVisibility(View.GONE);
        }
    }


    /**
     * 设置右边第一文字显示
     *
     * @param showFirstRightText
     */
    public void showFirstRightText(boolean showFirstRightText) {
        if (showFirstRightText) {
            mTvRightFirst.setVisibility(View.VISIBLE);
        } else {
            mTvRightFirst.setVisibility(View.GONE);
        }
    }


    /**
     * 设置右边第一文字内容
     *
     * @param firstRightText
     */
    public void setFirstRightText(String firstRightText) {
        mTvRightFirst.setText(firstRightText);
    }


    /**
     * 设置右边第一文字颜色
     *
     * @param firstRightTextColor
     */
    public void setFirstRightTextColor(int firstRightTextColor) {
        mTvRightFirst.setTextColor(firstRightTextColor);
    }


    /**
     * 设置右边第一文字大小
     *
     * @param firstRightTextSize
     */
    public void setFirstRightTextSize(int firstRightTextSize) {
        mTvRightFirst.setTextSize(firstRightTextSize);
    }


    /**
     * 设置右边第二文字显示
     *
     * @param showSecondRightText
     */
    public void showSecondRightText(boolean showSecondRightText) {
        if (showSecondRightText) {
            mTvRightSecond.setVisibility(View.VISIBLE);
        } else {
            mTvRightSecond.setVisibility(View.GONE);
        }
    }


    /**
     * 设置右边第二文字内容
     *
     * @param secondRightText
     */
    public void setSecondRightText(String secondRightText) {
        mTvRightSecond.setText(secondRightText);
    }


    /**
     * 设置右边第二文字颜色
     *
     * @param secondRightTextColor
     */
    public void setSecondRightTextColor(int secondRightTextColor) {
        mTvRightSecond.setTextColor(secondRightTextColor);
    }


    /**
     * 设置右边第二文字大小
     *
     * @param secondRightTextSize
     */
    public void setSecondRightTextSize(int secondRightTextSize) {
        mTvRightSecond.setTextSize(secondRightTextSize);
    }


    /**
     * 设置标题右边第一个图标
     *
     * @param firstRightIcon
     */
    public void setFirstRightIcon(int firstRightIcon) {
        if (firstRightIcon != 0) {
            mImgRightFirst.setImageDrawable(ContextCompat.getDrawable(this, firstRightIcon));
        } else {
            mImgRightFirst.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题右边第一个图标显示
     *
     * @param showFirstRightIcon
     */
    public void showFirstRightIcon(boolean showFirstRightIcon) {
        if (showFirstRightIcon) {
            mImgRightFirst.setVisibility(View.VISIBLE);
        } else {
            mImgRightFirst.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题右边第二个图标
     *
     * @param secondRightIcon
     */
    public void setSecondRightIcon(int secondRightIcon) {
        if (secondRightIcon != 0) {
            mImgRightSecond.setImageDrawable(ContextCompat.getDrawable(this, secondRightIcon));
        } else {
            mImgRightSecond.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题右边第二个图标显示
     *
     * @param showSecondRightIcon
     */
    public void showSecondRightIcon(boolean showSecondRightIcon) {
        if (showSecondRightIcon) {
            mImgRightSecond.setVisibility(View.VISIBLE);
        } else {
            mImgRightSecond.setVisibility(View.GONE);
        }
    }


    /**
     * 左右两边点击事件监听设置
     */
    private void initClick() {
        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftIconClickListener != null) {
                    leftIconClickListener.leftIconClick(view);
                } else {
                    BaseToolBarActivity.this.finish();
                }
            }
        });

        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftTextClickListener != null) {
                    leftTextClickListener.leftTextClick(view);
                } else {
                    BaseToolBarActivity.this.finish();
                }
            }
        });

        mImgRightSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightSecondIconClickListener != null) {
                    rightSecondIconClickListener.rightSecondIconClick(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "图片2", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mImgRightSecond.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (rightSecondIconLongClickListener != null) {
                    rightSecondIconLongClickListener.rightSecondIconLongClick(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "图片2长按", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        mImgRightFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightFirstIconClickListener != null) {
                    rightFirstIconClickListener.rightFirstIconClick(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "图片1", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mTvRightSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightSecondTextClickListener != null) {
                    rightSecondTextClickListener.rightSecondTextClick(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "文字2", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTvRightFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightFirstTextClickListener != null) {
                    rightFirstTextClickListener.rightFirstTextClick(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "文字1", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
