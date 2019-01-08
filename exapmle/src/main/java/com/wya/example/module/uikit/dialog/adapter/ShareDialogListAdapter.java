package com.wya.example.module.uikit.dialog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.example.module.uikit.dialog.bean.ShareItem;

import java.util.List;

/**
 * @date: 2018/11/21 11:58
 * @author: Chunjiang Mao
 * @classname: DialogListAdapter
 * @describe: 分享提示框适配器
 */

public class ShareDialogListAdapter extends BaseQuickAdapter<ShareItem, BaseViewHolder> {
    
    private List<ShareItem> data;
    private Context context;
    
    public ShareDialogListAdapter(Context context, int layoutResId, @Nullable List<ShareItem> data) {
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
    protected void convert(BaseViewHolder helper, ShareItem item) {
        helper.setText(R.id.tv_share_item, item.getName());
        helper.setImageDrawable(R.id.img_share_item, context.getResources().getDrawable(item.getImage()));
    }
}
