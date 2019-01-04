package com.wya.example.module.uikit.customitems.grid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.utils.utils.ColorUtil;

import java.util.List;

/**
  * @date: 2018/11/30 9:24
  * @author: Chunjiang Mao
  * @classname: GridAdapter
  * @describe: GridAdapter
  */

public class GridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
   private Context context;

   public GridAdapter(Context context, int layoutResId, @Nullable List<String> data) {
       super(layoutResId, data);
       this.context = context;
   }

   @SuppressLint("NewApi")
   @Override
   protected void convert(BaseViewHolder helper, String item) {
       helper.setText(R.id.tv_grid_item, item);
       helper.setBackgroundColor(R.id.img_grid_item, ColorUtil.hex2Int("#eeeeee"));
   }
}
