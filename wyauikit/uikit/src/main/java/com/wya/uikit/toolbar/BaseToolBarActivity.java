package com.wya.uikit.toolbar;

import android.annotation.SuppressLint;
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
import com.wya.uikit.toolbar.swipeback.SwipeBackActivity;

/**
 * @date: 2018/11/16 17:48
 * @author: Chunjiang Mao
 * @classname:  BaseToolBarActivity
 * @describe: 标题栏
 */

public abstract class BaseToolBarActivity extends SwipeBackActivity {

    private WYAToolBarHelper wyaToolBarHelper;
    private TextView title, tv_left, tv_right, tv_right_anther;
    private ImageView img_right, img_left, img_right_anther;
    private RelativeLayout title_bar_bg;

    private LinearLayout parentLinearLayout;

    protected abstract int getLayoutID();


    private onLeftImgOnclickListener onLeftImgOnclickListener;//设置左边点击事件
    private onLeftTvOnclickListener onLeftTvOnclickListener;//设置左边点击事件
    private onRightTvOnclickListener onRightTvOnclickListener;//设置右边点击事件
    private onRightTvAntherOnclickListener onRightTvAntherOnclickListener;//设置右边点击事件
    private onRightImageAntherOnLongClickListener onRightImageAntherOnLongClickListener;//设置右边长按点击事件
    private onRightImageOnclickListener onRightImageOnclickListener;//设置右边点击事件
    private onRightImageAntherOnclickListener onRightImageAntherOnclickListener;//设置右边点击事件

    /**
     * 设置左边点击事件
     */
    public interface onLeftImgOnclickListener {
        void onLeftImgClick(View view);
    }

    /**
     * 设置左边点击事件
     */
    public interface onLeftTvOnclickListener {
        void onLeftTvClick(View view);
    }

    /**
     * 设置右边文字点击事件
     */
    public interface onRightTvOnclickListener {
        void onRightTvOnclickListener(View view);
    }

    /**
     * 设置右边第二个文字点击事件
     */
    public interface onRightTvAntherOnclickListener {
        void onRightTvAntherOnclickListener(View view);
    }

    /**
     * 设置图片点击事件
     */
    public interface onRightImageOnclickListener {
        void onRightImageOnclickListener(View view);
    }

    /**
     * 设置第二张点击事件
     */
    public interface onRightImageAntherOnclickListener {
        void onRightImageAntherOnclickListener(View view);
    }

    /**
     * 设置第二张长按点击事件
     */
    public interface onRightImageAntherOnLongClickListener {
        void onRightImageAntherOnLongClickListener(View view);
    }


    /**
     * 设置左边点击事件内容和监听
     *
     * @param onLeftImgOnclickListener
     */
    public void setLeftImgOnclickListener(onLeftImgOnclickListener onLeftImgOnclickListener) {
        this.onLeftImgOnclickListener = onLeftImgOnclickListener;
    }

    /**
     * 设置左边点击事件内容和监听
     *
     * @param onLeftTvOnclickListener
     */
    public void setLeftTvOnclickListener(onLeftTvOnclickListener onLeftTvOnclickListener) {
        this.onLeftTvOnclickListener = onLeftTvOnclickListener;
    }

    /**
     * 设置右边文字点击事件
     *
     * @param onRightTvOnclickListener
     */
    public void setRightTvOnclickListener(onRightTvOnclickListener onRightTvOnclickListener) {
        this.onRightTvOnclickListener = onRightTvOnclickListener;
    }

    /**
     * 设置右边第二个文字点击事件监听
     *
     * @param onRightTvAntherOnclickListener
     */
    public void setRightTvAntherOnclickListener(onRightTvAntherOnclickListener onRightTvAntherOnclickListener) {
        this.onRightTvAntherOnclickListener = onRightTvAntherOnclickListener;
    }

    /**
     * 设置右边图片点击事件监听
     *
     * @param onRightImageOnclickListener
     */
    public void setRightImageOnclickListener(onRightImageOnclickListener onRightImageOnclickListener) {
        this.onRightImageOnclickListener = onRightImageOnclickListener;
    }

    /**
     * 设置右边第二个图片点击事件监听
     *
     * @param onRightImageAntherOnclickListener
     */
    public void setRightImageAntherOnclickListener(onRightImageAntherOnclickListener onRightImageAntherOnclickListener) {
        this.onRightImageAntherOnclickListener = onRightImageAntherOnclickListener;
    }

