package com.wya.uikit.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;
import com.wya.uikit.imagecrop.Crop;
import com.wya.uikit.imagepicker.LocalMedia;
import com.wya.uikit.imagepicker.PickerConfig;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PicturePreviewActivity extends Activity implements View.OnClickListener {

    private ImageView picture_left_back;
    private TextView picture_title;
    private LinearLayout ll_check;
    private CheckBox check;
    private PreviewViewPager preview_pager;
    private RelativeLayout select_bar_layout;
    private TextView tv_img_num;
    private TextView tv_ok;
    private TextView crop_edit;
    private LinearLayout id_ll_ok;
    private RecyclerView select_recycler;
    private LinearLayout select_list_layout;
    private SelectedRecyclerAdapter mSelectedRecyclerAdapter;

    private int type;
    private int position;
    private List<LocalMedia> images = new ArrayList<>();
    private List<LocalMedia> mImageSelected = new ArrayList<>();
    private List<Integer> selectedPosition = new ArrayList<>();
    private PreviewPagerAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private List<String> mCropUrlList = new ArrayList<>();
    private int requestForCode;
    private int max;
    private String TAG = "PicturePreviewActivity";
    public static final int CROP_IMAGE = 1002;
    private LocalMedia editLocalMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);

        setColor();
        getIntentExtra();
        initView();

        switch (type) {
            case GalleryConfig.GALLERY:
                ll_check.setVisibility(View.GONE);
                select_bar_layout.setVisibility(View.GONE);
                select_list_layout.setVisibility(View.GONE);
                break;
            case GalleryConfig.IMAGE_PICKER:
                ll_check.setVisibility(View.VISIBLE);
                select_bar_layout.setVisibility(View.VISIBLE);
                check.setChecked(mImageSelected.contains(images.get(position)));
                if (mImageSelected.size() > 0) {
                    select_list_layout.setVisibility(View.VISIBLE);
                } else {
                    select_list_layout.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }


    }


    /**
     * init intent value
     */
    private void getIntentExtra() {


        position = getIntent().getIntExtra(GalleryConfig.POSITION, -1);
        type = getIntent().getIntExtra(GalleryConfig.TYPE, GalleryConfig.GALLERY);
        requestForCode = getIntent().getIntExtra(GalleryConfig.PICKER_FOR_RESULT, -1);
        max = getIntent().getIntExtra(GalleryConfig.MAX_NUM, -1);

        switch (type) {
            case GalleryConfig.GALLERY:
                mList = getIntent().getStringArrayListExtra(GalleryConfig.IMAGE_LIST);
                break;
            case GalleryConfig.IMAGE_PICKER:
                List<LocalMedia> datas = DataHelper.getInstance().getImageSelected();
                mImageSelected.addAll(datas);
                List<LocalMedia> alllist = DataHelper.getInstance().getImages();
                images.addAll(alllist);
                mCropUrlList = DataHelper.getInstance().getCropList();

                //这里的循环可能不是很好，可以把selectionPosition在图片选择时就进行创建添加
                for (int i = 0; i < mImageSelected.size(); i++) {
                    for (int j = 0; j < images.size(); j++) {
                        if (images.get(j).equals(mImageSelected.get(i))) {
                            selectedPosition.add(j);
                        }
                    }
                }

                for (int i = 0; i < images.size(); i++) {
                    String cropPath = images.get(i).getCropPath();
                    if (TextUtils.isEmpty(cropPath)) {
                        mList.add(images.get(i).getPath());
                    } else {
                        mList.add(cropPath);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void initView() {
        picture_left_back = findViewById(R.id.picture_left_back);
        picture_title = findViewById(R.id.picture_title);
        ll_check = findViewById(R.id.ll_check);
        check = findViewById(R.id.check);
        preview_pager = findViewById(R.id.preview_pager);
        select_bar_layout = findViewById(R.id.select_bar_layout);
        tv_img_num = findViewById(R.id.tv_img_num);
        tv_ok = findViewById(R.id.tv_ok);
        id_ll_ok = findViewById(R.id.id_ll_ok);
        crop_edit = findViewById(R.id.crop_edit);
        select_recycler = findViewById(R.id.select_recycler);
        select_list_layout = findViewById(R.id.select_list_layout);

        initCommitBtn();
        initRecyclerView();

        mAdapter = new PreviewPagerAdapter(mList, this);
        preview_pager.setAdapter(mAdapter);
        picture_title.setText(position + 1 + "/" + mList.size());
        preview_pager.setCurrentItem(position);

        check.setOnClickListener(this);
        picture_left_back.setOnClickListener(this);
        id_ll_ok.setOnClickListener(this);
        crop_edit.setOnClickListener(this);

        preview_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int positions) {
                position = positions;
                picture_title.setText(positions + 1 + "/" + mList.size());
                LocalMedia localMedia = images.get(positions);
                String[] split = localMedia.getPath().split("[.]");
                String mediaType = split[split.length - 1];
                Log.i(TAG, "onPageSelected: " + isVideo(mediaType));
                crop_edit.setVisibility(isVideo(mediaType) ? View.GONE : View.VISIBLE);
                //update imageSelected
                if (mImageSelected.size() > 0) {
                    check.setChecked(mImageSelected.contains(images.get(positions)));
                    mSelectedRecyclerAdapter.updateSelected(position, selectedPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initRecyclerView() {
        mSelectedRecyclerAdapter = new SelectedRecyclerAdapter(mImageSelected, this);
        select_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .HORIZONTAL, false));
        select_recycler.setAdapter(mSelectedRecyclerAdapter);
        mSelectedRecyclerAdapter.updateSelected(position, selectedPosition);
        mSelectedRecyclerAdapter.setOnItemClickListener(new SelectedRecyclerAdapter
                .OnItemClickListener() {
            @Override
            public void onClick(int position) {
                preview_pager.setCurrentItem(selectedPosition.get(position));
            }
        });
    }

    private void initCommitBtn() {
        if (mImageSelected != null) {

            if (mImageSelected.size() > 0) {
                tv_img_num.setText("(" + mImageSelected.size() + ")");
                tv_img_num.setVisibility(View.VISIBLE);
                tv_ok.setTextColor(getResources().getColor(R.color.color_orange));
                id_ll_ok.setEnabled(true);
            } else {
                tv_img_num.setVisibility(View.GONE);
                tv_ok.setTextColor(getResources().getColor(R.color.color_666));
                id_ll_ok.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.picture_left_back) {
            onBackPressed();
        }

        if (v.getId() == R.id.check) {
            if (check.isChecked()) {
                if (max == mImageSelected.size()) {
                    check.setChecked(false);
                    Toast.makeText(PicturePreviewActivity.this, "最多选择" + max + "张图片", Toast
                            .LENGTH_SHORT).show();
                    return;
                }
                mImageSelected.add(images.get(position));
                selectedPosition.add(position);
            } else {
                selectedPosition.remove(Integer.valueOf(position));
                mImageSelected.remove(images.get(position));
            }
            if (mImageSelected.size() > 0) {
                mSelectedRecyclerAdapter.updateSelected(position, selectedPosition);
                select_recycler.smoothScrollToPosition(selectedPosition.size() - 1);
                select_list_layout.setVisibility(View.VISIBLE);
            } else {
                select_list_layout.setVisibility(View.INVISIBLE);
            }
            initCommitBtn();
        }

        if (v.getId() == R.id.id_ll_ok) {
            //删除不选择的编辑图片
            for (int i = 0; i < mImageSelected.size(); i++) {
                mCropUrlList.remove(mImageSelected.get(i).getCropPath());
            }
            GalleryUtils.removeAllFile(mCropUrlList);

            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(GalleryConfig.IMAGE_LIST_SELECTED, (Serializable)
                    mImageSelected);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

        //edit crop
        if (v.getId() == R.id.crop_edit) {
            if (!check.isChecked()) {
                if (max == mImageSelected.size()) {
                    Toast.makeText(PicturePreviewActivity.this, "最多选择" + max + "张图片", Toast
                            .LENGTH_SHORT).show();
                    return;
                }
            }

            int currentItem = preview_pager.getCurrentItem();
            editLocalMedia = images.get(currentItem);
            File file;
            String cropPath = editLocalMedia.getCropPath();
            String filePath;
            if (TextUtils.isEmpty(cropPath)) {
                file = new File(editLocalMedia.getPath());

            } else {
                file = new File(cropPath);
            }
            filePath = file.getParentFile().getPath() + "/" + System.currentTimeMillis() + ".jpg";
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            Crop.create(this)
                    .setImagePath(uri)
                    .CropQuality(80)
                    .saveCropImagePath(filePath)
                    .forResult(CROP_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CROP_IMAGE) {
            if (resultCode == RESULT_OK) {
                String path = data.getStringExtra("path");
                String cropPath = editLocalMedia.getCropPath();
                if (!TextUtils.isEmpty(cropPath)) {
                    GalleryUtils.deleteFile(cropPath);
                    mCropUrlList.remove(cropPath);
                }
                editLocalMedia.setCropPath(path);
                mCropUrlList.add(path);

                if (check.isChecked()) {
                    for (int i = 0; i < mImageSelected.size(); i++) {
                        if (mImageSelected.get(i).equals(editLocalMedia)) {
                            mImageSelected.set(i, editLocalMedia);
                        }
                    }

                } else {
                    check.setChecked(true);
                    mImageSelected.add(editLocalMedia);
                    selectedPosition.add(position);
                    //selected image 0->1
                    select_list_layout.setVisibility(View.VISIBLE);
                }

                mList.set(position, path);
                images.set(position, editLocalMedia);
                mSelectedRecyclerAdapter.updateSelected(position, selectedPosition);
                mAdapter.updateData(position);
                initCommitBtn();


            }
        }
    }

    @Override
    public void onBackPressed() {
        switch (requestForCode) {
            case PickerConfig.PICKER_GALLERY_RESULT:
            case PickerConfig.PICKER_GALLERY_PREVIEW:
                Intent intent = getIntent();
                DataHelper.getInstance().setImages(images);
                DataHelper.getInstance().setImageSelected(mImageSelected);
                DataHelper.getInstance().setCropList(mCropUrlList);
                setResult(RESULT_CANCELED, intent);
                break;
            default:
                break;
        }
        finish();
    }

    /**
     * 设置状态栏颜色
     */

    public void setColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(R.id
                    .statusbarutil_fake_status_bar_view);
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == View.GONE) {
                    fakeStatusBarView.setVisibility(View.VISIBLE);
                }
                fakeStatusBarView.setBackgroundColor(getResources().getColor(R.color.black));
            }
            ViewGroup parent = findViewById(android.R.id.content);
            for (int i = 0, count = parent.getChildCount(); i < count; i++) {
                View childView = parent.getChildAt(i);
                if (childView instanceof ViewGroup) {
                    childView.setFitsSystemWindows(true);
                    ((ViewGroup) childView).setClipToPadding(true);
                }
            }
        }
    }

    private static final String media =
            "MPEG/MPG/DAT/AVI/MOV/ASF/WMV/NAVI/3GP/MKV/FLV/F4V/RMVB/WEBM/MP4";

    private boolean isVideo(String mediaType) {
        return media.contains(mediaType.toUpperCase());
    }

}
