package com.wya.example.module.uikit.customitems.cardview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.uikit.customitems.WYACardView;

import java.util.List;

 /**
  * 创建日期：2018/11/29 17:05
  * 作者： Mao Chunjiang
  * 文件名称：CardViewAdapter
  * 类说明：卡片适配器
  */

public class CardViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public CardViewAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @SuppressLint("NewApi")
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((WYACardView)helper.getView(R.id.wya_card_view)).setTitle(item);
    }
}
