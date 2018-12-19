package com.wya.uikit.banner;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

public abstract class BannerAdapter<T> {
    private List<T>data;
    private int layoutId;


    public BannerAdapter(List<T> data, @LayoutRes int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
    }

    public abstract void convert(View view, int position, T item);

    protected List<T> getData() {
        return data;
    }

    protected int getLayoutId() {
        return layoutId;
    }
}