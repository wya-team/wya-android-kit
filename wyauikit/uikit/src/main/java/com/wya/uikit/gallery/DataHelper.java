package com.wya.uikit.gallery;

import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/05
 * desc   : instance that to save data
 * version: 1.0
 */
public class DataHelper<T> {
	private List<T> mImages;
	private List<T> mImageSelected;
	private String value;
	private static  final DataHelper mDataHelper = new DataHelper();

	private DataHelper() {

	}

	public static DataHelper getInstance() {
		return mDataHelper;
	}

	public List<T> getImages() {
		return mImages;
	}

	public void setImages(List<T> images) {
		mImages =  images;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<T> getImageSelected() {
		return mImageSelected;
	}

	public void setImageSelected(List<T> imageSelected) {
		mImageSelected = imageSelected;
	}
}
