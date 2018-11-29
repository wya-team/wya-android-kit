package com.wya.uikit.gallery;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wya.uikit.R;
import com.wya.uikit.gallery.photoview.PhotoView;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 */
public class PreviewPagerAdapter extends PagerAdapter {
	private List<String>mList;
	private Context mContext;

	public PreviewPagerAdapter(List<String> list, Context context) {
		mList = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		if (mList != null) {
			return mList.size();
		}
		return 0;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final View contentView = LayoutInflater.from(container.getContext())
				.inflate(R.layout.preview_item, container, false);
		// 常规图控件
		final PhotoView imageView = contentView.findViewById(R.id.preview_image);
//		// 长图控件
//		final SubsamplingScaleImageView longImg = (SubsamplingScaleImageView) contentView.findViewById(R.id.longImg);
//
//		ImageView iv_play = (ImageView) contentView.findViewById(R.id.iv_play);
		String imageUrl = mList.get(position);
		Glide.with(mContext).load(imageUrl).into(imageView);
		container.addView(contentView, 0);
		return contentView;
	}
}
