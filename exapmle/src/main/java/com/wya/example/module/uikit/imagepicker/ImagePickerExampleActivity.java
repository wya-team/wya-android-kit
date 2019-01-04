package com.wya.example.module.uikit.imagepicker;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.example.module.hardware.camera.CameraExampleActivity;
import com.wya.hardware.camera.WYACameraView;
import com.wya.uikit.dialog.CustomListener;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.uikit.gallery.GalleryCreator;
import com.wya.uikit.imagecrop.Crop;
import com.wya.uikit.imagepicker.ImagePickerCreator;
import com.wya.uikit.imagepicker.PickerConfig;
import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagePickerExampleActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> mAllList = new ArrayList<>();
    private List<String> mVideoList = new ArrayList<>();
    private EditText input;
    private ImagePickerAdapter mPickerAdapter;
    private ImageView cropCrop, cropSelect;
    private int num;
    private WYACustomDialog mWyaCustomDialog;

    public static final int CROP_PHOTO = 1001;
    public static final int CROP = 1002;
    public static final int PHOTO = 100;
    public static final int TAKE_PHOTO = 101;
    public static final int VIDEO = 102;
    public static final int NO_PERMISSIONS_CAMEAR = 103;
    public static final int CAMERA = 10001;
    private static final String TAG = "ImagePickerExampleActivity";

    @Override
    protected int getLayoutID() {
        return R.layout.activity_image_picker_example;
    }

    @Override
    protected void initView() {

        setToolBarTitle("图片选择器(imagepicker)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(ImagePickerExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightImageAntherOnLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(ImagePickerExampleActivity.this, url);
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        input = (EditText) findViewById(R.id.num_input);
        cropCrop = (ImageView) findViewById(R.id.crop_crop);
        cropSelect = (ImageView) findViewById(R.id.crop_select);


        findViewById(R.id.crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePickerCreator.create(ImagePickerExampleActivity.this)
                        .maxImages(1)
                        .forResult(CROP_PHOTO);
            }
        });

        initRecycler();

    }

    /**
     * 默认可以拍照和录制视频
     */
    private int state = WYACameraView.BUTTON_STATE_BOTH;
    /**
     * 权限申请自定义码
     */
    private final int GET_PERMISSION_REQUEST = 100;

    private void openCamera() {
        getPermissions();
    }


    /**
     * 获取权限
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                Intent intent = new Intent(ImagePickerExampleActivity.this, CameraExampleActivity.class);
                intent.putExtra("state", state);
                intent.putExtra("duration", 10 * 1000);
                startActivityForResult(intent, CAMERA);
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(ImagePickerExampleActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            }
        } else {
            Intent intent = new Intent(ImagePickerExampleActivity.this, CameraExampleActivity.class);
            intent.putExtra("state", state);
            intent.putExtra("duration", 10 * 1000);
            startActivityForResult(intent, CAMERA);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    Intent intent = new Intent(ImagePickerExampleActivity.this, CameraExampleActivity.class);
                    intent.putExtra("state", state);
                    intent.putExtra("duration", 10 * 1000);
                    startActivityForResult(intent, CAMERA);
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void initRecycler() {
        mAllList.add("");
        mPickerAdapter = new ImagePickerAdapter(mAllList, this);

        mPickerAdapter.setOnItemClickListener(new ImagePickerAdapter.OnItemClickListener() {
            @Override
            public void onDelete(int position) {
                if (!TextUtils.isEmpty(mAllList.get(mAllList.size() - 1))) {
                    mAllList.add("");
                }
                mAllList.remove(position);
                mPickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemClick(int position) {
                ArrayList<String> preview = new ArrayList<>();
                preview.addAll(mAllList);
                if (TextUtils.isEmpty(preview.get(preview.size() - 1))) {
                    preview.remove(preview.size() - 1);
                }
                GalleryCreator.create(ImagePickerExampleActivity.this).openPreviewGallery
                        (position, preview);
            }

            @Override
            public void onAddClick() {
                initDialog();
            }
        });


        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mPickerAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra(PickerConfig.IMAGE_SELECTED)) {
                    Bundle extras = data.getExtras();
                    ArrayList<String> list = extras.getStringArrayList(PickerConfig.IMAGE_SELECTED);
                    mAllList.addAll(list);
                    if (mAllList.size() < num) {
                        mAllList.add("");
                    }
                    mPickerAdapter.notifyDataSetChanged();
                }
            } else {
                mAllList.add("");
            }
        }
        if (requestCode == CROP_PHOTO && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(PickerConfig.IMAGE_SELECTED)) {
                Bundle extras = data.getExtras();
                List<String> list = extras.getStringArrayList(PickerConfig.IMAGE_SELECTED);
                String path = list.get(0);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                cropSelect.setImageBitmap(bitmap);
                File file = new File(path);
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(this, "com.wya.example.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }

                Crop.create(ImagePickerExampleActivity.this)
                        .setImagePath(uri)
                        .saveCropImagePath(file.getParentFile().getPath() + "/test.jpg")
                        .cropQuality(80)
                        .forResult(1002);
            }
        }
        if (requestCode == CROP && resultCode == RESULT_OK) {
            String path = data.getStringExtra("path");
            File file = new File(path);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            cropCrop.setImageBitmap(bitmap);
        }
        if (requestCode == CROP && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "裁剪被取消了", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == CAMERA) {
            if (resultCode == TAKE_PHOTO) {
                Log.i("MCJ", "take photo");
                String path = data.getStringExtra("path");
                mAllList.add(path);
                if (mAllList.size() < num) {
                    mAllList.add("");
                }
                mPickerAdapter.notifyDataSetChanged();
            }
            if (resultCode == VIDEO) {
                Log.i("MCJ", "video");
                String path = data.getStringExtra("path");
                String url = data.getStringExtra("url");
                mAllList.add(url);
                if (mAllList.size() < num) {
                    mAllList.add("");
                }
                mPickerAdapter.notifyDataSetChanged();
            }
            if (resultCode == NO_PERMISSIONS_CAMEAR) {
                mAllList.add("");
                mPickerAdapter.notifyDataSetChanged();
                Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                mAllList.add("");
                mPickerAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * 弹窗选择按钮
     */
    private void initDialog() {
        //RecyclerView条目点击事件
        mWyaCustomDialog = new WYACustomDialog.Builder(this)
                .setLayoutRes(R.layout.image_picker_choose, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView dialogCamera = v.findViewById(R.id.dialog_camera);
                        TextView dialogPhoto = v.findViewById(R.id.dialog_photo);
                        TextView dialogCancel = v.findViewById(R.id.dialog_cancel);
                        dialogCamera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mWyaCustomDialog.dismiss();
                                num = Integer.parseInt(TextUtils.isEmpty(input.getText().toString())
                                        ? "1" : input.getText().toString());
                                mAllList.remove(mAllList.size() - 1);
                                openCamera();
                            }
                        });
                        dialogPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mWyaCustomDialog.dismiss();
                                num = Integer.parseInt(TextUtils.isEmpty(input.getText().toString())
                                        ? "1" : input.getText().toString());
                                mAllList.remove(mAllList.size() - 1);
                                ImagePickerCreator.create(ImagePickerExampleActivity.this)
                                        .maxImages(num - mAllList.size())
                                        .forResult(PHOTO);
                            }
                        });
                        dialogCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mWyaCustomDialog.dismiss();
                            }
                        });
                    }
                })
                .cancelable(true)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .build();
        mWyaCustomDialog.show();
    }
}
