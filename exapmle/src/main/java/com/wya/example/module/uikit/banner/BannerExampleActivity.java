package com.wya.example.module.uikit.banner;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.banner.BannerAdapter;
import com.wya.uikit.banner.WYABanner;

import java.util.ArrayList;
import java.util.List;

public class BannerExampleActivity extends BaseActivity {


    private WYABanner<Integer> mWYABanner;
    private WYABanner<Integer> mWYABanner2;
    private WYABanner<Integer> scale_banner;
    private List<Integer> data = new ArrayList<>();

    @Override
    protected void initView() {

        setToolBarTitle("轮播图(banner)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(BannerExampleActivity.this, ReadmeActivity.class).putExtra
                    ("url", url));
        });

        data.add(R.mipmap.img1);
        data.add(R.mipmap.img2);
        data.add(R.mipmap.img3);


        mWYABanner = (WYABanner<Integer>) findViewById(R.id.banner);
        mWYABanner2 = (WYABanner<Integer>) findViewById(R.id.banner2);
        scale_banner = (WYABanner<Integer>) findViewById(R.id.scale_banner);


        mWYABanner.setUpdateTime(2000)
                .setDotVisible(true)
//				.setDotDark()
                .autoPlay(true);
        mWYABanner.setAdapter(new BannerAdapter<Integer>(data, R.layout.banner_example_item) {
            @Override
            public void convert(View view, int position, Integer item) {
                ImageView imageView = view.findViewById(R.id.image);
                imageView.setImageResource(item);
            }
        });


        scale_banner.setUpdateTime(2000)
                .setDotVisible(true)
//				.setDotDark()
                .setScale(20, 60, 60)
                .autoPlay(true);
        scale_banner.setAdapter(new BannerAdapter<Integer>(data, R.layout.banner_example_item) {
            @Override
            public void convert(View view, int position, Integer item) {
                ImageView imageView = view.findViewById(R.id.image);
                imageView.setImageResource(item);
            }
        });


        mWYABanner2.setUpdateTime(2000)
                .setDotVisible(true)
                .setDotDark()
                .autoPlay(true);
        mWYABanner2.setAdapter(new BannerAdapter<Integer>(data, R.layout.banner_default_item) {
            @Override
            public void convert(View view, int position, Integer item) {
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_banner_example;
    }
}
