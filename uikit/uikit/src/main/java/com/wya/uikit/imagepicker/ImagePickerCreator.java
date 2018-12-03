package com.wya.uikit.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/03
 * desc   : ImagePickerCreator
 * version: 1.0
 */
public class ImagePickerCreator {
	private WeakReference<Activity>mActivity;
	private WeakReference<Fragment>mFragment;
	private Intent intent;

	private ImagePickerCreator(Activity activity) {
		this(activity, null);
	}

	private ImagePickerCreator(Fragment fragment) {
		this(fragment.getActivity(), fragment);
	}

	private  ImagePickerCreator(Activity activity, Fragment fragment) {
		mActivity = new WeakReference<>(activity);
		mFragment = new WeakReference<>(fragment);
		intent = new Intent(getActivity(), ImagePickerActivity.class);
	}

	public static ImagePickerCreator create(Activity activity) {
		return new ImagePickerCreator(activity);
	}

	public static ImagePickerCreator create(Fragment fragment) {
		return new ImagePickerCreator(fragment);
	}

	public ImagePickerCreator forResult(int requestCode) {

		getActivity().startActivityForResult(intent, requestCode);
		return this;
	}

	public ImagePickerCreator maxImages(int num) {
		intent.putExtra(PickerConfig.IMAGE_NUMBER, num);
		return this;
	}

	private Activity getActivity() {
		return mActivity.get();
	}

	private Fragment getFragment() {
		return mFragment.get();
	}
}
