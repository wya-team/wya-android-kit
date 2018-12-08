package com.wya.example.module.utils.realm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;

import java.util.List;

/**
 * 创建日期：2018/11/21 11:58
 * 作者： Mao Chunjiang
 * 文件名称：DialogListAdapter
 * 类说明：Popupwind列表选择按钮
 */

public class RealmListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

   private List<String> data;
   private Context context;

   public RealmListAdapter(Context context, int layoutResId, @Nullable List<String> data) {
       super(layoutResId, data);
       this.data = data;
       this.context = context;
   }


    @SuppressLint("NewApi")
    @Override
    protected void convert(BaseViewHolder helper, String item) {
       helper.setText(R.id.tv_realm_name, item);
    }
}
