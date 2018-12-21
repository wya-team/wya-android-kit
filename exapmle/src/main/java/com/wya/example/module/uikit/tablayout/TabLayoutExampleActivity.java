package com.wya.example.module.uikit.tablayout;

import android.support.design.widget.TabLayout;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.segmentedcontrol.WYATabLayoutControl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutExampleActivity extends BaseActivity {

    @BindView(R.id.fixedTab)
    TabLayout mFixedTab;
    @BindView(R.id.scrollTab)
    TabLayout mScrollTab;
    @BindView(R.id.fixedTabText)
    TabLayout mFixedTabText;
    @BindView(R.id.scrollTabText)
    TabLayout mScrollTabText;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_tablayou_example;
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);

        setToolBarTitle("TabLayout");

        mFixedTab.addTab(mFixedTab.newTab().setText("标题1"));
        mFixedTab.addTab(mFixedTab.newTab().setText("标题2"));
        mFixedTab.addTab(mFixedTab.newTab().setText("标题3"));

        mScrollTab.addTab(mScrollTab.newTab().setText("标题1"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题222"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题3"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题444"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题555"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题6"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题7"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题8888"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题9"));
        mScrollTab.addTab(mScrollTab.newTab().setText("标题10"));

        mFixedTabText.addTab(mFixedTabText.newTab().setText("标题1"));
        mFixedTabText.addTab(mFixedTabText.newTab().setText("标题2"));
        mFixedTabText.addTab(mFixedTabText.newTab().setText("标题3"));
        WYATabLayoutControl.lineWidth(mFixedTabText);


        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题1111"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题2"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题3"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题4"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题5555"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题6"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题7"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题888"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题9"));
        mScrollTabText.addTab(mScrollTabText.newTab().setText("标题10"));
        WYATabLayoutControl.lineWidth(mScrollTabText);

    }
}
