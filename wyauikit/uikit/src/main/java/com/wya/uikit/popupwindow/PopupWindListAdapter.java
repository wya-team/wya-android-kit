package com.wya.uikit.popupwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.uikit.R;

import java.util.List;

/**
 * 创建日期：2018/11/21 11:58
 * 作者： Mao Chunjiang
 * 文件名称：DialogListAdapter
 * 类说明：Popupwind列表选择按钮
 */

public class PopupWindListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


   private List<String> data;
   private Context context;

   public PopupWindListAdapter(Context context, int layoutResId, @Nullable List<String> data) {
       super(layoutResId, data);
       this.data = data;
       this.context = context;
   }


   @SuppressLint("NewApi")
   @Override
   public void onBindViewHolder(BaseViewHolder holder, int position) {
       super.onBindViewHolder(holder, position);
   }

   @Override
   protected void convert(BaseViewHolder helper, String item) {
       helper.setText(R.id.tv_menu_item, item);
   }
}
