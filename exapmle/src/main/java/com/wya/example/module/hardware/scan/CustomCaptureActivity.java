/*
 * Copyright (C) 2018 Jenly Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wya.example.module.hardware.scan;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.hardware.scan.CaptureActivity;
import com.wya.hardware.scan.util.CodeUtils;
import com.wya.uikit.toolbar.StatusBarUtil;

import static com.wya.hardware.scan.Intents.Scan.RESULT;
 /**
  * 创建日期：2018/12/24 11:24
  * 作者： Mao Chunjiang
  * 文件名称：CustomCaptureActivity
  * 类说明：自定义布局扫码+图片二维码解析
  */

public class CustomCaptureActivity extends CaptureActivity {

    private final int REQUEST_EXTERNAL_STORAGE = 110;
    public final int REQUEST_CODE_PHOTO = 0X02;


    @Override
    public int getLayoutId() {
        return R.layout.custom_capture_activity;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        overridePendingTransition(R.anim.activity_start_right, R.anim.activity_start_left);
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        getBeepManager().setPlayBeep(true);
        getBeepManager().setVibrate(true);
    }

    private void offFlash() {
        Camera camera = getCameraManager().getOpenCamera().getCamera();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
    }

    public void openFlash() {
        Camera camera = getCameraManager().getOpenCamera().getCamera();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
    }


    private void clickFlash(View v) {
        if (v.isSelected()) {
            offFlash();
            v.setSelected(false);
        } else {
            openFlash();
            v.setSelected(true);
        }
    }


    private void checkExternalStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //不具有获取权限，需要进行权限申请
                startPhotoCode();
            } else {
                // 第一次申请，就直接申请
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            startPhotoCode();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    startPhotoCode();
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ivFlash:
                clickFlash(v);
                break;
            case R.id.ll_left:
                overridePendingTransition(R.anim.activity_start_left, R.anim.activity_start_left_exit);
                finish();
                break;
            case R.id.ll_right:
                checkExternalStoragePermissions();
                break;
        }
    }

    private void startPhotoCode() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }

        }
    }

    /**
     * 解析二维码图片
     *
     * @param data
     */
    private void parsePhoto(Intent data) {
        final String path = getImagePath(this, data);
        Log.d("Jenly", "path:" + path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        //异步解析
        asyncThread(() -> {
            final String result = CodeUtils.parseCode(path);
            runOnUiThread(() -> {
                Intent intent = new Intent();
                intent.putExtra(RESULT, result);
                setResult(RESULT_OK, intent);
                finish();
            });
        });

    }

    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }


    /**
     * 获取图片
     */
    public String getImagePath(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion > Build.VERSION_CODES.KITKAT) {
            Log.d("uri=intent.getData :", "" + uri);
            if (DocumentsContract.isDocumentUri(context, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                Log.d("getDocumentId(uri) :", "" + docId);
                Log.d("uri.getAuthority() :", "" + uri.getAuthority());
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(context, contentUri, null);
                }

            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(context, uri, null);
            }
        } else {
            imagePath = getImagePath(context, uri, null);
        }

        return imagePath;

    }

    /**
     * 通过uri和selection来获取真实的图片路径,从相册获取图片时要用
     */
    private String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
