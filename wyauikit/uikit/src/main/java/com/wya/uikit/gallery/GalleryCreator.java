package com.wya.uikit.gallery;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.wya.uikit.imagepicker.LocalMedia;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   : GalleryCreator
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

	public void openPreviewGallery(int position, ArrayList<String> images) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), PicturePreviewActivity.class);
		intent.putExtra(GalleryConfig.POSITION, position);
		intent.putStringArrayListExtra(GalleryConfig.IMAGE_LIST, images);
		intent.putExtra(GalleryConfig.TYPE, GalleryConfig.GALLERY);
		getActivity().startActivity(intent);
	}


	public void openPreviewImagePicker(int position, List<LocalMedia>images, List<LocalMedia>
			imagesSelected, int result, int max) {

		Intent intent = new Intent();
		intent.setClass(getActivity(), PicturePreviewActivity.class);
		intent.putExtra(GalleryConfig.POSITION, position);
		DataHelper.getInstance().setImages(images);
		DataHelper.getInstance().setImageSelected(imagesSelected);
		intent.putExtra(GalleryConfig.TYPE, GalleryConfig.IMAGE_PICKER);
		intent.putExtra(GalleryConfig.PICKER_FOR_RESULT, result);
		intent.putExtra(GalleryConfig.MAX_NUM, max);
		getActivity().startActivityForResult(intent, result);
	}

	@Nullable
	private Fragment getFragment() {
		return mFragment.get();
	}

	public Activity getActivity() {
		return mActivity.get();
	}
}
