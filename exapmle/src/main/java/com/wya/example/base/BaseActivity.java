package com.wya.example.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.wya.example.R;
import com.wya.example.module.utils.GestureFlingRightHelper;
import com.wya.helper.WYAConstants;
import com.wya.uikit.toast.WYAToast;
import com.wya.uikit.toolbar.BaseToolBarActivity;
import com.wya.utils.utils.ColorUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date: 2019/1/4 9:49
 * @author: Chunjiang Mao
 * @classname: BaseActivity
 * @describe:
 */

public abstract class BaseActivity extends BaseToolBarActivity {
    private Unbinder unbinder;
    private GestureDetector mGestureDetector;
    private boolean mIsSwipeBack = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        startActivityStyle();
//        initWYAActionBarDefault(true, ColorUtil.int2Hex(WYAConstants.THEME_COLOR), true, "初始化标题", 18, "#000000",
//                false, "", 14, "#000000", true, R.drawable.icon_backblue,
//                false, "", 14, "#000000", false, false, R.drawable.icon_search, R.drawable.iocn_saoyisao, false, "", 14, "#000000", false);
//        initToolBarBgColor(ColorUtil.hex2Int("#ffffff"), true);
        setLeftIcon(R.drawable.icon_backblue);
        setBackgroundColor(ColorUtil.hex2Int("#ffffff"), true );
        initView();
        initGesture();
    }

    private void startActivityStyle() {
        overridePendingTransition(R.anim.activity_start_right, R.anim.activity_start_left);
    }

    public WYAToast getWyaToast() {
        return new WYAToast(this);
    }

    /**
     * 初始化view
     */
    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.activity_start_left, R.anim.activity_start_left_exit);
    }

    @SuppressWarnings("deprecation")
    private void initGesture() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mGestureDetector = GestureFlingRightHelper.getInstance().getGestureDetector(() -> {
            if (mIsSwipeBack) {
                finish();
                return true;
            } else {
                return false;
            }
        }, outMetrics.widthPixels);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mGestureDetector != null ? (mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev)) : super.dispatchTouchEvent(ev);
    }

    public void setSwipeBack(boolean swipeBack) {
        this.mIsSwipeBack = swipeBack;
    }

}
