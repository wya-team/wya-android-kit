package com.wya.uikit.gallery;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 */
public class GalleryCreator {
	private final WeakReference<Activity> mActivity;
	private final WeakReference<Fragment> mFragment;

	private GalleryCreator(Activity activity) {
		this(activity, null);
	}

	private GalleryCreator(Fragment fragment) {
		this(fragment.getActivity(), fragment);
	}

	private GalleryCreator(Activity activity, Fragment fragment) {
		mActivity = new WeakReference<>(activity);
		mFragment = new WeakReference<>(fragment);
	}

	public static GalleryCreator create(Activity activity) {
		return new GalleryCreator(activity);
	}

	public static GalleryCreator create(Fragment fragment) {
		return new GalleryCreator(fragment);
	}


	public void openPreviewGallery(int position, List<String> images) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), PicturePreviewActivity.class);
		intent.putExtra(GalleryConfig.POSITION, position);
		intent.putExtra(GalleryConfig.IMAGE_LIST, (Serializable) images);
		intent.putExtra(GalleryConfig.TYPE, GalleryConfig.GALLERY);
		getActivity().startActivity(intent);
	}

	public void openPreviewImagePicker(int position, List<String> images) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), PicturePreviewActivity.class);
		intent.putExtra(GalleryConfig.POSITION, position);
		intent.putExtra(GalleryConfig.IMAGE_LIST, (Serializable) images);
		getActivity().startActivity(intent);
	}

	/**
	 * @return Activity.
	 */
	@Nullable
	private Activity getActivity() {
		return mActivity.get();
	}

	@Nullable
	private Fragment getFragment() {
		return mFragment.get();
	}

}
