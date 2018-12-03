package com.wya.uikit.imagepicker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/29
 * desc   : local image info
 * version: 1.0
 */
public class LocalImage implements Parcelable {
	private String path;
	private String type;
	private int width;
	private int height;
	private boolean isChecked;

	public LocalImage(String path, String type, int width, int height) {
		this.path = path;
		this.type = type;
		this.width = width;
		this.height = height;
	}

	protected LocalImage(Parcel in) {
		path = in.readString();
		type = in.readString();
		width = in.readInt();
		height = in.readInt();
		isChecked = in.readInt() == 1;
	}

	public static final Creator<LocalImage> CREATOR = new Creator<LocalImage>() {
		@Override
		public LocalImage createFromParcel(Parcel in) {
			return new LocalImage(in);
		}

		@Override
		public LocalImage[] newArray(int size) {
			return new LocalImage[size];
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.path);
		dest.writeString(this.type);
		dest.writeInt(this.width);
		dest.writeInt(this.height);
		dest.writeInt(this.isChecked ? 1 : 0);
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	@Override
	public boolean equals( Object obj) {
		LocalImage localImage = (LocalImage) obj;
		return localImage.getPath().equals(path);
	}
}
