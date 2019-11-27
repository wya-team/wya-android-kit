package com.weiyian.android.loadsir.core

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.weiyian.android.loadsir.callback.Callback
import com.weiyian.android.loadsir.callback.SuccessCallback
import java.util.*

/**
 * @author :
 */

class LoadLayout(context: Context, private val onReloadListener: Callback.OnReloadListener) : FrameLayout(context) {

    private val callbacks = HashMap<Class<out Callback>, Callback>()
    private var preCallback: Class<out Callback>? = null

    fun setupSuccessLayout(callback: Callback) {
        addCallback(callback)
        val successView = callback.getRootView()
        successView?.visibility = View.GONE
        addView(successView)
        currentCallback = SuccessCallback::class.java
    }

    fun setupCallback(callback: Callback) {
        val cloneCallback = callback.copy()
        cloneCallback?.setCallback(null, context, onReloadListener)
        cloneCallback?.let {
            addCallback(it)
        }
    }

    private fun addCallback(callback: Callback) {
        when {
            callbacks.containsKey(callback.javaClass) -> return
            else -> callbacks[callback.javaClass] = callback
        }
    }

    fun showCallback(callback: Class<out Callback>) {
        checkCallbackExist(callback)
        when {
            LoadSirUtil.isMainThread() -> showCallbackView(callback)
            else -> postToMainThread(callback)
        }
    }

    private fun postToMainThread(status: Class<out Callback>) {
        post { showCallbackView(status) }
    }

    private fun showCallbackView(status: Class<out Callback>) {
        preCallback?.let {
            when (preCallback) {
                status -> return
                else -> callbacks[preCallback!!]?.onDetach()
            }
        }

        when {
            childCount > 1 -> removeViewAt(CALLBACK_CUSTOM_INDEX)
        }

        callbacks.keys.forEach { key ->
            when (key) {
                status -> {
                    val successCallback = callbacks[SuccessCallback::class.java] as SuccessCallback?
                    when (key) {
                        SuccessCallback::class.java -> successCallback?.show()
                        else -> {
                            successCallback?.showWithCallback(Callback.successVisible)
                            val rootView = callbacks[key]?.getRootView()
                            addView(rootView)
                            callbacks[key]?.onAttach(context, rootView)
                        }
                    }
                    preCallback = status
                }
            }
        }
        currentCallback = status
    }

    fun setCallBack(callback: Class<out Callback>, transport: Transport?) {
        when {
            transport != null -> {
                checkCallbackExist(callback)
                transport.order(context, callbacks[callback]?.obtainRootView())
            }
        }
    }

    private fun checkCallbackExist(callback: Class<out Callback>) {
        when {
            !callbacks.containsKey(callback) -> throw IllegalArgumentException(String.format("The Callback (%s) is nonexistent.", callback.simpleName))
        }
    }

    companion object {

        private const val CALLBACK_CUSTOM_INDEX = 1
        lateinit var currentCallback: Class<out Callback>
            private set

    }

}
