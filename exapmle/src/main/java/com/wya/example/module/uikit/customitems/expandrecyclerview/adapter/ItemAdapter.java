package com.wya.example.module.uikit.customitems.expandrecyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wya.example.R;
import com.wya.example.module.uikit.customitems.expandrecyclerview.bean.Item;
import com.wya.example.module.uikit.customitems.expandrecyclerview.holder.ItemViewHolder;
import com.wya.uikit.expandrecyclerview.adapter.BaseRecyclerViewAdapter;
import com.wya.uikit.expandrecyclerview.bean.RecyclerViewData;

import java.util.List;
 /**
  * 创建日期：2018/12/13 9:56
  * 作者： Mao Chunjiang
  * 文件名称：ItemAdapter
  * 类说明：ItemAdapter
  */

public class ItemAdapter extends BaseRecyclerViewAdapter<String, Item, ItemViewHolder> {

    private Context ctx;
    private LayoutInflater mInflater;

    public ItemAdapter(Context ctx, List<RecyclerViewData> datas) {
        super(ctx, datas);
        mInflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
    }

    /**
     * head View数据设置
     *
     * @param holder
     * @param groupPos
     * @param position
     * @param groupData
     */
    @Override
    public void onBindGroupHolder(ItemViewHolder holder, int groupPos, int position, String groupData) {
        holder.tvTitle.setText(groupData);
    }

    /**
     * child View数据设置
     *
     * @param holder
     * @param groupPos
     * @param childPos
     * @param position
     * @param childData
     */
    @Override
    public void onBindChildpHolder(ItemViewHolder holder, int groupPos, int childPos, int position, Item childData) {
        holder.tvName.setText(childData.getName());
    }

    @Override
    public View getGroupView(ViewGroup parent) {
        return mInflater.inflate(R.layout.title_item_layout, parent, false);
    }

    @Override
    public View getChildView(ViewGroup parent) {
        return mInflater.inflate(R.layout.item_layout, parent, false);
    }

    @Override
    public ItemViewHolder createRealViewHolder(Context ctx, View view, int viewType) {
        return new ItemViewHolder(ctx, view, viewType);
    }

    /**
     * true 全部可展开
     * fasle  同一时间只能展开一个
     */
    @Override
    public boolean canExpandAll() {
        return true;
    }
}
