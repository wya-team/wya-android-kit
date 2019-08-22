package com.wya.example.module.uikit.photoview;

import android.graphics.Rect;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.wya.uikit.photoview.preview.enitity.IThumbViewInfo;
/**
 * @date: 2019/7/4 17:29
 * @author: Chunjiang Mao
 * @classname: ImageItem
 * @describe:
 */
public class ImageItem implements IThumbViewInfo {
    private String url;
    private Rect mBounds;

    public void setBounds(Rect mBounds) {
        this.mBounds = mBounds;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Rect getBounds() {
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, flags);
    }

    public ImageItem() {
    }

    protected ImageItem(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
