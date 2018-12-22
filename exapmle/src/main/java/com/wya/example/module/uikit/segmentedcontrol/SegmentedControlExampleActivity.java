package com.wya.example.module.uikit.segmentedcontrol;

import android.content.Intent;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.segmentedcontrol.WYASegmentedView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SegmentedControlExampleActivity extends BaseActivity {

    @BindView(R.id.segment_normal)
    WYASegmentedView mSegmentNormal;
    @BindView(R.id.segment_more)
    WYASegmentedView mSegmentMore;
    @BindView(R.id.segment_color)
    WYASegmentedView mSegmentColor;
    @BindView(R.id.segment_enable)
    WYASegmentedView mSegmentEnable;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_segmented_control_example;
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);

        setToolBarTitle("分段控制器(segmentedcontrol)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help,true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(SegmentedControlExampleActivity.this, ReadmeActivity.class).putExtra("url",url));
        });

        mSegmentNormal.addTabs(new String[]{"标题1", "标题2"});
        mSegmentNormal.setOnItemClickListener(new WYASegmentedView.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                Toast.makeText(SegmentedControlExampleActivity.this, "标题" + position, Toast
                        .LENGTH_SHORT).show();
            }

        });


        mSegmentMore.addTabs(new String[]{"标题1", "标题2", "标题3"});
        mSegmentMore.setOnItemClickListener(new WYASegmentedView.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                Toast.makeText(SegmentedControlExampleActivity.this, "标题" + position, Toast
                        .LENGTH_SHORT).show();
            }
        });
        mSegmentMore.setItemClicked(1);

        mSegmentColor.addTabs(new String[]{"标题1", "标题2", "标题3"});
        mSegmentColor.setOnItemClickListener(new WYASegmentedView.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                Toast.makeText(SegmentedControlExampleActivity.this, "标题" + position, Toast
                        .LENGTH_SHORT).show();
            }
        });
        mSegmentEnable.addTabs(new String[]{"标题1", "标题2"});
        mSegmentEnable.setEnabled(false);

    }

}
