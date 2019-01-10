package com.wya.uikit.pickerview;

import android.support.annotation.NonNull;

import com.wya.uikit.pickerview.wheelview.adapter.WheelAdapter;

import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 适配器
 */
public class PickerViewAdapter<T> implements WheelAdapter {
    private List<T> mData;
    
    public PickerViewAdapter(@NonNull List<T> data) {
        mData = data;
    }
    
    @Override
    public int getItemsCount() {
        return mData.size();
    }
    
    @Override
    public Object getItem(int index) {
        return mData.get(index);
    }
    
    @Override
    public int indexOf(Object o) {
        return mData.indexOf(o);
    }
    
}
