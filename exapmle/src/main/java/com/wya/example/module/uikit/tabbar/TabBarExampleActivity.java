package com.wya.example.module.uikit.tabbar;

import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.tabbar.TabBar;

public class TabBarExampleActivity extends BaseActivity {

	private TabBar tab;
	private TextView msg;

	@Override
	protected void initView() {
		setToolBarTitle("TabBar");
		setToolBar();
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
