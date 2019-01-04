package com.wya.example.module.uikit;

import android.content.Intent;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.badge.BadgeExampleActivity;
import com.wya.example.module.uikit.banner.BannerExampleActivity;
import com.wya.example.module.uikit.button.ButtonExampleActivity;
import com.wya.example.module.uikit.optionmenu.OptionMenuExampleActivity;
import com.wya.example.module.uikit.customedittext.CustomEditTextExampleActivity;
import com.wya.example.module.uikit.customitems.CustomItemsExampleActivity;
import com.wya.example.module.uikit.dialog.DialogExampleActivity;
import com.wya.example.module.uikit.gallery.GalleryExampleActivity;
import com.wya.example.module.uikit.imagepicker.ImagePickerExampleActivity;
import com.wya.example.module.uikit.keyboard.KeyboardExampleActivity;
import com.wya.example.module.uikit.notice.NoticeExampleActivity;
import com.wya.example.module.uikit.paginationview.PaginationViewExampleActivity;
import com.wya.example.module.uikit.pickerview.PickerViewExampleActivity;
import com.wya.example.module.uikit.popupwindow.PopupWindowExampleActivity;
import com.wya.example.module.uikit.progress.ProgressExampleActivity;
import com.wya.example.module.uikit.searchbar.SearchBarExampleActivity;
import com.wya.example.module.uikit.segmentedcontrol.SegmentedControlExampleActivity;
import com.wya.example.module.uikit.slidder.SlideViewExampleActivity;
import com.wya.example.module.uikit.stepper.StepperExampleActivity;
import com.wya.example.module.uikit.tabbar.TabBarExampleActivity;
import com.wya.example.module.uikit.toast.ToastExampleActivity;
import com.wya.example.module.uikit.toolbar.ToolBarExampleActivity;

import butterknife.OnClick;

public class UiKitExampleActivity extends BaseActivity {

	@Override
	protected void initView() {
		initImgLeft(R.drawable.icon_backblue, true);
	}

	@Override
	protected int getLayoutID() {
		return R.layout.activity_ui_kit_example;
	}

	@OnClick({R.id.tv_stepper, R.id.tv_custom_items, R.id.tv_button, R.id.tv_key_board, R.id
			.tv_search_bar, R.id.tv_toast, R.id.tv_tool_bar, R.id
			.tv_popupwindow, R.id.tv_tab_bar, R.id.tv_tab_layout, R.id.tv_dialog, R.id
			.tv_pagination_view, R.id.tv_choice_menu, R.id.tv_badge, R.id.tv_gallery, R.id
			.tv_image_picker, R.id.tv_progress, R.id.tv_banner, R.id.tv_picker_view, R.id
			.tv_crop_view, R.id.tv_notice_view, R.id.tv_custom_edit_text,R.id.tv_slide_view})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.tv_tool_bar:
				UiKitExampleActivity.this.startActivity(new Intent(UiKitExampleActivity.this,
						ToolBarExampleActivity.class));
				break;
			case R.id.tv_tab_bar:
				startActivity(new Intent(UiKitExampleActivity.this, TabBarExampleActivity.class));
				break;
			case R.id.tv_tab_layout:
				startActivity(new Intent(this, SegmentedControlExampleActivity.class));
				break;
			case R.id.tv_dialog:
				UiKitExampleActivity.this.startActivity(new Intent(UiKitExampleActivity.this,
						DialogExampleActivity.class));
				break;
			case R.id.tv_popupwindow:
				UiKitExampleActivity.this.startActivity(new Intent(UiKitExampleActivity.this,
						PopupWindowExampleActivity.class));
				break;
			case R.id.tv_toast:
				UiKitExampleActivity.this.startActivity(new Intent(UiKitExampleActivity.this,
						ToastExampleActivity.class));
				break;
			case R.id.tv_search_bar:
				UiKitExampleActivity.this.startActivity(new Intent(UiKitExampleActivity.this,
						SearchBarExampleActivity.class));
				break;
			case R.id.tv_key_board:
				UiKitExampleActivity.this.startActivity(new Intent(UiKitExampleActivity.this,
						KeyboardExampleActivity.class));
				break;
			case R.id.tv_pagination_view:
				startActivity(new Intent(this, PaginationViewExampleActivity.class));
				break;
			case R.id.tv_choice_menu:
				startActivity(new Intent(this, OptionMenuExampleActivity.class));
				break;
			case R.id.tv_badge:
				BadgeExampleActivity.start(UiKitExampleActivity.this);
				break;
			case R.id.tv_button:
				startActivity(new Intent(this, ButtonExampleActivity.class));
				break;
			case R.id.tv_gallery:
				startActivity(new Intent(this, GalleryExampleActivity.class));
				break;
			case R.id.tv_custom_items:
				startActivity(new Intent(this, CustomItemsExampleActivity.class));
				break;
			case R.id.tv_image_picker:
				startActivity(new Intent(this, ImagePickerExampleActivity.class));
				break;
			case R.id.tv_stepper:
				startActivity(new Intent(this, StepperExampleActivity.class));
				break;
			case R.id.tv_progress:
				startActivity(new Intent(this, ProgressExampleActivity.class));
				break;
			case R.id.tv_banner:
				startActivity(new Intent(this, BannerExampleActivity.class));
				break;
			case R.id.tv_picker_view:
				startActivity(new Intent(this, PickerViewExampleActivity.class));
				break;
			case R.id.tv_crop_view:
				startActivity(new Intent(this, ImagePickerExampleActivity.class));
				break;
			case R.id.tv_notice_view:
				NoticeExampleActivity.start(UiKitExampleActivity.this);
				break;
			case R.id.tv_custom_edit_text:
				startActivity(new Intent(this, CustomEditTextExampleActivity.class));
				break;
			case R.id.tv_slide_view:
				SlideViewExampleActivity.start(UiKitExampleActivity.this);
				break;
		}
	}
}
