package com.wya.example.module.uikit;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.dialog.DialogExampleActivity;
import com.wya.example.module.uikit.keyboard.KeyboardExampleActivity;
import com.wya.example.module.uikit.paginationview.PaginationViewExampleActivity;
import com.wya.example.module.uikit.popupwindow.PopupWindowExampleActivity;
import com.wya.example.module.uikit.searchbar.SearchBarExampleActivity;
import com.wya.example.module.uikit.segmentedcontrol.SegmentedControlExampleActivity;
import com.wya.example.module.uikit.tabbar.TabBarExampleActivity;
import com.wya.example.module.uikit.toast.ToastExampleActivity;
import com.wya.example.module.uikit.toolbar.ToolBarExampleActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class UiKitExampleActivity extends BaseActivity {

	@BindView(R.id.tv_tool_bar)
	Button tvToolBar;
	@BindView(R.id.tv_dialog)
	Button tvDialog;
	@BindView(R.id.tv_tab_bar)
	Button tvTabBar;
	@BindView(R.id.tv_popupwindow)
	Button tvPopupWindow;
	@BindView(R.id.tv_tab_layout)
	Button tvTabLayout;
	@BindView(R.id.tv_toast)
	Button tvToast;
	@BindView(R.id.tv_search_bar)
	Button tvSearchBar;
	@BindView(R.id.tv_key_board)
	Button tvKeyBoard;

	private Intent intent = null;

	@Override
	protected void initView() {
		initImgLeft(R.mipmap.icon_back_white, true);
	}

	@Override
	protected int getLayoutID() {
		return R.layout.activity_ui_kit_example;
	}


	@OnClick({R.id.tv_key_board, R.id.tv_search_bar, R.id.tv_toast, R.id.tv_tool_bar, R.id
			.tv_popupwindow, R.id.tv_tab_bar, R.id.tv_tab_layout, R.id.tv_dialog, R.id
            .tv_pagination_view})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.tv_tool_bar:
				intent = new Intent(UiKitExampleActivity.this, ToolBarExampleActivity.class);
				UiKitExampleActivity.this.startActivity(intent);
				break;
			case R.id.tv_tab_bar:
				intent = new Intent(UiKitExampleActivity.this, TabBarExampleActivity
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
			case R.id.tv_popupwindow:
				intent = new Intent(UiKitExampleActivity.this, PopupWindowExampleActivity.class);
				UiKitExampleActivity.this.startActivity(intent);
				break;
			case R.id.tv_toast:
				intent = new Intent(UiKitExampleActivity.this, ToastExampleActivity.class);
				UiKitExampleActivity.this.startActivity(intent);
				break;
			case R.id.tv_search_bar:
				intent = new Intent(UiKitExampleActivity.this, SearchBarExampleActivity.class);
				UiKitExampleActivity.this.startActivity(intent);
				break;
			case R.id.tv_key_board:
				intent = new Intent(UiKitExampleActivity.this, KeyboardExampleActivity.class);
				UiKitExampleActivity.this.startActivity(intent);
				break;
			case R.id.tv_pagination_view:
				intent = new Intent(this, PaginationViewExampleActivity.class);
				startActivity(intent);
				break;
		}
	}

}
