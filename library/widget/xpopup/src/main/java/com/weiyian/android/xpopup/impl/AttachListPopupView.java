package com.weiyian.android.xpopup.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lxj.easyadapter.CommonAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.weiyian.android.xpopup.R;
import com.weiyian.android.xpopup.core.BaseAttachPopupView;
import com.weiyian.android.xpopup.interfaces.OnSelectListener;

import java.util.Arrays;

/**
 * Description: Attach类型的列表弹窗
 * Create by dance, at 2018/12/12
 *
 * @author :
 */
public class AttachListPopupView extends BaseAttachPopupView {
    
    RecyclerView recyclerView;
    
    public AttachListPopupView(@NonNull Context context) {
        super(context);
    }
    
    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_attach_impl_list;
    }
    
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        recyclerView = findViewById(R.id.rv_list);
        final CommonAdapter<String> adapter = new CommonAdapter<String>(R.layout._xpopup_adapter_text, Arrays.asList(data)) {
            @Override
            protected void convert(@NonNull ViewHolder holder, @NonNull String s, int position) {
                holder.setText(R.id.tv_text, s);
                if (iconIds != null && iconIds.length > position) {
                    holder.setVisible(R.id.iv_image, true);
                    holder.setBackgroundRes(R.id.iv_image, iconIds[position]);
                } else {
                    holder.setVisible(R.id.iv_image, false);
                }
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (selectListener != null) {
                    selectListener.onSelect(position, adapter.getDatas().get(position));
                }
                if (popupInfo.autoDismiss) {
                    dismiss();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
    
    String[] data;
    int[] iconIds;
    
    public AttachListPopupView setStringData(String[] data, int[] iconIds) {
        this.data = data;
        this.iconIds = iconIds;
        return this;
    }
    
    public AttachListPopupView setOffsetXAndY(int offsetX, int offsetY) {
        this.defaultOffsetX += offsetX;
        this.defaultOffsetY += offsetY;
        return this;
    }
    
    private OnSelectListener selectListener;
    
    public AttachListPopupView setOnSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }
}
