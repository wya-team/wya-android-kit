package com.wya.uikit.expandrecyclerview.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
 /**
  * 创建日期：2018/12/12 18:05
  * 作者： Mao Chunjiang
  * 文件名称：BaseViewHolder
  * 类说明：BaseViewHolder
  */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_PARENT = 1;
    public static final int VIEW_TYPE_CHILD = 2;

    public ViewGroup childView;

    public ViewGroup groupView;

    public BaseViewHolder(Context ctx, View itemView, int viewType) {
        super(itemView);
        switch (viewType) {
            case VIEW_TYPE_PARENT:
                groupView = (ViewGroup)itemView.findViewById(getGroupViewResId());
                break;
            case VIEW_TYPE_CHILD:
                childView = (ViewGroup) itemView.findViewById(getChildViewResId());
                break;
        }
    }

    /**
     * return ChildView root layout id
     */
    public abstract int getChildViewResId();

    /**
     * return GroupView root layout id
     * */
    public abstract int getGroupViewResId();


}