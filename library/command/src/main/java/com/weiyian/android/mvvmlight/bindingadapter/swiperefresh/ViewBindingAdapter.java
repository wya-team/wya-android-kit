package com.weiyian.android.mvvmlight.bindingadapter.swiperefresh;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

import com.weiyian.android.mvvmlight.command.ReplyCommand0;

public class ViewBindingAdapter {
    
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand0 onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (onRefreshCommand != null) {
                onRefreshCommand.execute();
            }
        });
    }
    
}
