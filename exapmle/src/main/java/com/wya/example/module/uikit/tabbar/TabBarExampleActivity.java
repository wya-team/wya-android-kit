package com.wya.example.module.uikit.tabbar;

import android.support.design.internal.BottomNavigationItemView;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.badge.view.Builder;
import com.wya.uikit.badge.view.IBadgeView;
import com.wya.uikit.tabbar.WYATabBar;

public class TabBarExampleActivity extends BaseActivity {
    
    private WYATabBar tab;
    private TextView msg;
    
    @Override
    protected void initView() {
        setToolBarTitle("TabBar");
        setToolBar();
        showBadge(2, 999);
    }
    
    public void showBadge(int index, int num) {
        BottomNavigationItemView[] itemViews = tab.getBottomNavigationItemViews();
        if (index < 0 || index >= tab.getBottomNavigationItemViews().length) {
            return;
        }
        
        BottomNavigationItemView itemView = itemViews[index];
        IBadgeView badgeView = new Builder(this)
                .setOffset(50, 0)
                .setBadgeNum(num)
                .create();
        badgeView.bindToTarget(itemView);
    }
    
    private void setToolBar() {
        tab = findViewById(R.id.tab);
        msg = findViewById(R.id.message);
        //取消偏移
        tab.disableShiftMode();
        //取消item动画
        tab.enableAnimation(false);
        //item点击监听
        tab.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    msg.setText("首页");
                    break;
                case R.id.navigation_dashboard:
                    msg.setText("分类");
                    break;
                case R.id.navigation_notifications:
                    msg.setText("消息");
                    break;
                case R.id.navigation_notifications1:
                    msg.setText("消息2");
                    break;
            }
            return true;
        });
    }
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_tab_bar_example;
    }
}