    /**
     * 设置右边第二个图片长按点击事件监听
     *
     * @param onRightImageAntherOnLongClickListener
     */
    public void setRightImageAntherOnLongClickListener(onRightImageAntherOnLongClickListener onRightImageAntherOnLongClickListener) {
        this.onRightImageAntherOnLongClickListener = onRightImageAntherOnLongClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.base_toolbar_layout);
        setContentView(getLayoutID());
        initWYAActionBar();
    }


    public void initWYAActionBarDefault(boolean showToolBar, String toolbarBgColorValue, boolean isShowTitle, String titleStr, int titleTextSize, String titleTextColorValue,
                                        boolean isShowTvLeft, String tvLeftStr, int tvLeftTextSize, String tvLeftTextColorValue, boolean isShowImgLeft, int imgLeftRes,
                                        boolean isShowTvRight, String tvRightStr, int tvRightTextSize, String tvRightTextColorValue, boolean isShowImgRight,
                                        boolean isShowImgRightAnther, int imgRightRes, int imgRightResAnther,
                                        boolean isShowTvRightAnther, String tvRightAntherStr, int tvRightAntherTextSize, String tvRightAntherTextColorValue, boolean isLight) {
        wyaToolBarHelper.setShowTitle(showToolBar);
        wyaToolBarHelper.setToolbar_bg_color(toolbarBgColorValue);
        wyaToolBarHelper.setShowTitle(isShowTitle);
        wyaToolBarHelper.setTitleStr(titleStr);
        wyaToolBarHelper.setTitleTextSize(titleTextSize);
        wyaToolBarHelper.setTitleTextColor(titleTextColorValue);
        wyaToolBarHelper.setShowTvLeft(isShowTvLeft);
        wyaToolBarHelper.setTvLeftStr(tvLeftStr);
        wyaToolBarHelper.setTvLeftTextSize(tvLeftTextSize);
        wyaToolBarHelper.setTvLeftTextColor(tvLeftTextColorValue);
        wyaToolBarHelper.setShowImgLeft(isShowImgLeft);
        wyaToolBarHelper.setImgLeftRes(imgLeftRes);

        wyaToolBarHelper.setShowTvRight(isShowTvRight);
        wyaToolBarHelper.setTvRightStr(tvRightStr);
        wyaToolBarHelper.setTvRightTextSize(tvRightTextSize);
        wyaToolBarHelper.setTvRightTextColor(tvRightTextColorValue);
        wyaToolBarHelper.setShowTvRightAnther(isShowTvRightAnther);
        wyaToolBarHelper.setTvRightAntherStr(tvRightAntherStr);
        wyaToolBarHelper.setTvRightAntherTextSize(tvRightAntherTextSize);
        wyaToolBarHelper.setTvRightAntherTextColor(tvRightAntherTextColorValue);


        wyaToolBarHelper.setShowImgRight(isShowImgRight);
        wyaToolBarHelper.setImgRightRes(imgRightRes);
        wyaToolBarHelper.setImgRightResAnther(imgRightResAnther);
        wyaToolBarHelper.setShowImgRightAnther(isShowImgRightAnther);

        wyaToolBarHelper.setLight(isLight);

        initShowToolBar(wyaToolBarHelper.isShowTitle());
        initToolBarBgColor(wyaToolBarHelper.getToolbar_bg_color(), wyaToolBarHelper.isLight());
        initToolBarTitle(wyaToolBarHelper.getTitleStr(), wyaToolBarHelper.getTitleTextSize(), wyaToolBarHelper.getTitleTextColor(), wyaToolBarHelper.isShowTitle());
        initTvLeft(wyaToolBarHelper.getTvLeftStr(), wyaToolBarHelper.getTvLeftTextColor(), wyaToolBarHelper.getTvLeftTextSize(), wyaToolBarHelper.isShowTvLeft());
        initImgLeft(wyaToolBarHelper.getImgLeftRes(), wyaToolBarHelper.isShowImgLeft());
        initTvRight(wyaToolBarHelper.getTvRightStr(), wyaToolBarHelper.getTvRightTextColor(), wyaToolBarHelper.getTvRightTextSize(), wyaToolBarHelper.isShowTvRight());
        initTvRightAnther(wyaToolBarHelper.getTvRightAntherStr(), wyaToolBarHelper.getTvRightAntherTextColor(), wyaToolBarHelper.getTvRightAntherTextSize(), wyaToolBarHelper.isShowTvRightAnther());
        initImgRight(wyaToolBarHelper.getImgRightRes(), wyaToolBarHelper.isShowImgRight());
        initImgRightAnther(wyaToolBarHelper.getImgRightResAnther(), wyaToolBarHelper.isShowImgRightAnther());
    }

    /**
     * uikit-ToolBar
     * 初始化控件
     */
    public void initWYAActionBar() {
        wyaToolBarHelper = new WYAToolBarHelper();
        title = (TextView) findViewById(R.id.title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right_anther = (TextView) findViewById(R.id.tv_right_anther);
        img_right = (ImageView) findViewById(R.id.img_right);
        img_right_anther = (ImageView) findViewById(R.id.img_right_anther);
        img_left = (ImageView) findViewById(R.id.img_left);
        title_bar_bg = (RelativeLayout) findViewById(R.id.title_bar_bg);

        initShowToolBar(wyaToolBarHelper.isShowTitle());
        initToolBarBgColor(wyaToolBarHelper.getToolbar_bg_color(), wyaToolBarHelper.isLight());
        initToolBarTitle(wyaToolBarHelper.getTitleStr(), wyaToolBarHelper.getTitleTextSize(), wyaToolBarHelper.getTitleTextColor(), wyaToolBarHelper.isShowTitle());
        initTvLeft(wyaToolBarHelper.getTvLeftStr(), wyaToolBarHelper.getTvLeftTextColor(), wyaToolBarHelper.getTvLeftTextSize(), wyaToolBarHelper.isShowTvLeft());
        initImgLeft(wyaToolBarHelper.getImgLeftRes(), wyaToolBarHelper.isShowImgLeft());
        initTvRight(wyaToolBarHelper.getTvRightStr(), wyaToolBarHelper.getTvRightTextColor(), wyaToolBarHelper.getTvRightTextSize(), wyaToolBarHelper.isShowTvRight());
        initImgRight(wyaToolBarHelper.getImgRightRes(), wyaToolBarHelper.isShowImgRight());
        initImgRightAnther(wyaToolBarHelper.getImgRightResAnther(), wyaToolBarHelper.isShowImgRightAnther());
        initClick();
    }


    /**
     * 是否显示标题
     *
     * @param showTitle
     */
    public void initShowToolBar(boolean showTitle) {
        if (showTitle) {
            title_bar_bg.setVisibility(View.VISIBLE);
        } else {
            title_bar_bg.setVisibility(View.GONE);
        }
    }

    /**
     * 左右两边点击事件监听设置
     */
    private void initClick() {
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLeftImgOnclickListener != null) {
                    onLeftImgOnclickListener.onLeftImgClick(view);
                } else {
                    BaseToolBarActivity.this.finish();
                }
            }
        });

        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLeftTvOnclickListener != null) {
                    onLeftTvOnclickListener.onLeftTvClick(view);
                } else {
                    BaseToolBarActivity.this.finish();
                }
            }
        });

        img_right_anther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightImageAntherOnclickListener != null) {
                    onRightImageAntherOnclickListener.onRightImageAntherOnclickListener(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "图片2", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_right_anther.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onRightImageAntherOnLongClickListener != null) {
                    onRightImageAntherOnLongClickListener.onRightImageAntherOnLongClickListener(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "图片2长按", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightImageOnclickListener != null) {
                    onRightImageOnclickListener.onRightImageOnclickListener(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "图片1", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_right_anther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightTvAntherOnclickListener != null) {
                    onRightTvAntherOnclickListener.onRightTvAntherOnclickListener(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "文字2", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightTvOnclickListener != null) {
                    onRightTvOnclickListener.onRightTvOnclickListener(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "文字1", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 设置标题右边的图片
     *
     * @param imgRightRes  资源图片 R.mimap.xx
     * @param showImgRight 是否显示右边图片
     */
    @SuppressLint("NewApi")
    public void initImgRight(int imgRightRes, boolean showImgRight) {
        if (showImgRight) {
            if (imgRightRes != 0) {
                img_right.setVisibility(View.VISIBLE);
                img_right.setImageDrawable(getResources().getDrawable(imgRightRes));
            } else {
                img_right.setVisibility(View.GONE);
            }
        } else {
            img_right.setVisibility(View.GONE);
        }

    }

    /**
     * 设置标题右边另外的图片
     *
     * @param imgRightAntherRes 资源图片 R.mimap.xx
     * @param showImgAnther     是否显示右边图片
     */
    @SuppressLint("NewApi")
    public void initImgRightAnther(int imgRightAntherRes, boolean showImgAnther) {
        if (showImgAnther) {
            if (imgRightAntherRes != 0) {
                img_right_anther.setVisibility(View.VISIBLE);
                img_right_anther.setImageDrawable(getResources().getDrawable(imgRightAntherRes));
            } else {
                img_right_anther.setVisibility(View.GONE);
            }
        } else {
            img_right_anther.setVisibility(View.GONE);
        }

    }


    /**
     * 设置标题右边的文字
     *
     * @param tvRightStr       文本内容
     * @param tvRightTextColor 文本颜色
     * @param tvRightTextSize  文字大小
     * @param showTvRight      实现显示右边文字
     */
    @SuppressLint("NewApi")
    public void initTvRight(String tvRightStr, int tvRightTextColor, int tvRightTextSize, boolean showTvRight) {
        if (showTvRight) {
            if (tvRightStr != null) {
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
     * 设置标题右边的文字
     *
     * @param tvRightAntherStr       文本内容
     * @param tvRightAntherTextColor 文本颜色
     * @param tvRightAntherTextSize  文字大小
     * @param showTvRightAnther      实现显示右边文字
     */
    @SuppressLint("NewApi")
    public void initTvRightAnther(String tvRightAntherStr, int tvRightAntherTextColor, int tvRightAntherTextSize, boolean showTvRightAnther) {
        if (showTvRightAnther) {
            if (tvRightAntherStr != null) {
                tv_right_anther.setTextColor(this.getResources().getColor(tvRightAntherTextColor));
                tv_right_anther.setText(tvRightAntherStr);
                tv_right_anther.setTextSize(tvRightAntherTextSize);
                tv_right_anther.setVisibility(View.VISIBLE);
            } else {
                tv_right_anther.setVisibility(View.GONE);
            }
        } else {
            tv_right_anther.setVisibility(View.GONE);
        }
    }


    /**
     * 设置标题左边文字
     *
     * @param tvLeftStr       文本内容
     * @param tvLeftTextColor 文本颜色
     * @param tvLeftTextSize  文字大小
     * @param showTvLeft      实现显示左边文字
     */
    @SuppressLint("NewApi")
    public void initTvLeft(String tvLeftStr, int tvLeftTextColor, int tvLeftTextSize, boolean showTvLeft) {
        if (showTvLeft) {
            if (tvLeftStr != null) {
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
     * 设置标题左边图片
     *
     * @param imgLeftRes  资源图片 R.mimap.xx
     * @param showImgLeft 是否显示左边图片
     */
    @SuppressLint("NewApi")
    public void initImgLeft(int imgLeftRes, boolean showImgLeft) {
        if (showImgLeft) {
            if (imgLeftRes != 0) {
                img_left.setVisibility(View.VISIBLE);
                img_left.setImageDrawable(getResources().getDrawable(imgLeftRes));
            } else {
                img_left.setVisibility(View.GONE);
            }
        } else {
            img_left.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题的名字
     *
     * @param titleStr
     * @param titleTextSize
     * @param titleTextColor
     * @param showTitle
     */
    public void initToolBarTitle(String titleStr, int titleTextSize, int titleTextColor, boolean showTitle) {
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
     * 只设置标题的名字内容
     *
     * @param titleStr
     */
    public void setToolBarTitle(String titleStr) {
        title.setText(titleStr);
    }


    /**
     * 设置标题栏颜色和状态栏颜色
     *
     * @param toolbarBgColorValue 标题栏背景颜色
     * @param isLight             修改状态栏文字图标颜色
     */
    public void initToolBarBgColor(int toolbarBgColorValue, boolean isLight) {
        title_bar_bg.setBackgroundColor(toolbarBgColorValue);
        StatusBarUtil.setColorForSwipeBack(this, toolbarBgColorValue, 0);
        if (isLight) {
            StatusBarUtil.setLightMode(this);
        } else {
            StatusBarUtil.setDarkMode(this);
        }
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
