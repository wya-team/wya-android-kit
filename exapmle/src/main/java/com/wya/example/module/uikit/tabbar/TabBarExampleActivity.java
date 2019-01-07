package com.wya.example.module.uikit.tabbar;

import android.content.Intent;
import android.support.design.internal.BottomNavigationItemView;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.badge.BadgeGravity;
import com.wya.uikit.badge.Builder;
import com.wya.uikit.badge.IBadgeView;
import com.wya.uikit.slidder.Utils;
import com.wya.uikit.tabbar.WYATabBar;
import com.wya.utils.utils.StringUtil;

public class TabBarExampleActivity extends BaseActivity {
    
    private WYATabBar tab;
    private TextView msg;
    private IBadgeView badgeHome, badgeMy, badgeNotifications;
    
    @Override
    protected void initView() {
        setTitle("底部导航(tabbar)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(TabBarExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(TabBarExampleActivity.this, url);
        });
        setToolBar();
        showBadge();
    }
    
    public void showBadge() {
        BottomNavigationItemView[] itemViews = tab.getBottomNavigationItemViews();
        
        BottomNavigationItemView itemViewHome = itemViews[0];
        badgeHome = new Builder(this)
                .setGravity(new Builder.Gravity(BadgeGravity.GRAVITY_CENTER_TOP, Utils.dp2px(this, 20), Utils.dp2px(this, 1)))
                .setText("new")
                .create();
        badgeHome.bindToTarget(itemViewHome);
        
        BottomNavigationItemView itemViewNotifications = itemViews[2];
        badgeNotifications = new Builder(this)
                .setGravity(new Builder.Gravity(BadgeGravity.GRAVITY_CENTER_TOP, Utils.dp2px(this, 11), Utils.dp2px(this, 1)))
                .setBadgeNum(1)
                .create();
        badgeNotifications.bindToTarget(itemViewNotifications);
    
        BottomNavigationItemView itemViewMy = itemViews[3];
        badgeMy = new Builder(this)
                .setGravity(new Builder.Gravity(BadgeGravity.GRAVITY_CENTER_TOP, Utils.dp2px(this, 11), Utils.dp2px(this, 4)))
                .setBadgeNum(0)
                .create();
        badgeMy.bindToTarget(itemViewMy);
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
                    if (null != badgeHome) {
                        badgeHome.updateIsShow(false);
                    }
                    break;
                case R.id.navigation_dashboard:
                    msg.setText("点击了\"朋友\"tab，这里是朋友界面");
                    break;
                case R.id.navigation_notifications:
                    msg.setText("点击了\"消息\"tab，这里是消息界面");
                    if (null != badgeNotifications) {
                        badgeNotifications.updateIsShow(false);
                    }
                    break;
                case R.id.navigation_my:
                    msg.setText("点击了\"我的\"tab，这里是我的界面");
                    if (null != badgeMy) {
                        badgeMy.updateIsShow(false);
                    }
                    break;

                default:
                    break;
            }
            return true;
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab_bar_example;
    }
}
