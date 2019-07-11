package com.weiyian.android.loadsir.callback

import android.content.Context
import android.view.View

/**
 * @author :
 */
class SuccessCallback(view: View, context: Context, onReloadListener: OnReloadListener) : Callback(view, context, onReloadListener) {

    override fun onCreateView(): Int {
//        return R.layout.layout_base_loading
        return 0
    }

    fun show() {
        obtainRootView()?.visibility = View.VISIBLE
    }

    fun showWithCallback(successVisible: Boolean) {
        obtainRootView()?.visibility = when {
            successVisible -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }

}
