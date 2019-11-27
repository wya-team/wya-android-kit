package com.weiyian.android.fuzzy;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :
 */
public abstract class BaseFuzzySearchBaseAdapter<ITEM extends IFuzzySearchItem, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements Filterable {
    
    private FuzzySearchFilter mFilter;
    public List<ITEM> mBackDataList;
    public List<ITEM> mDataList;
    public IFuzzySearchRule mIFuzzySearchRule;
    
    public BaseFuzzySearchBaseAdapter(int layoutResId, @Nullable List<ITEM> dataList) {
        mIFuzzySearchRule = new DefaultFuzzySearchRule();
        mBackDataList = dataList;
        mDataList = dataList;
    }
    
    public void setDataList(List<ITEM> dataList) {
        mBackDataList = dataList;
        mDataList = dataList;
    }
    
    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }
    
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new FuzzySearchFilter();
        }
        return mFilter;
    }
    
    public class FuzzySearchFilter extends Filter {
        
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            List<ITEM> filterList;
            if (TextUtils.isEmpty(constraint)) {
                filterList = mBackDataList;
            } else {
                filterList = new ArrayList<>();
                for (ITEM item : mBackDataList) {
                    if (mIFuzzySearchRule.accept(constraint, item.getSourceKey(), item.getFuzzyKey())) {
                        filterList.add(item);
                    }
                }
            }
            result.values = filterList;
            result.count = filterList.size();
            return result;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataList = (List<ITEM>) results.values;
            if (null != mListener) {
                mListener.onResultsPublish(mDataList);
            }
            notifyDataSetChanged();
        }
    }
    
    public void setActionListener(ActionListener listener) {
        this.mListener = listener;
    }
    
    public ActionListener mListener;
    
    public List<ITEM> getBackDataList() {
        return mBackDataList;
    }
    
    public interface ActionListener<ITEM> {
        /**
         * 筛选结果
         *
         * @param itemList :
         */
        void onResultsPublish(List<ITEM> itemList);
    }
    
}
