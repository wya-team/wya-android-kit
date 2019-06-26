package com.wya.example.module.example;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.weiyian.android.router.facade.annotation.Autowired;
import com.weiyian.android.router.facade.annotation.Route;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.fragment.AboutUsFragment;
import com.wya.example.module.example.fragment.ExampleFragment;
import com.wya.uikit.tabbar.WYATabBar;

import butterknife.BindView;

/**
 * @date: 2019/1/4 11:49
 * @author: Chunjiang Mao
 * @classname: ExampleActivity
 * @describe: ExampleActivity
 */

@Route(path = "main/main")
public class ExampleActivity extends BaseActivity {
    
    @BindView(R.id.tab)
    WYATabBar tab;
    
    private AboutUsFragment aboutUsFragment;
    private ExampleFragment exampleFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    
    @Autowired
    public String key;
    
    @Override
    protected void initView() {
        showToolBar(false);
        initFragment();
        setTabBar();
        getSwipeBackLayout().setEnableGesture(false);
        setBackgroundColor(R.color.f4f4f4, true);
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
        // 取消偏移
        tab.disableShiftMode();
        // 取消item动画
        tab.enableAnimation(false);
        // item点击监听
        tab.setOnNavigationItemSelectedListener(item -> {
            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_example:
                    fragmentTransaction.show(exampleFragment).hide(aboutUsFragment).commit();
                    break;
                case R.id.navigation_about_us:
                    fragmentTransaction.show(aboutUsFragment).hide(exampleFragment).commit();
                    break;
                default:
                    break;
            }
            return true;
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    
}
