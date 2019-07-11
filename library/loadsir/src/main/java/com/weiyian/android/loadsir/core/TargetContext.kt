package com.weiyian.android.loadsir.core

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * @author :
 */
class TargetContext(val context: Context, internal val parentView: ViewGroup, internal val oldContent: View, internal val childIndex: Int)
