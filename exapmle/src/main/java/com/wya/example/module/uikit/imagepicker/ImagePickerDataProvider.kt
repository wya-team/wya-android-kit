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

package com.wya.example.module.uikit.imagepicker

import android.text.TextUtils
import android.util.Log
import java.util.*

/**
 * @author :
 */
class ImagePickerDataProvider(imageList: List<String>) : AbstractDataProvider() {

    private val mData: MutableList<ConcreteData>
    private var mLastRemovedData: ConcreteData? = null
    private var mLastRemovedPosition = -1

    private var mCurrentId = 0

    val data: List<ConcreteData>
        get() = mData

    override val count: Int
        get() = mData.size

    init {
        mData = LinkedList()
        for (i in imageList.indices) {
            when {
                !TextUtils.isEmpty(imageList[i]) -> {
                    val id = mCurrentId.toLong()
                    mCurrentId++
                    val viewType = 0
                    val text = i.toString()
                    mData.add(ConcreteData(id, viewType, text, 0, imageList[i]))
                }
            }
        }
    }

    fun addData(item: String) {
        when {
            !TextUtils.isEmpty(item) -> {
                val id = mCurrentId++.toLong()
                val viewType = 0
                val text = id.toString()
                mData.add(ConcreteData(id, viewType, text, 0, item))
            }
        }
    }

    override fun getItem(index: Int): Data {
        Log.e("TAG", "[getItem] index = " + index + " , list.size = " + mData.size)
        when {
            index < 0 || index >= count -> throw IndexOutOfBoundsException("index = $index")
            else -> return mData[index]
        }
    }

    override fun undoLastRemoval(): Int {
        when {
            mLastRemovedData != null -> {
                val insertedPosition: Int = when {
                    mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size -> mLastRemovedPosition
                    else -> mData.size
                }

                mData.add(insertedPosition, mLastRemovedData!!)

                mLastRemovedData = null
                mLastRemovedPosition = -1

                return insertedPosition
            }
            else -> return -1
        }
    }

    override fun moveItem(fromPosition: Int, toPosition: Int) {
        when (fromPosition) {
            toPosition -> return
            else -> {
                val item = mData.removeAt(fromPosition)
                mData.add(toPosition, item)
                mLastRemovedPosition = -1
            }
        }
    }

    override fun swapItem(fromPosition: Int, toPosition: Int) {
        when (fromPosition) {
            toPosition -> return
            else -> {
                Collections.swap(mData, toPosition, fromPosition)
                mLastRemovedPosition = -1
            }
        }
    }

    override fun removeItem(position: Int) {
        when {
            position < 0 || position >= mData.size -> return
            else -> {
                val removedItem = mData.removeAt(position)

                mLastRemovedData = removedItem
                mLastRemovedPosition = position
            }
        }
    }

    class ConcreteData internal constructor(override val id: Long, override val viewType: Int, text: String, swipeReaction: Int, override val imageUrl: String) : Data() {

        override val text: String
        override var isPinned = false

        override fun isSectionHeader() = false

        init {
            this.text = makeText(id, text, swipeReaction)
        }

        private fun makeText(id: Long, text: String, swipeReaction: Int): String {
            return "$id - $text"
        }

        override fun toString(): String {
            return text
        }
    }

}
