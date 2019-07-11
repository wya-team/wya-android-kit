package com.weiyian.android.xpopup.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lxj.easyadapter.CommonAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.weiyian.android.xpopup.R;
import com.weiyian.android.xpopup.XPopup;
import com.weiyian.android.xpopup.core.CenterPopupView;
import com.weiyian.android.xpopup.interfaces.OnSelectListener;
import com.weiyian.android.xpopup.widget.CheckView;

import java.util.Arrays;

/**
 * Description: 在中间的列表对话框
 * Create by dance, at 2018/12/16
 *
 * @author :
 */
public class CenterListPopupView extends CenterPopupView {
    
    private RecyclerView recyclerView;
    private TextView tvTitle;
    
    public CenterListPopupView(@NonNull Context context) {
        super(context);
    }
    
    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_center_impl_list;
    }
    
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        recyclerView = findViewById(R.id.rv_list);
        tvTitle = findViewById(R.id.tv_title);
        
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(GONE);
        } else {
            tvTitle.setText(title);
        }
        
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
                
                // 对勾View
                if (checkedPosition != -1) {
                    holder.setVisible(R.id.check_view, position == checkedPosition);
                    holder.<CheckView>getView(R.id.check_view).setColor(XPopup.getPrimaryColor());
                    holder.setTextColor(R.id.tv_text, position == checkedPosition ?
                            XPopup.getPrimaryColor() : getResources().getColor(R.color._xpopup_title_color));
                }
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (selectListener != null) {
                    if (position >= 0 && position < adapter.getDatas().size())
                        selectListener.onSelect(position, adapter.getDatas().get(position));
                }
                if (checkedPosition != -1) {
                    checkedPosition = position;
                    adapter.notifyDataSetChanged();
                }
                if (popupInfo.autoDismiss) {
                    dismiss();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
    
    String title;
    String[] data;
    int[] iconIds;
    
    public CenterListPopupView setStringData(String title, String[] data, int[] iconIds) {
        this.title = title;
        this.data = data;
        this.iconIds = iconIds;
        return this;
    }
    
    private OnSelectListener selectListener;
    
    public CenterListPopupView setOnSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }
    
    int checkedPosition = -1;
    
    /**
     * 设置默认选中的位置
     *
     * @param position
     * @return
     */
    public CenterListPopupView setCheckedPosition(int position) {
        this.checkedPosition = position;
        return this;
    }
    
    @Override
    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? (int) (super.getMaxWidth() * .8f)
                : popupInfo.maxWidth;
    }
}
