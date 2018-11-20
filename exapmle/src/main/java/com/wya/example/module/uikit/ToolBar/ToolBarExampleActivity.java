package com.wya.example.module.uikit.ToolBar;

import android.view.View;
import android.widget.RadioButton;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_tool_bar_example;
    }

    @OnClick({R.id.radio_red, R.id.radio_blue, R.id.radio_greeen, R.id.radio_left_show, R.id.radio_left_unshow, R.id.radio_tv_left_show, R.id.radio_tv_left_unshow, R.id.radio_right_show, R.id.radio_right_unshow, R.id.radio_tv_right_show, R.id.radio_tv_right_unshow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_red:
                initToolBarBgColor("#FF0000");
                break;
            case R.id.radio_blue:
                initToolBarBgColor("#0000FF");
                break;
            case R.id.radio_greeen:
                initToolBarBgColor("#00FF00");
                break;
            case R.id.radio_left_show:
                initLeft(R.mipmap.icon_back_white, "返回", R.color.white, true,14, false);
                break;
            case R.id.radio_left_unshow:
                initLeft(R.mipmap.icon_back_white, "返回", R.color.white, false,14, false);
                break;
            case R.id.radio_tv_left_show:
                initLeft(R.mipmap.icon_back_white, "返回", R.color.white, false,14, true);
                break;
            case R.id.radio_tv_left_unshow:
                initLeft(R.mipmap.icon_back_white, "返回", R.color.white, false,14, false);
                break;
            case R.id.radio_right_show:
                initRight(R.mipmap.ic_launcher_round, "右边", R.color.white, true,14, false);
                break;
            case R.id.radio_right_unshow:
                initRight(R.mipmap.ic_launcher_round, "右边", R.color.white, false,14, false);
                break;
            case R.id.radio_tv_right_show:
                initRight(R.mipmap.icon_back_white, "右边", R.color.white, false,14, true);
                break;
            case R.id.radio_tv_right_unshow:
                initRight(R.mipmap.icon_back_white, "右边", R.color.white, false,14, false);
                break;
        }
    }
}
