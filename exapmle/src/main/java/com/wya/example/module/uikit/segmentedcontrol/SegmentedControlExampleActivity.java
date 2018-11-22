package com.wya.example.module.uikit.segmentedcontrol;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.wya.example.R;
import com.wya.uikit.segmentedcontrol.WYASegmentedView;
import com.wya.uikit.segmentedcontrol.WYATabLayoutControl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SegmentedControlExampleActivity extends AppCompatActivity implements CompoundButton
		.OnCheckedChangeListener {

	@BindView(R.id.fix)
	RadioButton mFix;
	@BindView(R.id.scroll)
	RadioButton mScroll;
	@BindView(R.id.line)
	RadioButton mLine;
	@BindView(R.id.segment)
	RadioButton mSegment;
	@BindView(R.id.tabLayout)
	TabLayout mTabLayout;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;
	@BindView(R.id.segment_layout)
	WYASegmentedView mSegmentLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_segmented_control_example);
		ButterKnife.bind(this);


		mFix.setOnCheckedChangeListener(this);
		mScroll.setOnCheckedChangeListener(this);
		mLine.setOnCheckedChangeListener(this);
		mSegment.setOnCheckedChangeListener(this);

		mViewpager.setAdapter(mFragmentPagerAdapter);
		mTabLayout.setupWithViewPager(mViewpager);
		mTabLayout.getTabAt(0).setText("标题1");
		mTabLayout.getTabAt(1).setText("标题2");
		mTabLayout.getTabAt(2).setText("标题3");
		mSegmentLayout.setVisibility(View.GONE);
		mSegmentLayout.addTabs(new String[]{"标题1","标题2","标题3"});
		mSegmentLayout.setOnItemClickListener(new WYASegmentedView.OnItemClickListener() {
			@Override
			public void itemClicked(int position) {
				mViewpager.setCurrentItem(position);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {

			switch (buttonView.getId()) {
				case R.id.fix:
					mTabLayout.setVisibility(View.VISIBLE);
					WYATabLayoutControl.clear(mTabLayout);
					mTabLayout.setTabMode(TabLayout.MODE_FIXED);
					mSegmentLayout.setVisibility(View.GONE);
					break;
				case R.id.scroll:
					mTabLayout.setVisibility(View.VISIBLE);
					mSegmentLayout.setVisibility(View.GONE);
					WYATabLayoutControl.clear(mTabLayout);
					mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
					break;
				case R.id.line:
					mTabLayout.setVisibility(View.VISIBLE);
					mSegmentLayout.setVisibility(View.GONE);
					WYATabLayoutControl.lineWidth(mTabLayout);
					break;
				case R.id.segment:
					mTabLayout.setVisibility(View.GONE);
					mSegmentLayout.setVisibility(View.VISIBLE);
					break;
			}
		}
	}

	FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter
			(getSupportFragmentManager()) {
		@Override
		public Fragment getItem(int position) {
			return ItemFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			return 3;
		}
	};
}
