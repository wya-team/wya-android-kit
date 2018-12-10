package com.wya.uikit.imagecrop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/10
 * desc   :
 * version: 1.0
 */
public class Crop {
	private WeakReference<Activity> mActivity;
	private WeakReference<Fragment> mFragment;
	private Intent intent;
	private String savePath;
	private Uri imageUri;
	private int quality;

	private Crop(Activity activity) {
		this(activity, null);
	}

	private Crop(Fragment fragment) {
		this(fragment.getActivity(), fragment);
	}

	private Crop(Activity activity, Fragment fragment) {
		mActivity = new WeakReference<>(activity);
		mFragment = new WeakReference<>(fragment);
		intent = new Intent(getActivity(), CropActivity.class);
	}

	public static Crop create(Activity activity) {
		return new Crop(activity);
	}

	public static Crop create(Fragment fragment) {
		return new Crop(fragment);
	}

	public void forResult(int requestCode) {
		intent.putExtra(CropActivity.EXTRA_IMAGE_URI, imageUri);
		intent.putExtra(CropActivity.EXTRA_IMAGE_SAVE_PATH, savePath);
		intent.putExtra(CropActivity.EXTRA_IMAGE_SAVE_QUALITY, quality);
		getActivity().startActivityForResult(intent, requestCode);
	}

	public Crop saveCropImagePath(String path) {
		savePath = path;
		return this;
	}

	public Crop setImagePath(Uri uri) {
		imageUri = uri;
		return this;
	}

	public Crop CropQuality(int quality) {
		this.quality = quality;
		return this;
	}


	private Activity getActivity() {
		return mActivity.get();
	}

	private Fragment getFragent() {
		return mFragment.get();
	}
}
