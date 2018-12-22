package com.wya.example.module.uikit.toolbar;

import android.content.Intent;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;

import butterknife.OnClick;

/**
 * 创建日期：2018/11/20 15:00
 * 作者： Mao Chunjiang
 * 文件名称：ToolBarExampleActivity
 * 类说明：ToolBar的使用案例
 */

public class ToolBarExampleActivity extends BaseActivity {

    @Override
    protected void initView() {
        setToolBarTitle("导航栏(toolbar)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(ToolBarExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_tool_bar_example;
    }

    @OnClick({R.id.radio_toolbar_show, R.id.radio_toolbar_unshow, R.id.radio_right_anther_show, R.id.radio_right_anther_unshow, R.id.radio_red, R.id.radio_blue, R.id.radio_greeen, R.id.radio_left_show, R.id.radio_left_unshow, R.id.radio_tv_left_show, R.id.radio_tv_left_unshow, R.id.radio_right_show, R.id.radio_right_unshow, R.id.radio_tv_right_show, R.id.radio_tv_right_unshow, R.id.radio_tv_right_anther_show, R.id.radio_tv_right_anther_unshow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_toolbar_show:
                initShowToolBar(true);
                break;
            case R.id.radio_toolbar_unshow:
                initShowToolBar(false);
                break;
            case R.id.radio_red:
                initToolBarBgColor(this.getResources().getColor(R.color.red), false);
                break;
            case R.id.radio_blue:
                initToolBarBgColor(this.getResources().getColor(R.color.blue), false);
                break;
            case R.id.radio_greeen:
                initToolBarBgColor(this.getResources().getColor(R.color.green), true);
                break;
            case R.id.radio_left_show:
                initImgLeft(R.drawable.icon_backblue, true);
                break;
            case R.id.radio_left_unshow:
                initImgLeft(R.drawable.icon_backblue, false);
                break;
            case R.id.radio_tv_left_show:
                initTvLeft("返回", R.color.white, 14, true);
                break;
            case R.id.radio_tv_left_unshow:
                initTvLeft("返回", R.color.white, 14, false);
                break;
            case R.id.radio_right_show:
                initImgRight(R.drawable.icon_search, true);
                break;
            case R.id.radio_right_unshow:
                initImgRight(R.drawable.icon_search, false);
                break;
            case R.id.radio_right_anther_show:
                initImgRightAnther(R.drawable.iocn_saoyisao, true);
                break;
            case R.id.radio_right_anther_unshow:
                initImgRightAnther(R.drawable.iocn_saoyisao, false);
                break;
            case R.id.radio_tv_right_show:
                initTvRight("右边", R.color.white, 14, true);
                break;
            case R.id.radio_tv_right_unshow:
                initTvRight("右边", R.color.white, 14, false);
                break;
            case R.id.radio_tv_right_anther_show:
                initTvRightAnther("右边", R.color.white, 14, true);
                break;
            case R.id.radio_tv_right_anther_unshow:
                initTvRightAnther("右边", R.color.white, 14, false);
                break;
        }
    }

}
