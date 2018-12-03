package com.wya.uikit.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PicturePreviewActivity<T> extends Activity implements View.OnClickListener {

	private ImageView picture_left_back;
	private TextView picture_title;
	private LinearLayout ll_check;
	private CheckBox check;
	private PreviewViewPager preview_pager;
	private RelativeLayout select_bar_layout;
	private TextView tv_img_num;
	private TextView tv_ok;
	private LinearLayout id_ll_ok;

	private int type;
	private int position;
	private List<T> images = new ArrayList<>();
	private List<T> mImageSelected = new ArrayList<>();
	private PreviewPagerAdapter mAdapter;
	private String field;
	private List<String> mList = new ArrayList<>();
	private int requestCode;
	private int max;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_preview);


		type = getIntent().getIntExtra(GalleryConfig.TYPE, GalleryConfig.GALLERY);
		position = getIntent().getIntExtra(GalleryConfig.POSITION, -1);
		mImageSelected = (List<T>) getIntent().getSerializableExtra(GalleryConfig
				.IMAGE_LIST_SELECTED);
		field = getIntent().getStringExtra(GalleryConfig.FIELD_NAME);
		requestCode = getIntent().getIntExtra(GalleryConfig.PICKER_FOR_RESULT, -1);
		max = getIntent().getIntExtra(GalleryConfig.MAX_NUM, -1);


		if (!TextUtils.isEmpty(field)) {
			images = (List<T>) getIntent().getSerializableExtra(GalleryConfig.IMAGE_LIST);
			for (int i = 0; i < images.size(); i++) {
				T t = images.get(i);
				try {
					Field field1 = t.getClass().getDeclaredField(field);
					field1.setAccessible(true);
					String value = (String) field1.get(t);
					mList.add(value);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		} else {
			mList = (List<String>) getIntent().getSerializableExtra(GalleryConfig.IMAGE_LIST);
		}
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

		initCommitBtn();

		mAdapter = new PreviewPagerAdapter(mList, this);
		preview_pager.setAdapter(mAdapter);
		picture_title.setText(position + 1 + "/" + mList.size());
		preview_pager.setCurrentItem(position);

		check.setOnClickListener(this);
		picture_left_back.setOnClickListener(this);
		id_ll_ok.setOnClickListener(this);

		preview_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int
					positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int positions) {
				position = positions;
				picture_title.setText(positions + 1 + "/" + mList.size());
				if (mImageSelected != null) {
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
}
