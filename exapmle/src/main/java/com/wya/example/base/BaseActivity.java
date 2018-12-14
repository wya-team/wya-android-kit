package com.wya.example.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wya.example.R;
import com.wya.helper.WYAConstants;
import com.wya.uikit.toast.WYAToast;
import com.wya.uikit.toolbar.BaseToolBarActivity;
import com.wya.utils.utils.ColorUtil;

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
        unbinder = ButterKnife.bind(this);
//        initWYAActionBarDefault(true, ColorUtil.int2Hex(WYAConstants.THEME_COLOR),true, "初始化标题", 18, "#ffffff",
//        false, "", 14, "#ffffff", true, R.mipmap.icon_back_white,
//        false, "",14, "#ffffff", false, false, R.mipmap.icon_nav_more, 0);
        initView();
    }


    public WYAToast getWyaToast() {
        return new WYAToast(this);
    }

    protected abstract void initView();

}
