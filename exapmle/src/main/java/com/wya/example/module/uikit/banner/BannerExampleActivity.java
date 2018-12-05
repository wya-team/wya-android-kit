package com.wya.example.module.uikit.banner;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.banner.WYABanner;

import java.util.ArrayList;
import java.util.List;

public class BannerExampleActivity extends BaseActivity {


	private WYABanner<Integer> mWYABanner;
	private List<Integer> data = new ArrayList<>();

	@Override
	protected void initView() {
		data.add(R.mipmap.img1);
		data.add(R.mipmap.img2);
		data.add(R.mipmap.img3);


		mWYABanner = findViewById(R.id.banner);
		mWYABanner.setData(data);
		mWYABanner.setOnItemListener(new WYABanner.OnBannerListener<Integer>() {
			@Override
			public void bannerItem(View view, int position, Integer item) {
				ImageView imageView = (ImageView) view;
				imageView.setImageResource(item);

				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(BannerExampleActivity.this,position+"",Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
//		mWYABanner.setAutoPlay(false);
	}

	@Override
	protected int getLayoutID() {
		return R.layout.activity_banner_example;
	}
}
