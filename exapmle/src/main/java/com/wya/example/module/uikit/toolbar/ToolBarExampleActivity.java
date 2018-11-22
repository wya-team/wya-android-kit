package com.wya.example.module.uikit.toolbar;

import android.view.View;
import android.widget.RadioButton;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/11/20 15:00
 * 作者： Mao Chunjiang
 * 文件名称：ToolBarExampleActivity
 * 类说明：ToolBar的使用案例
 */

public class ToolBarExampleActivity extends BaseActivity {

    @BindView(R.id.radio_red)
    RadioButton radioRed;
    @BindView(R.id.radio_blue)
    RadioButton radioBlue;
    @BindView(R.id.radio_greeen)
    RadioButton radioGreeen;
    @BindView(R.id.radio_left_show)
    RadioButton radioLeftShow;
    @BindView(R.id.radio_left_unshow)
    RadioButton radioLeftUnshow;
    @BindView(R.id.radio_tv_left_show)
    RadioButton radioTvLeftShow;
    @BindView(R.id.radio_tv_left_unshow)
    RadioButton radioTvLeftUnshow;
    @BindView(R.id.radio_right_show)
    RadioButton radioRightShow;
    @BindView(R.id.radio_right_unshow)
    RadioButton radioRightUnshow;
    @BindView(R.id.radio_tv_right_show)
    RadioButton radioTvRightShow;
    @BindView(R.id.radio_tv_right_unshow)
    RadioButton radioTvRightUnshow;
    @BindView(R.id.radio_right_anther_show)
    RadioButton radioRightAntherShow;
    @BindView(R.id.radio_right_anther_unshow)
    RadioButton radioRightAntherUnshow;
    @BindView(R.id.radio_toolbar_show)
    RadioButton radioToolbarShow;
    @BindView(R.id.radio_toolbar_unshow)
    RadioButton radioToolbarUnshow;

    @Override
    protected void initView() {
        setToolBarTitle("ToolBar");
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_tool_bar_example;
    }

    @OnClick({R.id.radio_toolbar_show, R.id.radio_toolbar_unshow, R.id.radio_right_anther_show, R.id.radio_right_anther_unshow, R.id.radio_red, R.id.radio_blue, R.id.radio_greeen, R.id.radio_left_show, R.id.radio_left_unshow, R.id.radio_tv_left_show, R.id.radio_tv_left_unshow, R.id.radio_right_show, R.id.radio_right_unshow, R.id.radio_tv_right_show, R.id.radio_tv_right_unshow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_toolbar_show:
                initShowToolBar(true);
                break;
            case R.id.radio_toolbar_unshow:
                initShowToolBar(false);
                break;
            case R.id.radio_red:
                initToolBarBgColor(this.getResources().getColor(R.color.red));
                break;
            case R.id.radio_blue:
                initToolBarBgColor(this.getResources().getColor(R.color.blue));
                break;
            case R.id.radio_greeen:
                initToolBarBgColor(this.getResources().getColor(R.color.green));
                break;
            case R.id.radio_left_show:
                initImgLeft(R.mipmap.icon_back_white, true);
                break;
            case R.id.radio_left_unshow:
                initImgLeft(R.mipmap.icon_back_white, false);
                break;
            case R.id.radio_tv_left_show:
                initTvLeft("返回", R.color.white, 14, true);
                break;
            case R.id.radio_tv_left_unshow:
                initTvLeft("返回", R.color.white, 14, false);
                break;
            case R.id.radio_right_show:
                initImgRight(R.mipmap.ic_launcher_round, true, 0, false);
                break;
            case R.id.radio_right_unshow:
                initImgRight(R.mipmap.ic_launcher_round, false, 0, false);
                break;
            case R.id.radio_right_anther_show:
                initImgRight(R.mipmap.ic_launcher_round, true, R.mipmap.ic_launcher_round, true);
                break;
            case R.id.radio_right_anther_unshow:
                initImgRight(R.mipmap.ic_launcher_round, false, R.mipmap.ic_launcher_round, false);
                break;
            case R.id.radio_tv_right_show:
                initTvRight("右边", R.color.white, 14, true);
                break;
            case R.id.radio_tv_right_unshow:
                initTvRight("右边", R.color.white, 14, false);
                break;
        }
    }

}
