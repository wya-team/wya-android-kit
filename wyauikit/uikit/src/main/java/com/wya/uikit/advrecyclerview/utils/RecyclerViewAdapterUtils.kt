/*
 *    Copyright (C) 2015 Haruki Hasegawa
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

package com.wya.uikit.advrecyclerview.utils

import android.support.v7.widget.RecyclerView
import android.view.View

object RecyclerViewAdapterUtils {

    /**
     * Gets parent RecyclerView instance.
     *
     * @param view Child view of the RecyclerView's item
     * @return Parent RecyclerView instance
     */
    fun getParentRecyclerView(view: View?): RecyclerView? {
        if (view == null) {
            return null
        }
        val parent = view.parent
        return parent as? RecyclerView ?: when (parent) {
            is View -> getParentRecyclerView(parent as View)
            else -> null
        }
    }

    /**
     * Gets directly child of RecyclerView (== [android.support.v7.widget.RecyclerView.ViewHolder.itemView]})
     *
     * @param view Child view of the RecyclerView's item
     * @return Item view
     */
    fun getParentViewHolderItemView(view: View?): View? {
        val rv = getParentRecyclerView(view) ?: return null
        return rv.findContainingItemView(view!!)
    }

    /**
     * Gets [android.support.v7.widget.RecyclerView.ViewHolder].
     *
     * @param view Child view of the RecyclerView's item
     * @return ViewHolder
     */
    fun getViewHolder(view: View?): RecyclerView.ViewHolder? {
        val rv = getParentRecyclerView(view) ?: return null
        return rv.findContainingViewHolder(view!!)
    }

}
