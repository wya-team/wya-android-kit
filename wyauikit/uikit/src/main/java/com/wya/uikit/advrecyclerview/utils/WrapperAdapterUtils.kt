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
import com.wya.uikit.advrecyclerview.adapter.*
import java.util.*

object WrapperAdapterUtils {

    fun <T> findWrappedAdapter(adapter: RecyclerView.Adapter<*>, clazz: Class<T>): T? {
        return when {
            clazz.isInstance(adapter) -> clazz.cast(adapter)
            adapter is SimpleWrapperAdapter<*> -> {
                val wrappedAdapter = adapter.wrappedAdapter
                findWrappedAdapter(wrappedAdapter, clazz)
            }
            else -> null
        }
    }

    fun <T> findWrappedAdapter(originAdapter: RecyclerView.Adapter<*>, clazz: Class<T>, position: Int): T? {
        val path = AdapterPath()
        when (unwrapPosition(originAdapter, null, null, position, path)) {
            RecyclerView.NO_POSITION -> return null
            else -> {
                for (segment in path.segments()) {
                    when {
                        clazz.isInstance(segment.adapter) -> return clazz.cast(segment.adapter)
                    }
                }
                return null
            }
        }
    }

    fun releaseAll(adapter: RecyclerView.Adapter<*>): RecyclerView.Adapter<*> {
        return releaseCyclically(adapter)
    }

    private fun releaseCyclically(adapter: RecyclerView.Adapter<*>): RecyclerView.Adapter<*> {
        when (adapter) {
            !is WrapperAdapter<*> -> return adapter
            else -> {
                val wrapperAdapter = adapter as WrapperAdapter<*>
                val wrappedAdapters = ArrayList<RecyclerView.Adapter<*>>()
                wrapperAdapter.getWrappedAdapters(wrappedAdapters)
                wrapperAdapter.release()

                for (i in wrappedAdapters.indices.reversed()) {
                    val wrappedAdapter = wrappedAdapters[i]
                    releaseCyclically(wrappedAdapter)
                }
                wrappedAdapters.clear()
                return adapter
            }
        }

    }

    fun unwrapPosition(originAdapter: RecyclerView.Adapter<*>, position: Int): Int {
        return unwrapPosition(originAdapter, null, position)
    }

    fun unwrapPosition(originAdapter: RecyclerView.Adapter<*>, targetAdapter: RecyclerView.Adapter<*>?, position: Int): Int {
        return unwrapPosition(originAdapter, targetAdapter, null, position, null)
    }

    fun unwrapPosition(originAdapter: RecyclerView.Adapter<*>, targetAdapter: RecyclerView.Adapter<*>?, targetAdapterTag: Any?, position: Int): Int {
        return unwrapPosition(originAdapter, targetAdapter, targetAdapterTag, position, null)
    }

    fun unwrapPosition(originAdapter: RecyclerView.Adapter<*>, targetAdapterPathSegment: AdapterPathSegment, originPosition: Int, destPath: AdapterPath?): Int {
        return unwrapPosition(originAdapter, targetAdapterPathSegment.adapter, targetAdapterPathSegment.tag, originPosition, destPath)
    }

    fun unwrapPosition(originAdapter: RecyclerView.Adapter<*>, targetAdapter: RecyclerView.Adapter<*>?, targetAdapterTag: Any?, originPosition: Int, destPath: AdapterPath?): Int {
        var wrapper: RecyclerView.Adapter<*>? = originAdapter
        var wrappedPosition = originPosition
        val tmpResult = UnwrapPositionResult()
        var wrappedAdapterTag: Any? = null

        destPath?.clear()

        when (wrapper) {
            null -> return RecyclerView.NO_POSITION
            else -> {
                destPath?.append(AdapterPathSegment(originAdapter, null))
                loop@ do {
                    when (wrappedPosition) {
                        RecyclerView.NO_POSITION -> break@loop
                    }
                    when {
                        wrapper === targetAdapter -> break@loop
                    }
                    when (wrapper) {
                        !is WrapperAdapter<*> -> {
                            when {
                                targetAdapter != null -> wrappedPosition = RecyclerView.NO_POSITION
                            }
                            break@loop
                        }
                    }

                    val wrapperParentAdapter = wrapper as WrapperAdapter<*>?
                    tmpResult.clear()
                    wrapperParentAdapter!!.unwrapPosition(tmpResult, wrappedPosition)
                    wrappedPosition = tmpResult.position
                    wrappedAdapterTag = tmpResult.tag
                    when {
                        tmpResult.isValid -> destPath?.append(tmpResult)
                    }
                    wrapper = tmpResult.adapter
                } while (wrapper != null)

                when {
                    targetAdapter != null && wrapper !== targetAdapter -> wrappedPosition = RecyclerView.NO_POSITION
                }
                when {
                    targetAdapterTag != null && wrappedAdapterTag !== targetAdapterTag -> wrappedPosition = RecyclerView.NO_POSITION
                }
                when {
                    wrappedPosition == RecyclerView.NO_POSITION && destPath != null -> destPath.clear()
                }
                return wrappedPosition
            }
        }

    }

    fun wrapPosition(path: AdapterPath, originAdapter: RecyclerView.Adapter<*>?, targetAdapter: RecyclerView.Adapter<*>?, position: Int): Int {
        val segments = path.segments()
        val nSegments = segments.size

        var originSegmentIndex = when (originAdapter) {
            null -> nSegments - 1
            else -> -1
        }
        var targetSegmentIndex = when (targetAdapter) {
            null -> 0
            else -> -1
        }

        when {
            originAdapter != null || targetAdapter != null -> for (i in 0 until nSegments) {
                val segment = segments[i]
                when {
                    originAdapter != null && segment.adapter === originAdapter -> originSegmentIndex = i
                }
                when {
                    targetAdapter != null && segment.adapter === targetAdapter -> targetSegmentIndex = i
                }
            }
        }

        return when {
            !(originSegmentIndex != -1 && targetSegmentIndex != -1 && targetSegmentIndex <= originSegmentIndex) -> RecyclerView.NO_POSITION
            else -> wrapPosition(path, originSegmentIndex, targetSegmentIndex, position)
        }

    }

    private fun wrapPosition(path: AdapterPath, originSegmentIndex: Int, targetSegmentIndex: Int, position: Int): Int {
        val segments = path.segments()
        var wrappedPosition = position

        loop@ for (i in originSegmentIndex downTo targetSegmentIndex + 1) {
            val segment = segments[i]
            val parentSegment = segments[i - 1]
            val prevWrappedPosition = wrappedPosition

            wrappedPosition = (parentSegment.adapter as WrapperAdapter<*>).wrapPosition(segment, wrappedPosition)
            when (wrappedPosition) {
                RecyclerView.NO_POSITION -> break@loop
            }
        }
        return wrappedPosition
    }

}
