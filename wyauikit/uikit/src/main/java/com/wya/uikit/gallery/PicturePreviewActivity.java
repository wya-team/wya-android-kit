package com.wya.uikit.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
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

    private int type;
    private int position;
    private List<LocalMedia> images = new ArrayList<>();
    private List<LocalMedia> mImageSelected = new ArrayList<>();
    private PreviewPagerAdapter mAdapter;
    private String field;
    private List<String> mList = new ArrayList<>();
    private int requestCode;
    private int max;
    private String TAG = "PicturePreviewActivity";

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
                break;
            case GalleryConfig.IMAGE_PICKER:
                ll_check.setVisibility(View.VISIBLE);
                select_bar_layout.setVisibility(View.VISIBLE);
                check.setChecked(mImageSelected.contains(images.get(position)));
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
//        field = getIntent().getStringExtra(GalleryConfig.FIELD_NAME);
        requestCode = getIntent().getIntExtra(GalleryConfig.PICKER_FOR_RESULT, -1);
        max = getIntent().getIntExtra(GalleryConfig.MAX_NUM, -1);

        switch (type) {
            case GalleryConfig.GALLERY:
                mList = getIntent().getStringArrayListExtra(GalleryConfig.IMAGE_LIST);
                break;
            case GalleryConfig.IMAGE_PICKER:
                List<LocalMedia> datas = DataHelper.getInstance().getImageSelected();
                mImageSelected.addAll(datas);
                List<LocalMedia>alllist = DataHelper.getInstance().getImages();
                images.addAll(alllist);
                for (int i = 0; i < alllist.size(); i++) {
                    mList.add(alllist.get(i).getPath());
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

        initCommitBtn();

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
                if (mImageSelected.size() > 0) {
                    check.setChecked(mImageSelected.contains(images.get(positions)));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
            } else {
                mImageSelected.remove(images.get(position));
            }
            initCommitBtn();
        }

        if (v.getId() == R.id.id_ll_ok) {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(GalleryConfig.IMAGE_LIST_SELECTED, (Serializable)
                    mImageSelected);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

        //todo: 图片编辑功能
        if (v.getId() == R.id.crop_edit) {
            if (check.isChecked()) {
                int currentItem = preview_pager.getCurrentItem();
                File file = new File(mList.get(currentItem));
                Uri uri;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                Crop.create(this)
                        .setImagePath(uri)
                        .saveCropImagePath(file.getParentFile().getPath() + "/test.jpg")
                        .CropQuality(80)
                        .forResult(1002);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (requestCode != -1) {
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(GalleryConfig.IMAGE_LIST_SELECTED, (Serializable)
                    mImageSelected);
            intent.putExtras(bundle);
            setResult(RESULT_CANCELED, intent);
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
}
