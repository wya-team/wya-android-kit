package com.weiyian.android.xpopup.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.weiyian.android.xpopup.R;

/**
 * Description:
 * Create by dance, at 2018/12/12
 *
 * @author :
 */
public class VerticalRecyclerView extends RecyclerView {
    
    public VerticalRecyclerView(@NonNull Context context) {
        this(context, null);
    }
    
    public VerticalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public VerticalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        
        decoration.setDrawable(new ColorDrawable(getResources().getColor(R.color._xpopup_list_divider)));
        addItemDecoration(decoration);
    }
}
