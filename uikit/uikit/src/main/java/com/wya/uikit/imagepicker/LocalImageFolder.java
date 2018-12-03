package com.wya.uikit.imagepicker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 */
public class LocalImageFolder implements Parcelable {
	private String name;
	private List<LocalImage>mImages;
	private int imageNum;
	private String firstImagePath;


	public LocalImageFolder() {
	}

	protected LocalImageFolder(Parcel in) {
		name = in.readString();
		mImages = in.createTypedArrayList(LocalImage.CREATOR);
		imageNum = in.readInt();
		firstImagePath = in.readString();
	}

	public static final Creator<LocalImageFolder> CREATOR = new Creator<LocalImageFolder>() {
		@Override
		public LocalImageFolder createFromParcel(Parcel in) {
			return new LocalImageFolder(in);
		}

		@Override
		public LocalImageFolder[] newArray(int size) {
			return new LocalImageFolder[size];
		}
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LocalImage> getImages() {
		if (mImages == null) {
			mImages = new ArrayList<>();
		}
		return mImages;
	}

	public void setImages(List<LocalImage> images) {
		mImages = images;
	}

	public int getImageNum() {
		return imageNum;
	}

	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}

	public String getFirstImagePath() {
		return firstImagePath;
	}

	public void setFirstImagePath(String firstImagePath) {
		this.firstImagePath = firstImagePath;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.firstImagePath);
		dest.writeList(this.mImages);
		dest.writeInt(imageNum);
	}
}
