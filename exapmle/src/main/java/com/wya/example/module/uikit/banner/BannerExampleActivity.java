package com.wya.example.module.uikit.banner;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.banner.BaseBannerAdapter;
import com.wya.uikit.banner.WYABanner;
import com.wya.utils.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : banner Demo
 */
public class BannerExampleActivity extends BaseActivity {

    private WYABanner<Integer> mWYABanner;
    private WYABanner<Integer> mWYABanner2;
    private WYABanner<Integer> mScaleBanner;
    private List<Integer> data = new ArrayList<>();

    @Override
    protected void initView() {
        
        setTitle("轮播图(banner)");
        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(BannerExampleActivity.this, ReadmeActivity.class).putExtra
                    (EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            showShort("链接地址复制成功");
            StringUtil.copyString(BannerExampleActivity.this, url);
        });
        
        data.add(R.mipmap.img1);
        data.add(R.mipmap.img2);
        data.add(R.mipmap.img3);
        
        mWYABanner = (WYABanner<Integer>) findViewById(R.id.banner);
        mWYABanner2 = (WYABanner<Integer>) findViewById(R.id.banner2);
        mScaleBanner = (WYABanner<Integer>) findViewById(R.id.scale_banner);
        
        mWYABanner.setUpdateTime(2000)
                .setDotVisible(true)
                //				.setDotDark()
                .autoPlay(true);
        mWYABanner.setAdapter(new BaseBannerAdapter<Integer>(data, R.layout.banner_example_item) {
            @Override
            public void convert(View view, int position, Integer item) {
                ImageView imageView = view.findViewById(R.id.image);
                imageView.setImageResource(item);
            }
        });
        
        mScaleBanner.setUpdateTime(2000)
                .setDotVisible(true)
                //				.setDotDark()
                .setScale(20, 60, 60)
                .autoPlay(true);
        mScaleBanner.setAdapter(new BaseBannerAdapter<Integer>(data, R.layout.banner_example_item) {
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
        mWYABanner2.setAdapter(new BaseBannerAdapter<Integer>(data, R.layout.banner_default_item) {
            @Override
            public void convert(View view, int position, Integer item) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_example;
    }
}
