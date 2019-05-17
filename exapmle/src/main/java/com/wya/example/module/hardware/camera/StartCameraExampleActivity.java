package com.wya.example.module.hardware.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.hardware.camera.WYACameraView;
import com.wya.uikit.button.WYAButton;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;
import static com.wya.hardware.camera.WYACameraView.BUTTON_STATE_BOTH;
import static com.wya.hardware.camera.WYACameraView.BUTTON_STATE_ONLY_CAPTURE;

/**
 * @date: 2019/1/8 9:52
 * @author: Chunjiang Mao
 * @classname: StartCameraExampleActivity
 * @describe: 相机启动页
 */

public class StartCameraExampleActivity extends BaseActivity {
    public static final int TAKE_PHOTO = 101;
    public static final int VIDEO = 102;
    public static final int NO_PERMISSIONS_CAMEAR = 103;
    
    @BindView(R.id.image_photo)
    ImageView imagePhoto;
    @BindView(R.id.take_photo)
    RadioButton takePhoto;
    @BindView(R.id.take_video)
    RadioButton takeVideo;
    @BindView(R.id.take_video_and_photo)
    RadioButton takeVideoAndPhoto;
    @BindView(R.id.btn)
    WYAButton btn;
    @BindView(R.id.et_duration)
    EditText etDuration;
    @BindView(R.id.tv_path)
    TextView tvPath;
    /**
     * 默认可以拍照和录制视频
     */
    private int state = BUTTON_STATE_BOTH;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera_example_start;
    }
    
    @Override
    protected void initView() {
        setTitle("CameraExample");
        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(StartCameraExampleActivity.this, ReadmeActivity.class).putExtra(EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            showShort("链接地址复制成功");
            StringUtil.copyString(StartCameraExampleActivity.this, url);
        });
    }
    
    /**
     * 获取权限
     */
    @SuppressLint("CheckResult")
    private void checkPermissions() {
        if (state == BUTTON_STATE_ONLY_CAPTURE) {
            new RxPermissions(this).request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            ).subscribe(this::onPermissionResult);
        } else {
            new RxPermissions(this).request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            ).subscribe(this::onPermissionResult);
        }
    }
    
    private void onPermissionResult(boolean aBoolean) {
        if (aBoolean) {
            startCamare();
        } else {
            Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void startCamare() {
        Intent intent = new Intent(StartCameraExampleActivity.this, CameraExampleActivity.class);
        intent.putExtra("state", state);
        intent.putExtra("duration", Integer.valueOf(etDuration.getText().toString()).intValue() * 1000);
        startActivityForResult(intent, 100);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == TAKE_PHOTO) {
            Log.i("MCJ", "picture");
            String path = data.getStringExtra("path");
            tvPath.setText("照片路径：" + path);
            imagePhoto.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        if (resultCode == VIDEO) {
            Log.i("MCJ", "video");
            String path = data.getStringExtra("path");
            String url = data.getStringExtra("url");
            imagePhoto.setImageBitmap(BitmapFactory.decodeFile(path));
            tvPath.setText("视频路径:" + url + "\n首帧图片:" + path);
        }
        if (resultCode == NO_PERMISSIONS_CAMEAR) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }
    
    @OnClick({R.id.take_photo, R.id.take_video, R.id.take_video_and_photo, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                state = BUTTON_STATE_ONLY_CAPTURE;
                break;
            case R.id.take_video:
                state = WYACameraView.BUTTON_STATE_ONLY_RECORDER;
                break;
            case R.id.take_video_and_photo:
                state = BUTTON_STATE_BOTH;
                break;
            case R.id.btn:
                if ("".equals(etDuration.getText().toString())) {
                    etDuration.setText("10");
                }
                checkPermissions();
                break;
            default:
                break;
        }
    }
    
}
