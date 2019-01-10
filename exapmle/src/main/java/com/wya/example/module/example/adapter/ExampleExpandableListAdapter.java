package com.wya.example.module.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.module.example.bean.ExampleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2018/12/17 10:39
 * @author: Chunjiang Mao
 * @classname: DialogExpandableListAdapter
 * @describe:
 */

public class ExampleExpandableListAdapter extends BaseExpandableListAdapter {
    private List<ExampleItem> mDatas = new ArrayList<>();
    private Context context;
    
    public ExampleExpandableListAdapter(Context context, List<ExampleItem> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
    }
    
    /**
     * 获取分组的个数
     */
    @Override
    public int getGroupCount() {
        return mDatas.size();
    }
    
    /**
     * @param groupPosition
     * @return 获取指定分组中的子选项的个数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mDatas.get(groupPosition).getChild().size();
    }
    
    /**
     * @param groupPosition
     * @return 获取指定的分组数据
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }
    
    /**
     * @param groupPosition
     * @param childPosition
     * @return 获取指定分组中的指定子选项数据
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getChild().get(childPosition);
    }
    
    /**
     * @param groupPosition
     * @return 获取指定分组的ID, 这个ID必须是唯一的
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    
    /**
     * @param groupPosition
     * @param childPosition
     * @return 获取子选项的ID, 这个ID必须是唯一的
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    /**
     * @return 分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }
    
    /**
     * 获取显示指定分组的视图
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     */
    @SuppressLint("NewApi")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item_layout, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            groupViewHolder.imgDownUp = convertView.findViewById(R.id.img_down_up);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvTitle.setText(mDatas.get(groupPosition).getTitle());
        if (mDatas.get(groupPosition).isOpen()) {
            groupViewHolder.imgDownUp.setBackground(ContextCompat.getDrawable(context, R.drawable.icon_up));
        } else {
            groupViewHolder.imgDownUp.setBackground(ContextCompat.getDrawable(context, R.drawable.icon_down));
        }
        return convertView;
    }
    
    /**
     * 取得显示给定分组给定子位置的数据用的视图
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View, ViewGroup)
     */
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvTitle.setText(mDatas.get(groupPosition).getChild().get(childPosition));
        return convertView;
    }
    
    /**
     * @param groupPosition
     * @param childPosition
     * @return 指定位置上的子元素是否可选中
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
    static class GroupViewHolder {
        TextView tvTitle;
        ImageView imgDownUp;
    }
    
    static class ChildViewHolder {
        TextView tvTitle;
    }
}
