package com.wya.example.module.hardware.camera;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.hardware.camera.WYACameraView;
import com.wya.hardware.camera.listener.ClickListener;
import com.wya.hardware.camera.listener.ErrorListener;
import com.wya.hardware.camera.listener.WYACameraListener;
import com.wya.hardware.camera.util.DeviceUtil;
import com.wya.utils.utils.FileUtil;

import java.io.File;

/**
 * @date: 2018/12/5 16:34
 * @author: Chunjiang Mao
 * @classname: CameraExampleActivity
 * @describe: 相机实例
 */

public class CameraExampleActivity extends AppCompatActivity {
    private WYACameraView wyaCameraView;
    private String dirName = "WYACamera";
    /**
     * 录制时间
     */
    private int duration;
    /**
     * 相机拍照类型
     */
    private int state;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.activity_start_bottom_in, R.anim.activity_start_bottom);
        setContentView(R.layout.activity_camera_example);
        wyaCameraView = findViewById(R.id.wya_camera_view);
        duration = getIntent().getIntExtra("duration", 10000);
        state = getIntent().getIntExtra("state", WYACameraView.BUTTON_STATE_BOTH);
        //设置视频保存路径
        wyaCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + dirName);
        wyaCameraView.setFeatures(state);
        wyaCameraView.setTip("WYACameraView");
        wyaCameraView.setMediaQuality(WYACameraView.MEDIA_QUALITY_HIGH);
        wyaCameraView.setDuration(duration);
        wyaCameraView.setErrorListener(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i("MCJ", "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }
            
            @Override
            public void audiopermissionerror() {
                Toast.makeText(CameraExampleActivity.this, "请在设置允许录音", Toast.LENGTH_SHORT).show();
            }
        });
        //wyaCameraView监听
        wyaCameraView.setCameraListener(new WYACameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                String path = FileUtil.saveBitmap(dirName, bitmap);
                Intent intent = new Intent();
                intent.putExtra("path", path);
                setResult(101, intent);
                finish();
            }
            
            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取视频路径
                String path = FileUtil.saveBitmap(dirName, firstFrame);
                Log.i("MCJ", "url = " + url + ", Bitmap = " + path);
                Intent intent = new Intent();
                intent.putExtra("path", path);
                intent.putExtra("url", url);
                setResult(102, intent);
                finish();
            }
        });
        
        wyaCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraExampleActivity.this.finish();
                overridePendingTransition(R.anim.activity_start_bottom, R.anim.activity_start_bottom_exit);
            }
        });
        wyaCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(CameraExampleActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });
        
        Log.i("MCJ", DeviceUtil.getDeviceModel());
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        wyaCameraView.onResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        wyaCameraView.onPause();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
