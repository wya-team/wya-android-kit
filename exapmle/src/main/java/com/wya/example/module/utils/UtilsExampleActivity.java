package com.wya.example.module.utils;

import android.content.Intent;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.utils.realm.RealmExampleActivity;

import butterknife.OnClick;

public class UtilsExampleActivity extends BaseActivity {

	@Override
	protected int getLayoutID() {
		return R.layout.activity_utils_example;
	}

	@Override
	protected void initView() {
		setToolBarTitle("工具库");
	}

	@OnClick({R.id.wya_button_realm,R.id.wya_button_image})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.wya_button_realm:
				UtilsExampleActivity.this.startActivity(new Intent(UtilsExampleActivity.this, RealmExampleActivity
                        .class));
				break;
			case R.id.wya_button_image:
				startActivity(new Intent(this, QRCodeExampleActivity.class));
				break;
		}
	}
}
