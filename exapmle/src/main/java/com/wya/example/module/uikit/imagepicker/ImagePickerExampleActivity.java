package com.wya.example.module.uikit.imagepicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.dialog.CustomListener;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.uikit.gallery.GalleryCreator;
import com.wya.uikit.imagecrop.Crop;
import com.wya.uikit.imagepicker.ImagePickerCreator;
import com.wya.uikit.imagepicker.PickerConfig;
import com.wya.uikit.imagepicker.SpaceDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagePickerExampleActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> mAllList = new ArrayList<>();
    private List<String> mVideoList = new ArrayList<>();
    private EditText input;
    private ImagePickerAdapter mPickerAdapter;
    private ImageView crop_crop, crop_select;
    private int num;
    private WYACustomDialog mWyaCustomDialog;

    public static final int CROP_PHOTO = 1001;
    public static final int CROP = 1002;
    public static final int PHOTO = 100;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_image_picker_example;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        input = (EditText) findViewById(R.id.num_input);
        crop_crop = (ImageView) findViewById(R.id.crop_crop);
        crop_select = (ImageView) findViewById(R.id.crop_select);


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
                List<String> preview = new ArrayList<>();
                preview.addAll(mAllList);
                preview.remove(preview.size() - 1);
                GalleryCreator.create(ImagePickerExampleActivity.this).openPreviewGallery
                        (position, preview);
            }

            @Override
            public void onAddClick() {
                initDialog();
            }
        });


        mRecyclerView.addItemDecoration(new SpaceDecoration(4, 10, false));
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
                crop_select.setImageBitmap(bitmap);
                File file = new File(path);
                Uri uri;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(this, "com.wya.example.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }

                Crop.create(ImagePickerExampleActivity.this)
                        .setImagePath(uri)
                        .saveCropImagePath(file.getParentFile().getPath() + "/test.jpg")
                        .CropQuality(80)
                        .forResult(1002);
            }
        }
        if (requestCode == CROP && resultCode == RESULT_OK) {
            String path = data.getStringExtra("path");
            File file = new File(path);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            crop_crop.setImageBitmap(bitmap);
        }
        if (requestCode == CROP && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "裁剪被取消了", Toast.LENGTH_SHORT).show();
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
                        TextView dialog_camera = v.findViewById(R.id.dialog_camera);
                        TextView dialog_photo = v.findViewById(R.id.dialog_photo);
                        TextView dialog_cancel = v.findViewById(R.id.dialog_cancel);
                        dialog_camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mWyaCustomDialog.dismiss();

                            }
                        });
                        dialog_photo.setOnClickListener(new View.OnClickListener() {
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
                        dialog_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mWyaCustomDialog.dismiss();
                            }
                        });
                    }
                })
                .cancelable(true)
                .Gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .build();
        mWyaCustomDialog.show();
    }
}
