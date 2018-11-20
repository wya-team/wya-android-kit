package com.wya.example.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wya.uikit.toolbar.BaseToolBarActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 创建日期：2018/11/16 17:48
 * 作者： Mao Chunjiang
 * 文件名称： BaseActivity
 * 类说明：
 */

public abstract class BaseActivity extends BaseToolBarActivity {
    private Unbinder unbinder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        unbinder = ButterKnife.bind(this);
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
//            /**状态栏：5.1(不包含5.1)以上改为全屏，背景白色*/
//            StatusUtil.setStatusBarColor(this, android.R.color.white);
//            StatusUtil.setStatue(this, true);
//        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {/**5.1及5.1以下改为灰色*/
//            StatusUtil.setStatusBarColor(this, android.R.color.black);
//        }
        initView();
    }

    protected abstract void initView();

    protected abstract int getLayoutID();

}
