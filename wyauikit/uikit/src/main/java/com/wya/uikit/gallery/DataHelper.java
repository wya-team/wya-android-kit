package com.wya.uikit.gallery;

import com.wya.uikit.imagepicker.LocalMedia;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/05
 * desc   : instance that to save data
 * version: 1.0
 */
public class DataHelper {
	private List<LocalMedia> mImages;
	private List<LocalMedia> mImageSelected;
	private String value;
	private static  final DataHelper mDataHelper = new DataHelper();

	private DataHelper() {

	}

	public static DataHelper getInstance() {
		return mDataHelper;
	}

	public List<LocalMedia> getImages() {
		return mImages;
	}

	public void setImages(List<LocalMedia> images) {
		mImages =  images;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<LocalMedia> getImageSelected() {
		return mImageSelected;
	}

	public void setImageSelected(List<LocalMedia> imageSelected) {
		mImageSelected = imageSelected;
	}
}
