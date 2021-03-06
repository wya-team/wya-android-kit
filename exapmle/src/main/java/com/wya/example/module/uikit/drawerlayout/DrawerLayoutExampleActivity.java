package com.wya.example.module.uikit.drawerlayout;


import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DrawerLayoutExampleActivity extends BaseActivity {

    @BindView(R.id.main_left_drawer_layout)
    RelativeLayout mainLeftDrawerLayout;
    @BindView(R.id.main_right_drawer_layout)
    RelativeLayout mainRightDrawerLayout;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_drawer_layout_example;
    }

    @Override
    protected void initView() {
        setToolBarTitle("抽屉(drawerlayout)");
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
        }
    }
}
