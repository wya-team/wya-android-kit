package com.wya.example.module.uikit.tabbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.uikit.tabbar.TabBar;

public class TabBarExampleActivity extends AppCompatActivity {

	private TabBar tab;
	private TextView msg;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_bar_example);
		tab = findViewById(R.id.tab);
		msg = findViewById(R.id.message);
		//取消动画
		tab.disableShiftMode();
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
}
