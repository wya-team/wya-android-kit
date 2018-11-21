package com.wya.example.module.uikit;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.dialog.DialogExampleActivity;
import com.wya.example.module.uikit.segmentedcontrol.SegmentedControlExampleActivity;
import com.wya.example.module.uikit.toolbar.ToolBarExampleActivity;
import com.wya.example.module.uikit.tabbar.TabBarExampleActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class UiKitExampleActivity extends BaseActivity {

	@BindView(R.id.tv_tool_bar)
	TextView tvToolBar;
	@BindView(R.id.tv_dialog)
	TextView tvDialog;
	@BindView(R.id.tv_tab_bar)
	TextView tvTabBar;
	@BindView(R.id.tv_tab_layout)
	TextView tvTabLayout;

	private Intent intent = null;

	@Override
	protected void initView() {
		initImgLeft(R.mipmap.icon_back_white, true);
		setLeftOnclickListener(new onLeftOnclickListener() {
			@Override
			public void onLeftClick() {
				Toast.makeText(UiKitExampleActivity.this, "监听返回", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected int getLayoutID() {
		return R.layout.activity_ui_kit_example;
	}


	@OnClick({R.id.tv_tool_bar, R.id.tv_tab_bar, R.id.tv_tab_layout, R.id.tv_dialog})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.tv_tool_bar:
				intent = new Intent(UiKitExampleActivity.this, ToolBarExampleActivity.class);
				UiKitExampleActivity.this.startActivity(intent);
				break;
			case R.id.tv_tab_bar:
				intent = new Intent(this, TabBarExampleActivity
						.class);
				startActivity(intent);
				break;
			case R.id.tv_tab_layout:
				intent = new Intent(this, SegmentedControlExampleActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_dialog:
				intent = new Intent(UiKitExampleActivity.this, DialogExampleActivity.class);
				UiKitExampleActivity.this.startActivity(intent);
				break;
		}
	}
}
