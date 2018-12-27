package com.wya.uikit.imagepicker;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   : local image info
 * version: 1.0
 */
public class LocalMedia implements Parcelable {
	private String path;
	private String type;
	private String cropPath;
	private Bitmap cropBitmap;

	public LocalMedia(String path, String type) {
		this.path = path;
		this.type = type;
	}

	protected LocalMedia(Parcel in) {
		path = in.readString();
		type = in.readString();
		cropPath = in.readString();
	}

	public static final Creator<LocalMedia> CREATOR = new Creator<LocalMedia>() {
		@Override
		public LocalMedia createFromParcel(Parcel in) {
			return new LocalMedia(in);
		}

		@Override
		public LocalMedia[] newArray(int size) {
			return new LocalMedia[size];
		}
	};

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.path);
		dest.writeString(this.type);
		dest.writeString(this.cropPath);
	}



	@Override
	public boolean equals( Object obj) {
		LocalMedia localMedia = (LocalMedia) obj;
		return localMedia.getPath().equals(path);
	}

	public String getCropPath() {
		return cropPath;
	}

	public void setCropPath(String cropPath) {
		this.cropPath = cropPath;
	}

	public Bitmap getCropBitmap() {
		return cropBitmap;
	}

	public void setCropBitmap(Bitmap cropBitmap) {
		this.cropBitmap = cropBitmap;
	}
}
