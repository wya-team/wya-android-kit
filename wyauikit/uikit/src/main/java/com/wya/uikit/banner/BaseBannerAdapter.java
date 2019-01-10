package com.wya.uikit.banner;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 适配器
 */
public abstract class BaseBannerAdapter<T> {
    private List<T> data;
    private int layoutId;

    public BaseBannerAdapter(List<T> data, @LayoutRes int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
    }
    
    /**
     * convert
     * @param view
     * @param position
     * @param item
     */
    public abstract void convert(View view, int position, T item);

    protected List<T> getData() {
        return data;
    }

    protected int getLayoutId() {
        return layoutId;
    }
}