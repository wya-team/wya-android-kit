package com.wya.uikit.toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;
import com.wya.uikit.toolbar.swipebacklayout.BGASwipeBackHelper;
import com.wya.uikit.toolbar.swipebacklayout.StatusBarUtil;

/**
 * 创建日期：2018/11/16 17:48
 * 作者： Mao Chunjiang
 * 文件名称： BaseToolBarActivity
 * 类说明：标题栏
 */

public abstract class BaseToolBarActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate {

    private WYAToolBarHelper wyaToolBarHelper;
    private TextView title, tv_left, tv_right;
    private LinearLayout ll_right, ll_left;
    private ImageView img_right, img_left, img_right_anther;
    private RelativeLayout title_bar_bg;

    private LinearLayout parentLinearLayout;

    protected abstract int getLayoutID();


    private onLeftOnclickListener onLeftOnclickListener;//设置左边点击事件
    private onRightOnclickListener onRightOnclickListener;//设置右边点击事件


    /**
     * 设置左边点击事件
     */
    public interface onLeftOnclickListener {
        void onLeftClick(View view);
    }

    /**
     * 设置右边点击事件
     */
    public interface onRightOnclickListener {
        void onRightClick(View view);
    }


    /**
     * 设置左边点击事件内容和监听
     *
     * @param onLeftOnclickListener
     */
    public void setLeftOnclickListener(onLeftOnclickListener onLeftOnclickListener) {
        this.onLeftOnclickListener = onLeftOnclickListener;
    }

    /**
     * 设置右边边点击事件内容和监听
     *
     * @param onRightOnclickListener
     */
    public void setRightOnclickListener(onRightOnclickListener onRightOnclickListener) {
        this.onRightOnclickListener = onRightOnclickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        initContentView(R.layout.base_toolbar_layout);
        setContentView(getLayoutID());
        initWYAActionBar();
    }


    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    protected BGASwipeBackHelper mSwipeBackHelper;
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }


    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }


    public void initWYAActionBarDefault(boolean showToolBar, String toolbarBgColorValue, boolean isShowTitle, String titleStr, int titleTextSize, String titleTextColorValue,
                                        boolean isShowTvLeft, String tvLeftStr, int tvLeftTextSize, String tvLeftTextColorValue, boolean isShowImgLeft, int imgLeftRes,
                                        boolean isShowTvRight, String tvRightStr, int tvRightTextSize, String tvRightTextColorValue, boolean isShowImgRight, boolean isShowImgRightAnther, int imgRightRes, int imgRightResAnther) {
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
        wyaToolBarHelper.setShowImgRight(isShowImgRight);
        wyaToolBarHelper.setImgRightRes(imgRightRes);
        wyaToolBarHelper.setImgRightResAnther(imgRightResAnther);
        wyaToolBarHelper.setShowImgRightAnther(isShowImgRightAnther);

        initShowToolBar(wyaToolBarHelper.isShowTitle());
        initToolBarBgColor(wyaToolBarHelper.getToolbar_bg_color());
        initToolBarTitle(wyaToolBarHelper.getTitleStr(), wyaToolBarHelper.getTitleTextSize(), wyaToolBarHelper.getTitleTextColor(), wyaToolBarHelper.isShowTitle());
        initTvLeft(wyaToolBarHelper.getTvLeftStr(), wyaToolBarHelper.getTvLeftTextColor(), wyaToolBarHelper.getTvLeftTextSize(), wyaToolBarHelper.isShowTvLeft());
        initImgLeft(wyaToolBarHelper.getImgLeftRes(), wyaToolBarHelper.isShowImgLeft());
        initTvRight(wyaToolBarHelper.getTvRightStr(), wyaToolBarHelper.getTvRightTextColor(), wyaToolBarHelper.getTvRightTextSize(), wyaToolBarHelper.isShowTvRight());
        initImgRight(wyaToolBarHelper.getImgRightRes(), wyaToolBarHelper.isShowImgRight(), wyaToolBarHelper.getImgRightResAnther(), wyaToolBarHelper.isShowImgRightAnther());
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
        ll_right = (LinearLayout) findViewById(R.id.ll_right);
        ll_left = (LinearLayout) findViewById(R.id.ll_left);
        img_right = (ImageView) findViewById(R.id.img_right);
        img_right_anther = (ImageView) findViewById(R.id.img_right_anther);
        img_left = (ImageView) findViewById(R.id.img_left);
        title_bar_bg = (RelativeLayout) findViewById(R.id.title_bar_bg);

        initShowToolBar(wyaToolBarHelper.isShowTitle());
        initToolBarBgColor(wyaToolBarHelper.getToolbar_bg_color());
        initToolBarTitle(wyaToolBarHelper.getTitleStr(), wyaToolBarHelper.getTitleTextSize(), wyaToolBarHelper.getTitleTextColor(), wyaToolBarHelper.isShowTitle());
        initTvLeft(wyaToolBarHelper.getTvLeftStr(), wyaToolBarHelper.getTvLeftTextColor(), wyaToolBarHelper.getTvLeftTextSize(), wyaToolBarHelper.isShowTvLeft());
        initImgLeft(wyaToolBarHelper.getImgLeftRes(), wyaToolBarHelper.isShowImgLeft());
        initTvRight(wyaToolBarHelper.getTvRightStr(), wyaToolBarHelper.getTvRightTextColor(), wyaToolBarHelper.getTvRightTextSize(), wyaToolBarHelper.isShowTvRight());
        initImgRight(wyaToolBarHelper.getImgRightRes(), wyaToolBarHelper.isShowImgRight(), wyaToolBarHelper.getImgRightResAnther(), wyaToolBarHelper.isShowImgRightAnther());
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
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLeftOnclickListener != null) {
                    onLeftOnclickListener.onLeftClick(view);
                } else {
                    BaseToolBarActivity.this.finish();
                }
            }
        });
        ll_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightOnclickListener != null) {
                    onRightOnclickListener.onRightClick(view);
                } else {
                    Toast.makeText(BaseToolBarActivity.this, "右边", Toast.LENGTH_SHORT).show();
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
    public void initImgRight(int imgRightRes, boolean showImgRight, int imgRightAntherRes, boolean showImgAnther) {
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
        if (showImgAnther) {
            if (imgRightAntherRes != 0) {
                img_right_anther.setVisibility(View.VISIBLE);
                img_right_anther.setBackground(getResources().getDrawable(imgRightAntherRes));
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
                img_left.setBackground(getResources().getDrawable(imgLeftRes));
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
     * @param titleStr
     */
    public void setToolBarTitle(String titleStr) {
        title.setText(titleStr);
    }



    /**
     * 初始标题栏颜色
     *
     * @param toolbarBgColorValue 标题栏背景颜色
     */
    public void initToolBarBgColor(int toolbarBgColorValue) {
        title_bar_bg.setBackgroundColor(toolbarBgColorValue);
        StatusBarUtil.setColorForSwipeBack(this, toolbarBgColorValue, 0);
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
