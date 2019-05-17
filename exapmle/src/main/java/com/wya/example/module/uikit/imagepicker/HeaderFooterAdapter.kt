/*
 *    Copyright (C) 2016 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.wya.example.module.uikit.imagepicker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wya.example.R
import com.wya.uikit.advrecyclerview.composedadapter.ComposedAdapter
import com.wya.uikit.advrecyclerview.headerfooter.AbstractHeaderFooterWrapperAdapter
import com.wya.uikit.advrecyclerview.utils.RecyclerViewAdapterUtils
import com.wya.uikit.advrecyclerview.utils.WrapperAdapterUtils
import java.util.*

internal class HeaderFooterAdapter(adapter: RecyclerView.Adapter<*>, private val mOnItemClickListener: OnListItemClickMessageListener)
    : AbstractHeaderFooterWrapperAdapter<HeaderFooterAdapter.FooterViewHolder, HeaderFooterAdapter.FooterViewHolder>()
        , View.OnClickListener {

    private val mFooterItems: MutableList<HeaderFooterItem>

    init {
        setAdapter(adapter)
        mFooterItems = ArrayList()
        addFooterItem()
    }

    override fun getHeaderItemCount(): Int {
        return 0
    }

    override fun getFooterItemCount(): Int {
        return mFooterItems.size
    }

    override fun getHeaderItemViewType(localPosition: Int): Int {
        return -1
    }

    override fun getFooterItemViewType(localPosition: Int): Int {
        return mFooterItems[localPosition].viewType
    }

    override fun onCreateHeaderItemViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.picker_layout_add_item, parent, false)
        val vh = FooterViewHolder(v)
        vh.itemView.setOnClickListener(this)
        return vh
    }

    override fun onCreateFooterItemViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.picker_layout_add_item, parent, false)
        val vh = FooterViewHolder(v)
        vh.itemView.setOnClickListener(this)
        return vh
    }

    override fun onBindHeaderItemViewHolder(holder: FooterViewHolder, localPosition: Int) {}

    override fun onBindFooterItemViewHolder(holder: FooterViewHolder, localPosition: Int) {
        holder.ivAdd.setOnClickListener { mOnItemClickListener.onItemClicked("") }
    }

    fun addFooterItem() {
        val viewType = 0
        mFooterItems.add(HeaderFooterItem(viewType))
        footerAdapter.notifyItemInserted(mFooterItems.size - 1)
    }

    fun removeFooterItem() {
        when {
            mFooterItems.isEmpty() -> return
            else -> {
                mFooterItems.removeAt(mFooterItems.size - 1)
                footerAdapter.notifyItemRemoved(mFooterItems.size)
            }
        }
    }

    override fun onClick(v: View) {
        val rv = RecyclerViewAdapterUtils.getParentRecyclerView(v)
        val vh = rv?.findContainingViewHolder(v)
        when (val rootPosition = vh?.adapterPosition) {
            RecyclerView.NO_POSITION -> return
            else -> {
                val rootAdapter = rv?.adapter
                val localPosition = WrapperAdapterUtils.unwrapPosition(rootAdapter!!, this, rootPosition!!)

                val segmentedPosition = getSegmentedPosition(localPosition)
                val segment = ComposedAdapter.extractSegmentPart(segmentedPosition)
                val offset = ComposedAdapter.extractSegmentOffsetPart(segmentedPosition)

                val message: String

                message = when (segment) {
                    SEGMENT_TYPE_HEADER -> "CLICKED: Header item $offset"
                    SEGMENT_TYPE_FOOTER -> "CLICKED: Footer item $offset"
                    else -> throw IllegalStateException("Something wrong.")
                }
                mOnItemClickListener.onItemClicked(message)
            }
        }

    }

    internal class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivAdd: ImageView = itemView.findViewById(R.id.iv_add)
    }

    internal class HeaderFooterItem(val viewType: Int)

}