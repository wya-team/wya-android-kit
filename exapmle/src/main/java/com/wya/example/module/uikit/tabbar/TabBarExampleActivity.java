package com.wya.example.module.uikit.tabbar;

import android.support.design.internal.BottomNavigationItemView;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.badge.Builder;
import com.wya.uikit.badge.IBadgeView;
import com.wya.uikit.tabbar.WYATabBar;

public class TabBarExampleActivity extends BaseActivity {

    private WYATabBar tab;
    private TextView msg;

    @Override
    protected void initView() {
        setToolBarTitle("TabBar");
        setToolBar();
        showBadge();
    }

    public void showBadge() {
        BottomNavigationItemView[] itemViews = tab.getBottomNavigationItemViews();

        BottomNavigationItemView itemView = itemViews[0];
        IBadgeView badgeView = new Builder(this)
                .setOffset(50, 10)
                .setText("news")
                .create();
        badgeView.bindToTarget(itemView);

        BottomNavigationItemView itemView1 = itemViews[1];
        IBadgeView badgeView1 = new Builder(this)
                .setOffset(120, 20)
                .setBadgeNum(0)
                .create();
        badgeView1.bindToTarget(itemView1);

        BottomNavigationItemView itemView2 = itemViews[2];
        IBadgeView badgeView2 = new Builder(this)
                .setOffset(80, 10)
                .setBadgeNum(1)
                .create();
        badgeView2.bindToTarget(itemView2);
    }

    private void setToolBar() {
        tab = (WYATabBar) findViewById(R.id.tab);
        msg = (TextView) findViewById(R.id.msg);
        //取消偏移
        tab.disableShiftMode();
        //取消item动画
        tab.enableAnimation(false);
        tab.setSelectedItemId(R.id.navigation_dashboard);
        msg.setText("点击了\"朋友\"tab，这里是朋友界面");
        //item点击监听
        tab.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    msg.setText("点击了\"主页\"tab，这里是主页界面");
                    break;
                case R.id.navigation_dashboard:
                    msg.setText("点击了\"朋友\"tab，这里是朋友界面");
                    break;
                case R.id.navigation_notifications:
                    msg.setText("点击了\"消息\"tab，这里是消息界面");
                    break;
                case R.id.navigation_my:
                    msg.setText("点击了\"我的\"tab，这里是消息界面");
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
