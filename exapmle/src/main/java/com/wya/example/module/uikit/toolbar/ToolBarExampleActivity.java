package com.wya.example.module.uikit.toolbar;

import android.content.Intent;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

import butterknife.OnClick;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @date: 2018/11/20 15:00
 * @author: Chunjiang Mao
 * @classname: ToolBarExampleActivity
 * @describe: ToolBar的使用案例
 */

public class ToolBarExampleActivity extends BaseActivity {

    @Override
    protected void initView() {
        setTitle("导航栏(toolbar)");
        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(ToolBarExampleActivity.this, ReadmeActivity.class).putExtra(EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            showShort("链接地址复制成功");
            StringUtil.copyString(ToolBarExampleActivity.this, url);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tool_bar_example;
    }

    @OnClick({R.id.radio_toolbar_show, R.id.radio_toolbar_unshow, R.id.radio_right_anther_show, R.id.radio_right_anther_unshow, R.id.radio_red, R.id.radio_blue, R.id.radio_greeen, R.id.radio_left_show, R.id.radio_left_unshow, R.id.radio_tv_left_show, R.id.radio_tv_left_unshow, R.id.radio_right_show, R.id.radio_right_unshow, R.id.radio_tv_right_show, R.id.radio_tv_right_unshow, R.id.radio_tv_right_anther_show, R.id.radio_tv_right_anther_unshow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_toolbar_show:
                showToolBar(true);
                break;
            case R.id.radio_toolbar_unshow:
                showToolBar(false);
                break;
            case R.id.radio_red:
                setBackgroundColor(this.getResources().getColor(R.color.red), false);
                break;
            case R.id.radio_blue:
                setBackgroundColor(this.getResources().getColor(R.color.blue), false);
                break;
            case R.id.radio_greeen:
                setBackgroundColor(this.getResources().getColor(R.color.green), true);
                break;
            case R.id.radio_left_show:
                showLeftIcon(true);
                break;
            case R.id.radio_left_unshow:
                showLeftIcon(false);
                break;
            case R.id.radio_tv_left_show:
                showLeftText(true);
                setLeftText("返回");
                break;
            case R.id.radio_tv_left_unshow:
                showLeftText(false);
                setLeftText("返回");
                break;
            case R.id.radio_right_show:
                showSecondRightIcon(true);
                setSecondRightIcon(R.drawable.icon_search);
                break;
            case R.id.radio_right_unshow:
                showSecondRightIcon(false);
                setSecondRightIcon(R.drawable.icon_search);
                break;
            case R.id.radio_right_anther_show:
                showFirstRightIcon(true);
                setFirstRightIcon(R.drawable.iocn_saoyisao);
                break;
            case R.id.radio_right_anther_unshow:
                showFirstRightIcon(false);
                setFirstRightIcon(R.drawable.iocn_saoyisao);
                break;
            case R.id.radio_tv_right_show:
                showFirstRightText(true);
                setFirstRightText("右1");
                break;
            case R.id.radio_tv_right_unshow:
                showFirstRightText(false);
                setFirstRightText("右1");
                break;
            case R.id.radio_tv_right_anther_show:
                showSecondRightText(true);
                setSecondRightText("右2");
                break;
            case R.id.radio_tv_right_anther_unshow:
                showSecondRightText(false);
                setSecondRightText("右2");
                break;
            default:
                break;
        }
    }

}
