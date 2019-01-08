package com.wya.example.module.utils;

import android.content.Intent;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.utils.fliedownload.FileDownloadExampleActivity;
import com.wya.example.module.utils.image.QRCodeExampleActivity;
import com.wya.example.module.utils.realm.RealmExampleActivity;

import butterknife.OnClick;

public class UtilsExampleActivity extends BaseActivity {
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_utils_example;
    }
    
    @Override
    protected void initView() {
        setTitle("工具库");
    }
    
    @OnClick({R.id.wya_button_realm, R.id.wya_button_image, R.id.wya_button_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wya_button_realm:
                UtilsExampleActivity.this.startActivity(new Intent(UtilsExampleActivity.this,
                        RealmExampleActivity
                                .class));
                break;
            case R.id.wya_button_image:
                startActivity(new Intent(this, QRCodeExampleActivity.class));
                break;
            case R.id.wya_button_download:
                startActivity(new Intent(this, FileDownloadExampleActivity.class));
                break;
            default:
                break;
        }
    }
}
