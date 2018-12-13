package com.wya.example.module.uikit.customitems.expandrecyclerview.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.uikit.expandrecyclerview.holder.BaseViewHolder;


 /**
  * 创建日期：2018/12/13 9:56
  * 作者： Mao Chunjiang
  * 文件名称：ItemViewHolder
  * 类说明：ItemViewHolder
  */


public class ItemViewHolder extends BaseViewHolder {

    public TextView tvName;
    public TextView tvTitle;

    public ItemViewHolder(Context ctx, View itemView, int viewType) {
        super(ctx,itemView, viewType);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
    }

    @Override
    public int getGroupViewResId() {
        return R.id.group;
    }

    @Override
    public int getChildViewResId() {
        return R.id.child;
    }
}
