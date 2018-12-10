package com.wya.example.module.uikit.paginationview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.uikit.segmentedcontrol.ItemFragment;
import com.wya.uikit.paginationview.WYAPaginationBottomView;
import com.wya.uikit.paginationview.WYAPaginationDot;

public class PaginationViewExampleActivity extends BaseActivity {

	private WYAPaginationBottomView mBottomView;
	private WYAPaginationDot mDot;
	private ViewPager mViewPager;

	@Override
	protected int getLayoutID() {
		return R.layout.activity_pagination_view_example;
	}


	@Override
	protected void initView() {
		mBottomView = findViewById(R.id.pagination_view);
		mBottomView.setAllNum(20);
		mBottomView.setPageSearchListener(new WYAPaginationBottomView.onPageSearchListener() {
			@Override
			public boolean onSearch(long page) {
				return true;
			}
		});

		mDot = findViewById(R.id.pagination_dot);
		mDot.setPointNumber(5);

		mViewPager = findViewById(R.id.viewpager);
		mViewPager.setAdapter(mAdapter);
		mDot.setUpWithViewPager(mViewPager);
	}

	FragmentPagerAdapter mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
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
