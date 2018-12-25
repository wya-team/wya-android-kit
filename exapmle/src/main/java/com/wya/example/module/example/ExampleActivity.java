package com.wya.example.module.example;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.fragment.AboutUsFragment;
import com.wya.example.module.example.fragment.ExampleFragment;
import com.wya.uikit.tabbar.WYATabBar;
import com.wya.utils.utils.ColorUtil;

import butterknife.BindView;

public class ExampleActivity extends BaseActivity {

    @BindView(R.id.tab)
    WYATabBar tab;

    private AboutUsFragment aboutUsFragment;
    private ExampleFragment exampleFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void initView() {
        initShowToolBar(false);
        initFragment();
        setTabBar();
        getSwipeBackLayout().setEnableGesture(false);
        initToolBarBgColor(ColorUtil.hex2Int("#f4f4f4"), true);
    }

    private void initFragment() {
        exampleFragment = new ExampleFragment();
        aboutUsFragment = new AboutUsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, exampleFragment);
        fragmentTransaction.add(R.id.content, aboutUsFragment);
        fragmentTransaction.show(exampleFragment).hide(aboutUsFragment).commit();
    }

    private void setTabBar() {
        //取消偏移
        tab.disableShiftMode();
        //取消item动画
        tab.enableAnimation(false);
        //item点击监听
        tab.setOnNavigationItemSelectedListener(item -> {
            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_example:
                    fragmentTransaction.show(exampleFragment).hide(aboutUsFragment).commit();
                    break;
                case R.id.navigation_about_us:
                    fragmentTransaction.show(aboutUsFragment).hide(exampleFragment).commit();
                    break;
            }
            return true;
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("test", "onStop: -----");
    }
}
