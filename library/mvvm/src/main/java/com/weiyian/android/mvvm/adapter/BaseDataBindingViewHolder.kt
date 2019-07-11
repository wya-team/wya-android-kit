package com.weiyian.android.mvvm.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

class BaseDataBindingViewHolder<T : Any, DB : ViewDataBinding>(
        view: View,
        bindBinding: (View) -> DB,
        private val callback: (T, DB, Int) -> Unit = { _, _, _ -> }
) : RecyclerView.ViewHolder(view) {

    private var binding: DB? = null

    init {
        binding = bindBinding(view)
    }

    fun bind(data: T, position: Int) {
        callback(data, binding!!, position)
    }
}
