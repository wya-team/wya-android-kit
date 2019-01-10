package com.wya.uikit.optionmenu;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : optionMenu 适配器
 */
public abstract class BaseOptionMenuAdapter<T> extends RecyclerView.Adapter<OptionMenuViewHolder> {
    private static final String TAG = "BaseOptionMenuAdapter";
    private List<T> mData;
    private int layoutId;
    
    public BaseOptionMenuAdapter(List<T> data, @LayoutRes int layoutId) {
        mData = data;
        this.layoutId = layoutId;
    }
    
    @NonNull
    @Override
    public OptionMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new OptionMenuViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull OptionMenuViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    /**
     * convert
     *
     * @param viewHolder
     * @param item
     */
    protected abstract void convert(OptionMenuViewHolder viewHolder, T item);
}
