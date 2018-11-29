package com.wya.uikit.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wya.uikit.R;

import java.util.ArrayList;
import java.util.List;

public class PicturePreviewActivity extends Activity {

	private ImageView picture_left_back;
	private TextView picture_title;
	private LinearLayout ll_check;
	private CheckBox check;
	private PreviewViewPager preview_pager;
	private RelativeLayout select_bar_layout;
	private TextView tv_img_num;
	private TextView tv_ok;

	private int type;
	private int position;
	private List<String> images = new ArrayList<>();
	private PreviewPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_preview);


		type = getIntent().getIntExtra(GalleryConfig.TYPE, GalleryConfig.GALLERY);
		position = getIntent().getIntExtra(GalleryConfig.POSITION, -1);
		images = (List<String>) getIntent().getSerializableExtra(GalleryConfig.IMAGE_LIST);

		initView();
		switch (type) {
			case GalleryConfig.GALLERY:
				ll_check.setVisibility(View.GONE);
				select_bar_layout.setVisibility(View.GONE);
				break;
			case GalleryConfig.IMAGE_PICKER:
				ll_check.setVisibility(View.VISIBLE);
				select_bar_layout.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}


	}

	private void initView() {
		picture_left_back =  findViewById(R.id.picture_left_back);
		picture_title =  findViewById(R.id.picture_title);
		ll_check =  findViewById(R.id.ll_check);
		check =  findViewById(R.id.check);
		preview_pager =  findViewById(R.id.preview_pager);
		select_bar_layout = findViewById(R.id.select_bar_layout);
		tv_img_num = findViewById(R.id.tv_img_num);
		tv_ok = findViewById(R.id.tv_ok);

		mAdapter = new PreviewPagerAdapter(images, this);
		preview_pager.setAdapter(mAdapter);
		picture_title.setText(position + 1 + "/" + images.size());
		preview_pager.setCurrentItem(position);


		preview_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int
					positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				picture_title.setText(position + 1 + "/" + images.size());
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}
}
