package com.weiyian.android.loadsir.callback

import android.content.Context
import android.view.View
import java.io.*

/**
 * @author :
 */
abstract class Callback : Serializable {

    private var rootView: View? = null
    private var context: Context? = null
    private var onReloadListener: OnReloadListener? = null

    constructor(context: Context) {
        this.context = context;
    }

    constructor(rootView: View?, context: Context, onReloadListener: OnReloadListener) {
        this.rootView = rootView
        this.context = context
        this.onReloadListener = onReloadListener
    }

    constructor()

    fun setCallback(view: View?, context: Context, onReloadListener: OnReloadListener): Callback {
        this.rootView = view
        this.context = context
        this.onReloadListener = onReloadListener
        return this
    }

    fun getRootView(): View? {
        val resId = onCreateView()
        when {
            resId == 0 && rootView != null -> return rootView
            onBuildView(context) != null -> rootView = onBuildView(context)
        }

        when (rootView) {
            null -> rootView = View.inflate(context, onCreateView(), null)
        }

        // click
        rootView?.setOnClickListener(View.OnClickListener { v ->
            when {
                onReloadEvent(context, rootView) -> return@OnClickListener
                else -> onReloadListener?.onReload(v)
            }
        })
        onViewCreate(context, rootView)
        return rootView
    }

    protected open fun onBuildView(context: Context?): View? {
        return null
    }

    open fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }

    fun copy(): Callback? {
        val bao = ByteArrayOutputStream()
        val oos: ObjectOutputStream
        var obj: Any? = null
        try {
            oos = ObjectOutputStream(bao)
            oos.writeObject(this)
            oos.close()
            val bis = ByteArrayInputStream(bao.toByteArray())
            val ois = ObjectInputStream(bis)
            obj = ois.readObject()
            ois.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return obj as Callback?
    }

    /**
     * @since 1.2.2
     */
    fun obtainRootView(): View? {
        when (rootView) {
            null -> rootView = View.inflate(context, onCreateView(), null)
        }
        return rootView
    }

    protected abstract fun onCreateView(): Int

    /**
     * Called immediately after [.onCreateView]
     *
     * @since 1.2.2
     */
    protected open fun onViewCreate(context: Context?, view: View?) {}

    /**
     * Called when the rootView of Callback is attached to its LoadLayout.
     *
     * @since 1.2.2
     */
    fun onAttach(context: Context, view: View?) {}

    /**
     * Called when the rootView of Callback is removed from its LoadLayout.
     *
     * @since 1.2.2
     */
    fun onDetach() {}

    interface OnReloadListener : Serializable {
        fun onReload(v: View)
    }

    companion object {
        /**
         * if return true, the successView will be visible when the view of callback is attached.
         */
        var successVisible: Boolean = false
            internal set
    }

}
