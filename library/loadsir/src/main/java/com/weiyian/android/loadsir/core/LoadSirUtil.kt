package com.weiyian.android.loadsir.core

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.View
import android.view.ViewGroup

/**
 * @author :
 */
object LoadSirUtil {

    fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()

    fun getTargetContext(target: Any): TargetContext {
        val contentParent: ViewGroup?
        val context: Context

        when (target) {
            is Activity -> {
                context = target
                contentParent = target.findViewById(android.R.id.content)
            }
            is View -> {
                context = target.context
                contentParent = target.parent as ViewGroup
            }
            else -> throw IllegalArgumentException("The target must be within Activity, Fragment, View.")
        }

        val oldContent: View?
        var childIndex = 0
        val childCount = contentParent?.childCount ?: 0

        when (target) {
            is View -> {
                oldContent = target
                for (i in 0 until childCount) {
                    if (contentParent?.getChildAt(i) === oldContent) {
                        childIndex = i
                        break
                    }
                }
            }
            else -> oldContent = contentParent?.getChildAt(0)
        }

        when (oldContent) {
            null -> throw IllegalArgumentException(String.format("enexpected error when register LoadSir in %s", target.javaClass.simpleName))
            else -> {
                contentParent?.removeView(oldContent)
                return TargetContext(context, contentParent!!, oldContent, childIndex)
            }
        }
    }

}
