package com.weiyian.android.mvvm.binding.support

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.support.v4.widget.SwipeRefreshLayout

object SwipeRefreshLayoutBinding {

    @JvmStatic
    @BindingAdapter("app:bind_swipeRefreshLayout_refreshing")
    fun setSwipeRefreshLayoutRefreshing(
            swipeRefreshLayout: SwipeRefreshLayout,
            newValue: Boolean
    ) {
        if (swipeRefreshLayout.isRefreshing != newValue)
            swipeRefreshLayout.isRefreshing = newValue
    }

    @JvmStatic
    @InverseBindingAdapter(
            attribute = "app:bind_swipeRefreshLayout_refreshing",
            event = "app:bind_swipeRefreshLayout_refreshingAttrChanged"
    )
    fun isSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean =
            swipeRefreshLayout.isRefreshing

    @JvmStatic
    @BindingAdapter(
            "app:bind_swipeRefreshLayout_refreshingAttrChanged",
            requireAll = false
    )
    fun setOnRefreshListener(
            swipeRefreshLayout: SwipeRefreshLayout,
            bindingListener: InverseBindingListener?
    ) {
        if (bindingListener != null)
            swipeRefreshLayout.setOnRefreshListener {
                bindingListener.onChange()
            }
    }
}