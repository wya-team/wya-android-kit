package com.wya.example.module.hardware;


import android.content.Intent;
import android.view.View;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.hardware.camera.StartCameraExampleActivity;
import com.wya.example.module.hardware.videoplayer.VideoPlayerExampleActivity;

import butterknife.OnClick;

public class HardwareExampleActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_harware;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.wya_button_camera, R.id.wya_button_video_player})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wya_button_camera:
                HardwareExampleActivity.this.startActivity(new Intent(HardwareExampleActivity.this, StartCameraExampleActivity.class));
                break;
            case R.id.wya_button_video_player:
                HardwareExampleActivity.this.startActivity(new Intent(HardwareExampleActivity.this, VideoPlayerExampleActivity.class));
                break;
        }
    }
}
