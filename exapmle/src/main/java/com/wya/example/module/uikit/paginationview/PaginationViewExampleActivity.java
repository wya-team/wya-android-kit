package com.wya.example.module.uikit.paginationview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wya.example.R;
import com.wya.example.module.uikit.segmentedcontrol.ItemFragment;
import com.wya.uikit.paginationview.WYAPaginationBottomView;
import com.wya.uikit.paginationview.WYAPaginationDot;

public class PaginationViewExampleActivity extends AppCompatActivity {

	private WYAPaginationBottomView mBottomView;
	private WYAPaginationDot mDot;
	private ViewPager mViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagination_view_example);

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
