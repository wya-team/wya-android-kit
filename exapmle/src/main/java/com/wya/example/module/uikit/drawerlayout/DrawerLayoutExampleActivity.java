package com.wya.example.module.uikit.drawerlayout;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date: 2019/1/8 9:56
 * @author: Chunjiang Mao
 * @classname: DrawerLayoutExampleActivity
 * @describe:
 */

public class DrawerLayoutExampleActivity extends BaseActivity {
    
    @BindView(R.id.main_left_drawer_layout)
    RelativeLayout mainLeftDrawerLayout;
    @BindView(R.id.main_right_drawer_layout)
    RelativeLayout mainRightDrawerLayout;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawer_layout_example;
    }
    
    @Override
    protected void initView() {
        setTitle("抽屉(drawerlayout)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(DrawerLayoutExampleActivity.this, ReadmeActivity.class)
                    .putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(DrawerLayoutExampleActivity.this, url);
        });
        
        getSwipeBackLayout().setEnableGesture(false);
        setDrawerLayout();
    }
    
    private void setDrawerLayout() {
        mainDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            
            @Override
            public void onDrawerOpened(View drawerView) {
            }
            
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }
    
    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                mainDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.btn_right:
                mainDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
            default:
                break;
        }
    }
}
